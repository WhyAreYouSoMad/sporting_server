package shop.mtcoding.sporting_server.topic.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.topic.user.dto.UserRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

<<<<<<< HEAD
=======
    @PostMapping("/joinUser")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        // select 됨
        UserResponse.JoinDto data = userService.회원가입(joinDTO);

        // select 안됨
        ResponseDto<?> responseDTO = new ResponseDto<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }

>>>>>>> 25384157b52fc64a7ac53ca6956c64dae0473408
    @PostMapping("/login")
    // @Vaild 붙여서 처리 가능
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        String jwt = userService.로그인(loginDTO);
        // userdetailsService 활용하지 않은 코드
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body("로그인완료");
    }
}
