package shop.mtcoding.sporting_server.topic.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    // GetMapping, PostMapping, PutMapping, DeleteMapping
    @PostMapping("/user/payments/form/{id}")
    public ResponseEntity<?> paymentForm(@PathVariable Long id, @RequestBody PaymentRequest.FormInDTO formInDTO) {
        PaymentResponse.FormOutDTO formOutDTO = paymentService.getForm(id, formInDTO);

        return ResponseEntity.ok().body(new ResponseDto<>().data(formOutDTO));
    }

    @PostMapping("/user/payments")
    public ResponseEntity<?> payment(Long courtId, String resDate, String resTime,
            @RequestBody PaymentRequest.ReceiptInDTO ReceiptDTO) throws JsonProcessingException {
        System.out.println("테스트 courtId : " + courtId);
        System.out.println("테스트 resDate : " + resDate);
        System.out.println("테스트 resTime : " + resTime);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        System.out.println("테스트 : ReceiptDTO " + om.writeValueAsString(ReceiptDTO));

        return ResponseEntity.ok().body(new ResponseDto<>());
    }
}
