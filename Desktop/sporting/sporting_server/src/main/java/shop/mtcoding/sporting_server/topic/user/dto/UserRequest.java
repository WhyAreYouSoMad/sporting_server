package shop.mtcoding.sporting_server.topic.user.dto;

<<<<<<< HEAD
=======
import java.time.LocalDateTime;

>>>>>>> 25384157b52fc64a7ac53ca6956c64dae0473408
import lombok.Getter;
import lombok.Setter;

public class UserRequest {
    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;

        // save하는게 아니라 toEntity 필요 없음
    }
}
