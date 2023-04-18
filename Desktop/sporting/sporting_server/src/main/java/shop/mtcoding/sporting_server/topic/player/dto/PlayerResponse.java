package shop.mtcoding.sporting_server.topic.player.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.util.MyDateUtils;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class PlayerResponse {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class JoinOutDto {
        private Long id;
        private String nickname;
        private String email;
        private String role;
        private String createdAt;

        public JoinOutDto(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.createdAt = MyDateUtils.toStringFormat(user.getCreatedAt());
        }
    }
}
