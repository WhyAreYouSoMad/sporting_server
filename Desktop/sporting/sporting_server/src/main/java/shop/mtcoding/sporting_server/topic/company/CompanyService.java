package shop.mtcoding.sporting_server.topic.company;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.sporting_server.core.enums.role.RoleType;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final UserRepository userRepository;
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
}
