package shop.mtcoding.sporting_server.topic.payment.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public class PaymentResponse {

    @Setter
    @Getter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class FormOutDTO {
        private Long courtId;
        private String stadiumName;
        private String courtContent;
        private LocalDate reservationDate;
        private String reservationTime;
        private Integer paymentPrice;

        // 결제페이지 ResponseDTO
        public FormOutDTO(StadiumCourt stadiumCourtPS, PaymentRequest.FormInDTO forInDTO) {
            this.courtId = stadiumCourtPS.getId();
            this.stadiumName = stadiumCourtPS.getStadium().getName();
            this.courtContent = stadiumCourtPS.getContent();
            this.paymentPrice = stadiumCourtPS.getCourtPrice();
            this.reservationDate = forInDTO.getReservationDate();
            this.reservationTime = forInDTO.getTime();
        }

        // 테스트용
        public FormOutDTO(String stadiumName, String courtContent, LocalDate reservationDate, String reservationTime,
                Integer paymentPrice) {
            this.stadiumName = stadiumName;
            this.courtContent = courtContent;
            this.reservationDate = reservationDate;
            this.reservationTime = reservationTime;
            this.paymentPrice = paymentPrice;
        }

    }
}
