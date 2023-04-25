package shop.mtcoding.sporting_server.topic.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class UserResponse {

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class UserDetailOutDto {
        private Long id;
        private String nickname;
        private String role;

        public UserDetailOutDto(User user) {
            this.id = user.getId();
            if (user.getNickname() != null) {
                this.nickname = user.getNickname();
            } else {
                this.nickname = user.getEmail();
            }
            this.role = user.getRole();
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class LoginOutDto {
        private Long id;
        private String role;
    }
}
