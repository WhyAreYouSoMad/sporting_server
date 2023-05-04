package shop.mtcoding.sporting_server.modules.court_reservation.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.user_reservation.dto.ReservationListOutDTO;

public interface CourtReservationCustomRepository {

    List<ReservationListOutDTO> findReservationListByUserId(@Param("userId") Long userId);

}
