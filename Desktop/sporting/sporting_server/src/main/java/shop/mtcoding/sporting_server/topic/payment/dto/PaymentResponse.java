package shop.mtcoding.sporting_server.topic.payment.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public class PaymentResponse {

    @Setter
    @Getter
    @NoArgsConstructor
    public static class formOutDTO {
        private String stadiumName;
        private String courtContent;
        private LocalDate reservationDate;
        private String reservationTime;
        private Integer paymentPrice;

    }
}
