package shop.mtcoding.sporting_server.topic.payment.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

public class PaymentRequest {

    @Getter
    @Setter
    public static class formInDTO {
        private LocalDate reservationDate;
        private String time;
    }
}
