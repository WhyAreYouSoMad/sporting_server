package shop.mtcoding.sporting_server.core.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class DummyEntity {
    public User newPlayerUser(String username, String nickname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .nickname(nickname)
                .email(username + "@nate.com")
                .password(passwordEncoder.encode("1234"))
                .role("USER")
                .status(UserStatus.일반회원)
                .build();
    }

    public User newCompanyUser(String username, String nickname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .nickname(nickname)
                .email(username + "@nate.com")
                .password(passwordEncoder.encode("1234"))
                .role("COMPNAY")
                .status(UserStatus.인증대기)
                .build();
    }

}
