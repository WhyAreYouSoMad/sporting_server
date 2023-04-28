package shop.mtcoding.sporting_server.topic.stadium_court;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumCourtOutDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StadiumCourtController {

    private final StadiumCourtService stadiumCourtService;

    @PostMapping("/company/stadiums/{stadiumId}/court")
    public ResponseEntity<?> save(@RequestBody @Valid StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO,
            @AuthenticationPrincipal MyUserDetails myUserDetails, Stadium stadiumPS, @PathVariable Long stadiumId)
            throws IOException {

        StadiumCourtOutDTO stadiumCourtOutDTO = stadiumCourtService.save(stadiumCourtInDTO,
                stadiumId, myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(new ResponseDto<>().data(stadiumCourtOutDTO));
    }

}
