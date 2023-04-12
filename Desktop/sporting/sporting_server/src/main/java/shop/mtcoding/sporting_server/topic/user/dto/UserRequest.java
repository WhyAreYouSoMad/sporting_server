package shop.mtcoding.sporting_server.topic.user.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class UserRequest {
    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private String role;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .status(UserStatus.인증대기)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;

        // save하는게 아니라 toEntity 필요 없음
    }
}
