package shop.mtcoding.sporting_server.topic.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.topic.user.dto.UserRequest;
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    // @Vaild 붙여서 처리 가능
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        String jwt = userService.로그인(loginDTO);
        // userdetailsService 활용하지 않은 코드
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body(new ResponseDto<>());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> userCheck(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Long principalId = myUserDetails.getUser().getId();
        String role = myUserDetails.getUser().getRole();
        if (principalId != id) {
            return ResponseEntity.badRequest().body(" 올바른 접근이 아닙니다");
        }
        return ResponseEntity.ok().body(id + " : " + role);
    }

    @GetMapping("/a/user")
    public ResponseEntity<?> userDetail(Authentication authentication) {
        UserResponse.UserDetailOutDto userDetailOutDto = userService.getUser(authentication);
        return ResponseEntity.ok().body(new ResponseDto<>().data(userDetailOutDto));
    }
}
