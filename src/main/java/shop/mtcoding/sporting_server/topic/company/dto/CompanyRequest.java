package shop.mtcoding.sporting_server.topic.company.dto;

import lombok.*;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CompanyRequest {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class JoinInDTO {
        @Email(message = "올바른 email 형식이 아닙니다")
        @NotEmpty(message = "email을 입력해주세요")
        private String email;
        @NotEmpty(message = "password를 입력해주세요")
        private String password;
        private String passwordCon;
        private String role;

        public User toEntity() {
            return User.builder()
                    .nickname(email)
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
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class UpdateInDTO {

        private String nickname;
        @NotEmpty(message = "password를 입력해주세요")
        private String password;

        private String tel;
        private String businessAddress;
        private String businessNumber;
        private CompanyFileDTO sourceFile;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class CompanyFileDTO {
            private Long id;
            private String fileBase64;
        }

    }

}