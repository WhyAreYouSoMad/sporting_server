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
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 1. 트랜잭션 관리
     * 2. 영속성 객체 변경감지
     * 3. RequestDTO 요청받기
     * 4. 비지니스 로직 처리하기
     * 5. ResponseDTO 응답하기
     */
    @Transactional
    public UserResponse.JoinDto 회원가입(UserRequest.JoinDTO joinDTO) {
        // rawPassword : 입력 Password값 저장
        String rawPassword = joinDTO.getPassword();

        // encPassword : BCryptPasswordEncoder로 60Byte 암호화
        // 암호화 결과 : $2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW
        String encPassword = passwordEncoder.encode(rawPassword);
        joinDTO.setPassword(encPassword);

        // JPA 사용에 있어 영속성 컨텍스트
        User userPS = userRepository.save(joinDTO.toEntity());
        return new UserResponse.JoinDto(userPS);
    }

    public String 로그인(UserRequest.LoginDTO loginDTO) {
        System.out.println("디버깅 : " + 1);
        Optional<User> userOP = userRepository.findByUsername(loginDTO.getNickname());
        // 로그인 유저 아이디가 있다면
        System.out.println("디버깅 : " + 2);
        if (userOP.isPresent()) {
            // 있으면 비밀번호 match (details를 안쓸거면 내가 비교해야되고, 암호화 된걸 처리해야 함)
            User userPS = userOP.get();
            // 로그인 입력 값과 DB password를 비교
            if (passwordEncoder.matches(loginDTO.getPassword(), userPS.getPassword())) {
                System.out.println("디버깅 : " + 3);
                System.out.println("디버깅 111: " + System.getenv("HS512_SECRET"));
                String jwt = MyJwtProvider.create(userPS); // 토큰 생성1
                System.out.println("디버깅 : " + 4);
                return jwt;
            }
            throw new RuntimeException("패스워드 틀렸어");
        } else {
            throw new RuntimeException("유저네임 없어");
        }
    }
}
