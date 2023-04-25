package shop.mtcoding.sporting_server.topic.player.dto;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class PlayerRequest {
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class JoinInDTO {

        private String password;
        private String email;
        private String role;

        public User toEntity() {
            return User.builder()
                    .nickname(email)
                    .password(password)
                    .email(email)
                    .role(role)
                    .status(UserStatus.일반회원)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;

        // save하는게 아니라 toEntity 필요 없음
    }
}
