package shop.mtcoding.sporting_server.modules.court_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.court_reservation.entity.CourtReservation;

public interface CourtReservationRepository
                extends JpaRepository<CourtReservation, Long>, CourtReservationCustomRepository {

}
