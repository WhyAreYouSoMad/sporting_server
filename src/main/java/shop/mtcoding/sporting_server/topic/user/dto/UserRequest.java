package shop.mtcoding.sporting_server.topic.user.dto;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {
    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;

        // save하는게 아니라 toEntity 필요 없음

        public LoginDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public LoginDTO() {
        }

    }
}
