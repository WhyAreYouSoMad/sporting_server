package shop.mtcoding.sporting_server.core.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;

@Getter
@Setter
@NoArgsConstructor
public class MyLoginUser {
    private Long id;
    private String role;
    private String username;
    private String email;
    private String password;
    private UserStatus status;

    @Builder
    public MyLoginUser(Long id, String role, String email) {
        this.id = id;
        this.role = role;
        this.email = email;
    }
}
