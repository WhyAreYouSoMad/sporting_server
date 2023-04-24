package shop.mtcoding.sporting_server.topic.company.dto;


import lombok.*;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;

import java.time.LocalDateTime;

public class CompanyRequest {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class JoinInDTO {

        private String password;
        private String passwordCon;
        private String email;
        private String role;

        public User toEntity() {
            return User.builder()
                    .password(password)
                    .email(email)
                    .role(role)
                    .status(UserStatus.인증대기)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }
}