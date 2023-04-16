package shop.mtcoding.sporting_server.topic.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentResponse;

@RestController
@RequiredArgsConstructor
// @RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    // GetMapping, PostMapping, PutMapping, DeleteMapping
    @PostMapping("/user/payment/form/{id}")
    public ResponseEntity<?> paymentForm(@PathVariable Long id) {

        return null;
        // return ResponseEntity.ok().body(new ResponseDto<>().data(formOutDTO));
    }
}
