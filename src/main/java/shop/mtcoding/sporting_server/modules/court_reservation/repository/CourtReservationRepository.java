package shop.mtcoding.sporting_server.modules.court_reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.court_reservation.entity.CourtReservation;

public interface CourtReservationRepository
                extends JpaRepository<CourtReservation, Long>, CourtReservationCustomRepository {

        @Query("select c from CourtReservation c where c.reservationDate = :reservationDate and c.reservationTime = :reservationTime")
        Optional<CourtReservation> findByDateAndTime(@Param("reservationDate") LocalDate reservationDate,
                        @Param("reservationTime") String reservationTime);

        @Query("SELECT cr.reservationTime FROM CourtReservation cr WHERE cr.stadiumCourt.id = :stadiumCourtId AND cr.reservationDate = :reservationDate")
        List<String> findReservationTimesByStadiumCourtIdAndReservationDate(
                        @Param("stadiumCourtId") Long stadiumCourtId,
                        @Param("reservationDate") LocalDate reservationDate);
        // List<CourtReservation> findByStadiumCourtIdAndReservationDate(Long
        // stadiumCourtId,
        // LocalDate reservationDate);

}
