package shop.mtcoding.sporting_server.topic.company.dto;

import lombok.*;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.core.util.MyDateUtils;
import shop.mtcoding.sporting_server.modules.user.entity.User;

import java.time.LocalDateTime;

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
}