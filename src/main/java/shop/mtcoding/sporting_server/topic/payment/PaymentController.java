package shop.mtcoding.sporting_server.topic.payment;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/user/payments")
    public ResponseEntity<?> payment(Long courtId, String resDate, String resTime,
            @RequestBody PaymentRequest.ReceiptInDTO ReceiptDTO, @AuthenticationPrincipal MyUserDetails myUserDetails)
            throws JsonProcessingException {
        System.out.println("테스트 courtId : " + courtId);
        System.out.println("테스트 resDate : " + resDate);
        System.out.println("테스트 resTime : " + resTime);
        System.out.println("테스트 ReceiptDTO : " + ReceiptDTO.getData().getMethod());
        System.out.println("테스트 myUserDetails.getUser().getId() : " + myUserDetails.getUser().getId());
        paymentService.paymentAndReservation(courtId, resDate, resTime, ReceiptDTO, myUserDetails.getUser().getId());

        return ResponseEntity.ok().body(new ResponseDto<>());
    }
}
