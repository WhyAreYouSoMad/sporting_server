package shop.mtcoding.sporting_server.topic.stadium;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse.StadiumRegistrationOutDTO;

@RestController
@RequiredArgsConstructor
// @RequestMapping("/api")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping("/company/stadiums")
    public ResponseEntity<?> save(
            @RequestBody @Valid StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO,
            BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        StadiumRegistrationOutDTO stadiumRegistrationOutDTO = stadiumService.save(myUserDetails.getUser().getId(),
                stadiumRegistrationInDTO);

        // return null;
        return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumRegistrationOutDTO));
    }
}
