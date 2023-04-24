package shop.mtcoding.sporting_server.topic.payment.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PaymentRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class FormInDTO {
        private LocalDate reservationDate;
        private String time;

        public FormInDTO(LocalDate reservationDate, String time) {
            this.reservationDate = reservationDate;
            this.time = time;
        }
    }
}
