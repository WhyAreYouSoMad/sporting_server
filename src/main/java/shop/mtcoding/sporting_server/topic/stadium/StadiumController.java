package shop.mtcoding.sporting_server.topic.stadium;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.core.util.BASE64DecodedMultipartFile;
import shop.mtcoding.sporting_server.core.util.S3Utils;
import shop.mtcoding.sporting_server.core.util.StadiumUtils;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumDetailOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse.StadiumRegistrationOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StadiumController {

        private final StadiumService stadiumService;
        @Value("${bucket}")
        private String bucket;

        @Value("${static}")
        private String staticRegion;

        @PostMapping("/company/stadiums")
        public ResponseEntity<?> save(
                        @RequestBody @Valid StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO,
                        BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails) {

                StadiumRegistrationOutDTO stadiumRegistrationOutDTO = stadiumService.save(
                                myUserDetails.getUser().getId(),
                                stadiumRegistrationInDTO);

                return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumRegistrationOutDTO));
        }

        @PutMapping("/company/stadiums")
        public ResponseEntity<?> update(
                        @RequestBody @Valid StadiumRequest.StadiumUpdateInDTO stadiumUpdateInDTO,
                        BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails)
                        throws IOException {

                AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(staticRegion).build();
                ObjectMetadata objectMetadata = s3Client.getObjectMetadata(bucket,
                                "Stadium/야구_Stadium.png");
                long contentLength = objectMetadata.getContentLength();
                System.out.println("테스트 1 : " + contentLength);

                MultipartFile multipartFile = BASE64DecodedMultipartFile
                                .convertBase64ToMultipartFile(stadiumUpdateInDTO.getStadiumFile().getFileBase64());
                System.out.println("테스트 2 : " + multipartFile.getSize());

                stadiumService.update(myUserDetails.getUser().getId(), stadiumUpdateInDTO);

                return ResponseEntity.ok().body(new ResponseDto<>());
        }

        @GetMapping("/user/stadiums")
        public ResponseEntity<?> findAllList(String keyword) {
                StadiumUtils.keywordValidiationCheck(keyword);
                List<StadiumListOutDTO> stadiumListOutDTO = stadiumService.findKeywordList(keyword);

                return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumListOutDTO));
        }

        @GetMapping("/company/mystadiums")
        public ResponseEntity<?> findAllMyList(String keyword, @AuthenticationPrincipal MyUserDetails myUserDetails) {
                StadiumUtils.keywordValidiationCheck(keyword);

                List<StadiumMyListOutDTO> stadiumMyListOutDTO = stadiumService
                                .findKeywordMyList(myUserDetails.getUser().getId(), keyword);

                return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumMyListOutDTO));
        }

        @GetMapping("/company/mystadiums/updateform/{stadiumId}")
        public ResponseEntity<?> updateForm(@PathVariable Long stadiumId,
                        @AuthenticationPrincipal MyUserDetails myUserDetails) {

                StadiumUpdateFomrOutDTO stadiumUpdateFomrOutDTO = stadiumService.getUpdateForm(
                                myUserDetails.getUser().getId(),
                                stadiumId);

                return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumUpdateFomrOutDTO));
        }

        @GetMapping("/user/detail/{stadiumId}")
        public ResponseEntity<?> detail(@PathVariable Long stadiumId) {
                StadiumDetailOutDTO stadiumDetailDTO = stadiumService.detail(
                                stadiumId);
                return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumDetailDTO));
        }
}
