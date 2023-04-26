package shop.mtcoding.sporting_server.topic.stadium;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.exception.Exception403;
import shop.mtcoding.sporting_server.core.util.BASE64DecodedMultipartFile;
import shop.mtcoding.sporting_server.core.util.S3Utils;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumDetailOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest.StadiumUpdateInDTO.CourtDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse.StadiumRegistrationOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumService {
    private final AmazonS3Client amazonS3Client;
    private final StadiumRepository stadiumRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final SportCategoryRepository sportCategoryRepository;
    private final StadiumCourtRepository stadiumCourtRepository;
    private final FileInfoRepository fileInfoRepository;
    private final ProfileFileRepository profileFileRepository;

    @Value("${bucket}")
    private String bucket;
    @Value("${static}")
    private String staticRegion;

    @Transactional
    public StadiumRegistrationOutDTO save(Long id, StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO) {

        CompanyInfo companyInfoPS = companyInfoRepository.findByUserId(id).orElseThrow(() -> {
            throw new Exception400("유저정보를 업데이트해야 작성 가능합니다.");
        });

        SportCategory sportCategoryPS = sportCategoryRepository.findBySport(stadiumRegistrationInDTO.getCategory())
                .orElseThrow(() -> {
                    throw new Exception400("해당 스포츠 카테고리가 존재하지 않습니다.");
                });

        Stadium stadiumPS = stadiumRepository
                .save(stadiumRegistrationInDTO.toEntity(companyInfoPS, stadiumRegistrationInDTO, sportCategoryPS));

        String sport = stadiumPS.getCategory().getSport();
        FileInfo fileInfo;
        ProfileFile profileFile;

        fileInfo = FileInfo.builder().type(FileInfoSource.경기장사진).build();
        fileInfoRepository.save(fileInfo);
        profileFile = ProfileFile.builder()
                .fileInfo(fileInfo)
                .fileName(S3Utils.chooseStadiumName(sport))
                .fileUrl(S3Utils.chooseStadiumUrl(sport))
                .build();
        profileFileRepository.save(profileFile);

        stadiumPS.setFileInfo(fileInfo);
        return new StadiumRegistrationOutDTO(stadiumPS, profileFile);
    }

    public List<StadiumListOutDTO> findKeywordList(String keyword) {
        List<StadiumListOutDTO> stadiumListOut = stadiumRepository.findStadiumListBySportKeyword(keyword);
        return stadiumListOut;
    }

    public List<StadiumMyListOutDTO> findKeywordMyList(Long pricipalCompanyId, String keyword) {
        List<StadiumMyListOutDTO> stadiumMyListOut = stadiumRepository.findStadiumMyListBySportKeyword(keyword,
                pricipalCompanyId);

        return stadiumMyListOut;
    }

    public StadiumUpdateFomrOutDTO getUpdateForm(Long id, Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 경기장입니다.");
        });
        if (!stadium.getCompanyInfo().getUser().getId().equals(id)) {
            throw new Exception403("수정 권한이 없습니다.");
        }

        StadiumUpdateFomrOutDTO stadiumUpdateFomrOutDTO = stadiumRepository.findByStadiumId(stadiumId);

        stadiumUpdateFomrOutDTO.setCourt(stadiumRepository.findCourtsByStadiumId(stadiumId));
        // stadiumUpdateFomrOutDTO.setCourt(stadiumRepository.findByStadiumIdForCourtList(stadiumId));

        return stadiumUpdateFomrOutDTO;
    }

    public StadiumDetailOutDTO detail(Long stadiumId) {

        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 경기장입니다.");
        });

        StadiumDetailOutDTO stadiumDetailDTO = stadiumRepository.findByStadiumId2(stadiumId);

        stadiumDetailDTO.setCategory(stadiumRepository.findCategoryByStadiumId(stadium.getCategory().getId()));

        stadiumDetailDTO.setStadiumCourt(stadiumCourtRepository.findStadiumCourtByStadiumId(stadiumId));

        return stadiumDetailDTO;
    }

    @Transactional
    public void update(Long userId, StadiumRequest.StadiumUpdateInDTO stadiumUpdateInDTO) throws IOException {

        Stadium stadiumPS = stadiumRepository.findById(Long.parseLong(stadiumUpdateInDTO.getId())).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 경기장입니다.");
        });

        if (stadiumPS.getCompanyInfo().getUser().getId() != userId) {
            throw new Exception400("해당 게시물을 올린 사용자만 수정할 수 있습니다.");
        }

        List<CourtDTO> courtList = stadiumUpdateInDTO.getCourtList();
        List<Long> courtIdList = courtList.stream()
                .map(court -> Long.parseLong(court.getId()))
                .collect(Collectors.toList());

        // DTO에 담긴 CourtIds를 이용한 DB 조회 리스트
        List<StadiumCourt> courtsToUpdatePS = stadiumCourtRepository.findByIdIn(courtIdList);
        if (courtsToUpdatePS.size() != courtList.size()) {
            throw new Exception400("존재하지 않는 Court가 요청 되었습니다.");

        }

        // court를 Map으로 변환(courtsToUpdatePS 영속화를 활용하기 위함)
        Map<Long, StadiumCourt> courtMap = courtsToUpdatePS.stream()
                .collect(Collectors.toMap(StadiumCourt::getId, Function.identity()));

        List<StadiumCourt> stadiumsToUpdate = courtList.stream()
                .map(court -> {
                    // court: RequestDTO, courtMap value: DB에서 조회
                    StadiumCourt stadiumCourtPS = courtMap.get(Long.parseLong(court.getId()));

                    if (!stadiumCourtPS.getStadium().equals(stadiumPS)) {
                        throw new Exception400("해당 경기장에서 관리하는 Court만 수정 가능합니다.");
                    }
                    // StadiumCourt update - fileUrl 제외
                    stadiumCourtPS.dtoToEntityForCourtUpdate(court);
                    return stadiumCourtPS;
                })
                .collect(Collectors.toList());

        // Stadium update - fileUrl 제외
        stadiumPS.dtoToEntityForStadiumUpdate(stadiumUpdateInDTO);
        if (!stadiumPS.getCategory().getSport().equals(stadiumUpdateInDTO.getCategory())) {
            SportCategory sportCategory = sportCategoryRepository.findBySport(stadiumUpdateInDTO.getCategory())
                    .orElseThrow(() -> {
                        throw new Exception400("해당 스포츠 카테고리가 존재하지 않습니다.");
                    });
            stadiumPS.setCategory(sportCategory);
        }

        ProfileFile stadiumProfileFilePS = profileFileRepository.findById(stadiumPS.getFileInfo().getId())
                .orElseThrow(() -> {
                    throw new Exception400("Stadium Profile File이 존재하지 않습니다.");
                });
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(staticRegion).build();
        ObjectMetadata objectMetadata = s3Client.getObjectMetadata(bucket,
                stadiumProfileFilePS.getFileName());

        long stadiumDBFileSize = objectMetadata.getContentLength();
        MultipartFile multipartFile1 = BASE64DecodedMultipartFile
                .convertBase64ToMultipartFile(stadiumUpdateInDTO.getStadiumFile().getFileBase64());
        long stadiumDTOFileSize = multipartFile1.getSize();

        if (stadiumDBFileSize != stadiumDTOFileSize) {
            MultipartFile multipartFile2 = BASE64DecodedMultipartFile
                    .convertBase64ToMultipartFile(stadiumUpdateInDTO.getStadiumFile().getFileBase64());
            List<String> nameAndUrl = S3Utils.uploadFile(multipartFile2, "Stadium", bucket, amazonS3Client);

            stadiumProfileFilePS.setFileName(nameAndUrl.get(0));
            stadiumProfileFilePS.setFileUrl(nameAndUrl.get(1));
        }

        // file 체킹 테스트

    }

}
