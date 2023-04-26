package shop.mtcoding.sporting_server.topic.company.dto;

import lombok.*;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
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

        // private String nickname;
        // private String password;

        private String tel;
        private String businessAdress;
        // private FileInfo fileInfo;/

        // public User toEntity() {
        // return User.builder()
        // .nickname(nickname)
        // .password(password)
        // .updatedAt(LocalDateTime.now())
        // .build();
        // }

        public CompanyInfo toEntity2() {
            return CompanyInfo.builder()
                    .tel(tel)
                    .businessAdress(businessAdress)
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

}