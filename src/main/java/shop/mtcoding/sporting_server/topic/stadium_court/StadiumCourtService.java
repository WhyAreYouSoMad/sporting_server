package shop.mtcoding.sporting_server.topic.stadium_court;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.util.BASE64DecodedMultipartFile;
import shop.mtcoding.sporting_server.core.util.S3Utils;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumCourtOutDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumCourtService {

    @Value("${bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final StadiumCourtRepository stadiumCourtRepository;
    private final StadiumRepository stadiumRepository;
    private final FileInfoRepository fileInfoRepository;
    private final ProfileFileRepository profileFileRepository;

    public StadiumCourtOutDTO save(StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO, Long stadiumId, Long id)
            throws IOException {
        Stadium stadiumPS = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new Exception400("경기장 정보를 업데이트해야 작성 가능합니다."));
        if (stadiumPS.getCompanyInfo().getUser().getId() != id) {
            throw new Exception400("등록할 권한이 없습니다");
        }
        String sport = stadiumPS.getCategory().getSport();

        FileInfo fileInfo;
        ProfileFile profileFile;
        if (!stadiumCourtInDTO.getCourtProfile().equals("")) {

            MultipartFile multipartFile = BASE64DecodedMultipartFile
                    .convertBase64ToMultipartFile(stadiumCourtInDTO.getCourtProfile());
            String storeFileUrl = S3Utils.uploadFile(multipartFile, "Court", bucket, amazonS3Client);

            fileInfo = FileInfo.builder().type(FileInfoSource.코트사진).build();
            fileInfoRepository.save(fileInfo);
            profileFile = ProfileFile.builder().fileInfo(fileInfo).fileUrl(storeFileUrl).build();
            profileFileRepository.save(profileFile);

        } else {
            fileInfo = FileInfo.builder().type(FileInfoSource.코트사진).build();
            fileInfoRepository.save(fileInfo);
            profileFile = ProfileFile.builder().fileInfo(fileInfo)
                    .fileUrl(S3Utils.chooseCourtUrl(sport))
                    .build();
            profileFileRepository.save(profileFile);

        }

        StadiumCourt stadiumCourtPS = stadiumCourtRepository
                .save(stadiumCourtInDTO.toEntity(stadiumPS, stadiumCourtInDTO, fileInfo));
        return new StadiumCourtOutDTO(stadiumCourtPS, profileFile);
    }

    public Page<StadiumCourt> getStadiumCourtList(Pageable pageable) {
        StadiumCourt stadiumCourt = new StadiumCourt();
        stadiumCourt.setStatus(StadiumCourtStatus.등록완료);
        Example<StadiumCourt> example = Example.of(stadiumCourt);

        return stadiumCourtRepository.findAll(example, pageable);
    }

    public Page<StadiumCourt> getStadiumCourtWaitList(Pageable pageable) {
        StadiumCourt stadiumCourt = new StadiumCourt();
        stadiumCourt.setStatus(StadiumCourtStatus.등록대기);
        Example<StadiumCourt> example = Example.of(stadiumCourt);

        return stadiumCourtRepository.findAll(example, pageable);
    }

    public Page<StadiumCourt> getCourtListByTitleContaining(String title, Pageable pageable) {
        return stadiumCourtRepository.findByTitleContaining(title, pageable);
    }

    @Transactional
    public boolean approveCompany(Long courtId) {
        Optional<StadiumCourt> StadiumCourt = stadiumCourtRepository.findById(courtId);
        if (StadiumCourt.isPresent()) {
            StadiumCourt stadiumCourt = StadiumCourt.get();
            stadiumCourt.setStatus(StadiumCourtStatus.등록완료);
            stadiumCourtRepository.save(stadiumCourt);

            return true;
        } else {
            return false;
        }
    }
}