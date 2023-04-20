package shop.mtcoding.sporting_server.topic.stadium_court;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.topic.stadium.StadiumService;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumCourtOutDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StadiumCourtController {

    private final StadiumCourtService stadiumCourtService;

    @PostMapping("/company/stadiums/court/{stadiumId}")
    public ResponseEntity<?> save(@RequestBody @Valid StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO,
            @AuthenticationPrincipal MyUserDetails myUserDetails, @PathVariable Long stadiumId) {

        StadiumCourtOutDTO stadiumCourtOutDTO = stadiumCourtService.save(stadiumCourtInDTO,
                myUserDetails.getUser().getId(), stadiumId);

        System.out.println("테스트 : " + stadiumCourtOutDTO.getId());
        System.out.println("테스트 : " + stadiumCourtOutDTO.getContent());

        return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumCourtOutDTO));
    }

}
