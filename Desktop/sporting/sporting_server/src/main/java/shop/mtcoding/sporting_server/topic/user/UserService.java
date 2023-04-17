package shop.mtcoding.sporting_server.topic.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.user.dto.UserRequest;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public String 로그인(UserRequest.LoginDTO loginDTO) {
        Optional<User> userOP = userRepository.findByEmail(loginDTO.getEmail());
        // 로그인 유저 아이디가 있다면
        if (userOP.isPresent()) {
            // 있으면 비밀번호 match (details를 안쓸거면 내가 비교해야되고, 암호화 된걸 처리해야 함)
            User userPS = userOP.get();
            // 로그인 입력 값과 DB password를 비교
            if (passwordEncoder.matches(loginDTO.getPassword(), userPS.getPassword())) {
                String jwt = MyJwtProvider.create(userPS); // 토큰 생성1
                return jwt;
            }
            throw new RuntimeException("패스워드 틀렸어");
        } else {
            throw new RuntimeException("유저네임 없어");
        }
    }
}
