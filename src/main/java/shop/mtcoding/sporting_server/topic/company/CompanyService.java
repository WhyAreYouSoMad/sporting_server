package shop.mtcoding.sporting_server.topic.company;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.sporting_server.core.enums.role.RoleType;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyUpdateFormOutDTO;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest.UpdateInDTO;

import java.util.Optional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final UserRepository userRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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

    public void 정보변경(Long id, UpdateInDTO updateInDTO) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            throw new Exception400("존재하지 않는 유저 입니다");
        }

    }
}
