package shop.mtcoding.sporting_server.topic.user;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse.LoginOutDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    // @Vaild 붙여서 처리 가능
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        ArrayList loginOutList = userService.로그인(loginDTO);
        String jwt = (String) loginOutList.get(0);
        UserResponse.LoginOutDto loginOutDto = new LoginOutDto((Long) loginOutList.get(1),
                (String) loginOutList.get(2));

        // userdetailsService 활용하지 않은 코드
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt)
                .body(new ResponseDto<>().data(loginOutDto));
    }

    @GetMapping("/a")
    public ResponseEntity<?> userCheck(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        UserResponse.LoginOutDto loginOutDto = new LoginOutDto(myUserDetails.getUser().getId(),
                myUserDetails.getUser().getRole());
        return ResponseEntity.ok().body(new ResponseDto<>().data(loginOutDto));
    }

    @GetMapping("/a/user")
    public ResponseEntity<?> userDetail(Authentication authentication) {
        UserResponse.UserDetailOutDto userDetailOutDto = userService.getUser(authentication);
        return ResponseEntity.ok().body(new ResponseDto<>().data(userDetailOutDto));
    }
}
