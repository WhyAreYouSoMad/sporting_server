package shop.mtcoding.sporting_server.topic.stadium;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.core.util.StadiumUtils;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse.StadiumRegistrationOutDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping("/company/stadiums")
    public ResponseEntity<?> save(
            @RequestBody @Valid StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO,
            BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        StadiumRegistrationOutDTO stadiumRegistrationOutDTO = stadiumService.save(myUserDetails.getUser().getId(),
                stadiumRegistrationInDTO);

        return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumRegistrationOutDTO));
    }

    @GetMapping("/user/stadiums")
    public ResponseEntity<?> findAllList(String keyword) {
        StadiumUtils.keywordValidiationCheck(keyword);
        List<StadiumListOutDTO> stadiumListOutDTO = stadiumService.findKeywordList(keyword);

        return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumListOutDTO));
    }

    @GetMapping("/company/mystadiums")
    public ResponseEntity<?> findAllMyList(String keyword, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        StadiumUtils.keywordValidiationCheck(keyword);

        List<StadiumMyListOutDTO> stadiumMyListOutDTO = stadiumService
                .findKeywordMyList(myUserDetails.getUser().getId(), keyword);

        return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumMyListOutDTO));
    }
}
