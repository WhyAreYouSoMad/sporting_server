package shop.mtcoding.sporting_server.topic.player;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerUpdateFormOutDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/joinPlayer")
    public ResponseEntity<?> join(@RequestBody @Valid PlayerRequest.JoinInDTO joinDTO, BindingResult bindingResult) {
        // select 됨
        PlayerResponse.JoinOutDto data = playerService.회원가입(joinDTO);

        // select 안됨
        ResponseDto<?> responseDTO = new ResponseDto<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        System.out.println("디버깅 111: " + System.getenv("HS512_SECRET"));
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> updateForm(@AuthenticationPrincipal MyUserDetails myUserDetails,
            @PathVariable Long id) {
        PlayerUpdateFormOutDTO playerUpdateFormOutDTO = playerService.getUpdateForm(myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(new ResponseDto<>().data(playerUpdateFormOutDTO));
    }

}
