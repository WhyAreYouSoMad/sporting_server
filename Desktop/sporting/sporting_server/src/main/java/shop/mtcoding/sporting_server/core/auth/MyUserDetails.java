package shop.mtcoding.sporting_server.core.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Getter
public class MyUserDetails implements UserDetails {

    private MyLoginUser user;

    public MyUserDetails(MyLoginUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_" + user.getRole());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // User status enum값 : 인증대기, 인증완료, 휴먼상태, 탈퇴계정
        // false로 return 되면 접근을 하지 못하는데 활용한다고 함
        // 따라서, 탈퇴계정만 지정

        String status = user.getStatus().name();
        boolean res;
        if (status.equals("탈퇴계정")) {
            res = false;
        } else {
            res = true;
        }

        return res;
    }
}
