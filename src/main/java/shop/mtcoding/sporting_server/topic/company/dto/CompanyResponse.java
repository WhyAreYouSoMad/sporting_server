package shop.mtcoding.sporting_server.topic.company.dto;

import lombok.*;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.core.util.MyDateUtils;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.user.entity.User;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public class CompanyResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinDTO {
        private Long id;
        private String email;
        private String role;
        private String status;
        private String createdAt; // 포맷해서 던짐 (util)

        public JoinDTO(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.createdAt = MyDateUtils.toStringFormat(user.getCreatedAt());
            this.status = user.getStatus().toString();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class UpdateOutDTO {

        private Long id;
        private String nickname;
        private String password;

        private String tel;
        private String businessAdress;
        private String businessNumber;
        private CompanyFileDTO companyFile = new CompanyFileDTO();

        public UpdateOutDTO(Long id, String nickname, String password, String tel, String businessAdress,
                String businessNumber, CompanyFileDTO companyFile) {
            this.id = id;
            this.nickname = nickname;
            this.password = password;
            this.tel = tel;
            this.businessAdress = businessAdress;
            this.businessNumber = businessNumber;
            this.companyFile = companyFile;
        }

        public UpdateOutDTO(User userPS, CompanyInfo companyInfoPS, ProfileFile companyProfileFilePS) {
            this.id = userPS.getId();
            this.nickname = userPS.getNickname();
            this.password = userPS.getPassword();
            this.tel = companyInfoPS.getTel();
            this.businessAdress = companyInfoPS.getBusinessAdress();
            this.businessNumber = companyInfoPS.getBusinessNumber();
            this.companyFile.id = companyProfileFilePS.getId();
            this.companyFile.fileName = companyProfileFilePS.getFileName();
            this.companyFile.fileBase64 = companyProfileFilePS.getFileUrl();
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class CompanyFileDTO {
            private Long id;
            private String fileName;
            private String fileBase64;

        }

    }
}