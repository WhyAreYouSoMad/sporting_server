package shop.mtcoding.sporting_server.topic.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDTO;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.topic.user.dto.UserRequest;
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        // select 됨
        UserResponse.JoinDto data = userService.회원가입(joinDTO);

        // select 안됨
        ResponseDTO<?> responseDTO = new ResponseDTO<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/login")
    // @Vaild 붙여서 처리 가능
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        String jwt = userService.로그인(loginDTO);

        // userdetailsService 활용하지 않은 코드
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body("로그인완료");
    }

    @GetMapping("/users/1")
    public ResponseEntity<?> userCheck(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        Long id = myUserDetails.getUser().getId();
        String role = myUserDetails.getUser().getRole();

        return ResponseEntity.ok().body(id + " : " + role);
    }

    @GetMapping("/")
    public ResponseEntity<?> hello() {

        return ResponseEntity.ok().body("ok");
    }

}
