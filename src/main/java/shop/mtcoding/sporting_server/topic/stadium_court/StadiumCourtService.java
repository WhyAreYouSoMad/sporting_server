package shop.mtcoding.sporting_server.topic.stadium_court;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
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
            List<String> nameAndUrl = S3Utils.uploadFile(multipartFile, "Court", bucket, amazonS3Client);

            fileInfo = FileInfo.builder().type(FileInfoSource.코트사진).build();
            fileInfoRepository.save(fileInfo);
            profileFile = ProfileFile.builder().fileInfo(fileInfo).fileName(nameAndUrl.get(0))
                    .fileUrl(nameAndUrl.get(1)).build();
            profileFileRepository.save(profileFile);

        } else {
            fileInfo = FileInfo.builder().type(FileInfoSource.코트사진).build();
            fileInfoRepository.save(fileInfo);

            profileFile = ProfileFile.builder().fileInfo(fileInfo)
                    .fileName(S3Utils.chooseCourtName(sport))
                    .fileUrl(S3Utils.chooseCourtUrl(sport))
                    .build();
            profileFileRepository.save(profileFile);

        }

        StadiumCourt stadiumCourtPS = stadiumCourtRepository
                .save(stadiumCourtInDTO.toEntity(stadiumPS, stadiumCourtInDTO, fileInfo));
        return new StadiumCourtOutDTO(stadiumCourtPS, profileFile);
    }
}