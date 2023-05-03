package shop.mtcoding.sporting_server.topic.user_reservation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.user_reservation.dto.ReservationListOutDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserResController {

    private final UserResService userResService;

    @GetMapping("/user/reservations")
    public ResponseEntity<?> findAllList(@AuthenticationPrincipal MyUserDetails myUserDetails)
            throws JsonProcessingException {

        List<ReservationListOutDTO> reservationListOutDTO = userResService
                .getReservationList(myUserDetails.getUser().getId());

        return ResponseEntity.ok().body(new ResponseDto<>().data(reservationListOutDTO));
    }
}
