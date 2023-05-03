package shop.mtcoding.sporting_server.topic.user_reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReservationListOutDTO {

    private Long id;
    private String name;
    private LocalDateTime reservationDateTime;
    private Integer courtPrice;

    @QueryProjection
    public ReservationListOutDTO(Long id, String name, LocalDate reservationDate, String reservationTime,
            Integer paymentAmount) {
        this.id = id;
        this.name = name;
        LocalTime time = LocalTime.parse(reservationTime, DateTimeFormatter.ofPattern("H"));
        LocalDateTime dateTime = reservationDate.atTime(time);
        this.reservationDateTime = dateTime;
        this.courtPrice = paymentAmount;
    }
}
