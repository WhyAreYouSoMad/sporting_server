package shop.mtcoding.sporting_server.topic.company;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;

import shop.mtcoding.sporting_server.core.enums.role.RoleType;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.util.BASE64DecodedMultipartFile;
import shop.mtcoding.sporting_server.core.util.S3Utils;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyUpdateFormOutDTO;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest.UpdateInDTO;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse.UpdateOutDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final ProfileFileRepository profileFileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${bucket}")
    private String bucket;
    @Value("${static}")
    private String staticRegion;

    @Transactional
    public CompanyResponse.JoinDTO 회원가입(CompanyRequest.JoinInDTO joinDTO) {

        Optional<User> emailCheck = userRepository.findByEmail(joinDTO.getEmail());
        if (emailCheck.isPresent()) {
            throw new Exception400("동일한 email이 있습니다");
        }

        if (joinDTO.getPassword() == joinDTO.getPasswordCon()) {
            throw new Exception400("비밀번호가 일치하지 않습니다");
        }
        String rawPassword = joinDTO.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 60Byte
        joinDTO.setPassword(encPassword);
        joinDTO.setRole(RoleType.COMPANY.toString());

        User userPS = userRepository.save(joinDTO.toEntity());

        return new CompanyResponse.JoinDTO(userPS);
    }

    public CompanyUpdateFormOutDTO getUpdateForm(Long id) {

        userRepository.findById(id).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 유저 입니다");
        });

        CompanyUpdateFormOutDTO companyUpdateFormOutDTO = userRepository.findByCompanyUserId(id);
        companyUpdateFormOutDTO.setCompanyInfo(companyInfoRepository.findCompanyInfoByUserId(id));
        return companyUpdateFormOutDTO;

    }

    @Transactional
    public UpdateOutDTO 정보변경(Long id, UpdateInDTO updateInDTO) throws IOException {

        User userPS = userRepository.findById(id).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 유저입니다.");
        });

        String rawPassword = updateInDTO.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        userPS.setNickname(updateInDTO.getNickname());
        userPS.setPassword(encPassword);
        // userPS.setUpdatedAt(updateInDTO.getUpdatedAt());

        CompanyInfo companyInfoPS = companyInfoRepository.findByUserId(id).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 회사입니다.");
        });
        companyInfoPS.setTel(updateInDTO.getTel());
        companyInfoPS.setBusinessAdress(updateInDTO.getBusinessAdress());
        companyInfoPS.setBusinessNumber(updateInDTO.getBusinessNumber());
        // System.out.println("디버깅 00 : " + companyInfoPS.getFileInfo().getId());
        ProfileFile companyProfileFilePS = profileFileRepository.findById(companyInfoPS.getFileInfo().getId())
                .orElseThrow(() -> {
                    throw new Exception400("Profile File이 존재하지 않습니다.");
                });

        // size가 다르면 false, 같으면 true
        Boolean sizeCheck = S3Utils.updateProfileCheck_Company(companyProfileFilePS,
                updateInDTO.getSourceFile().getFileBase64(), bucket, staticRegion);

        if (!sizeCheck) {
            MultipartFile multipartFile2 = BASE64DecodedMultipartFile
                    .convertBase64ToMultipartFile(updateInDTO.getSourceFile().getFileBase64());

            // 사진이 업데이트 되었을 경우 S3upload 후, DB 반영
            List<String> nameAndUrl = S3Utils.uploadFile(multipartFile2, "CompanyProfile", bucket, amazonS3Client);
            companyProfileFilePS.setFileName(nameAndUrl.get(0));
            companyProfileFilePS.setFileUrl(nameAndUrl.get(1));
        }

        UpdateOutDTO updateOutDTO = new UpdateOutDTO(userPS, companyInfoPS, companyProfileFilePS);

        return updateOutDTO;
    }
}
