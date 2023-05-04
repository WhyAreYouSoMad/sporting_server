package shop.mtcoding.sporting_server.topic.user;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.core.util.AdminStatisticsUtils;
import shop.mtcoding.sporting_server.modules.connection_data.repository.ConnectionDataRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.user.dto.UserRequest;
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse;
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse.UserDetailOutDto;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConnectionDataRepository connectionDataRepository;
    private final AdminStatisticsUtils adminStatisticsUtils;

    @Transactional
    public ArrayList 로그인(UserRequest.LoginDTO loginDTO) {

        ArrayList loginOutList = new ArrayList();

        Optional<User> userOP = userRepository.findByEmail(loginDTO.getEmail());
        // 로그인 유저 아이디가 있다면
        if (userOP.isPresent()) {
            // 있으면 비밀번호 match (details를 안쓸거면 내가 비교해야되고, 암호화 된걸 처리해야 함)
            User userPS = userOP.get();
            // 로그인 입력 값과 DB password를 비교
            if (passwordEncoder.matches(loginDTO.getPassword(), userPS.getPassword())) {
                String jwt = MyJwtProvider.create(userPS); // 토큰 생성1
                loginOutList.add(jwt);
                loginOutList.add(userPS.getId());
                loginOutList.add(userPS.getNickname());
                loginOutList.add(userPS.getRole());
                if (userPS.getStatus().equals(UserStatus.악질유저)) {
                    throw new Exception400("악질유저는 로그인이 불가능합니다");
                } else if (userPS.getStatus().equals(UserStatus.인증대기)) {
                    throw new Exception400("관리자의 승인이 필요합니다");
                }

                // 로그인 시 관리자 - 접속률 통계확인용 DB저장
                adminStatisticsUtils.manageConnectionsData(userPS);

                return loginOutList;

            }
            throw new Exception400("패스워드가 유효하지 않습니다");
        } else {
            throw new Exception400("이메일이 유효하지 않거나 존재하지 않습니다");
        }
    }

    @Transactional
    public UserDetailOutDto getUser(Authentication authentication) {
        Long loggedInUserId = ((MyUserDetails) authentication.getPrincipal()).getId();
        User userPS = userRepository.findById(loggedInUserId).orElseThrow(() -> {
            throw new Exception400("해당 유저가 없습니다");
        });
        UserResponse.UserDetailOutDto userDetailOutDto = new UserDetailOutDto(userPS);
        return userDetailOutDto;
    }
}
