package shop.mtcoding.sporting_server.topic.player.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class PlayerRequest {
    @Getter
    @Setter
    @EqualsAndHashCode
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinInDTO {
        @Email(message = "올바른 email 형식이 아닙니다")
        @NotEmpty(message = "email을 입력해주세요")
        private String email;
        @NotEmpty(message = "password를 입력해주세요")
        private String password;
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

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class PlayerUpdateInDTO {
        private String nickname;
        private String password;

        private String tel;
        private String gender;
        private String age;
        private String address;
        private PlayerFileDTO sourceFile;

        private List<PlayerFavoriteSportDTO> sportList;

        @EqualsAndHashCode
        @NoArgsConstructor
        @AllArgsConstructor
        @Setter
        @Getter
        public static class PlayerFileDTO {
            private Long id;
            private String fileBase64;
        }

        @EqualsAndHashCode
        @NoArgsConstructor
        @AllArgsConstructor
        @Setter
        @Getter
        public static class PlayerFavoriteSportDTO {
            private Long id;
            private String sport;
        }
    }
}
