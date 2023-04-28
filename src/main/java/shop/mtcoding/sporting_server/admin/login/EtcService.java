package shop.mtcoding.sporting_server.admin.login;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.login.dto.AdmingLoginInDTO;
import shop.mtcoding.sporting_server.core.handler.ex.CustomException;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EtcService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public void 로그인(AdmingLoginInDTO loginDTO) {
        User userPS = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> {
            throw new CustomException("관리자 아이디를 확인하세요.", HttpStatus.UNAUTHORIZED);
        });

        if (!userPS.getRole().equals("ADMIN")) {
            throw new CustomException("관리자만 로그인할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }

        if (!loginDTO.getPassword().equals(userPS.getPassword())) {
            throw new CustomException("관리자 암호를 확인하세요.", HttpStatus.UNAUTHORIZED);
        }
    }

}
