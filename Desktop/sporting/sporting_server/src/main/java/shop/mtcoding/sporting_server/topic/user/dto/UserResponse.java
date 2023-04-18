package shop.mtcoding.sporting_server.topic.user.dto;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.util.MyDateUtils;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class UserResponse {

    @Setter
    @Getter
    public static class UserDetailOutDto {
        private Long id;
        private String email;
        private String role;

        public UserDetailOutDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.role = user.getRole();
        }

    }
}
