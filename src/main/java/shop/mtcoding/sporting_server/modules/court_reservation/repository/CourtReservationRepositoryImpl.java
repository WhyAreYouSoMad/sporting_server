package shop.mtcoding.sporting_server.modules.court_reservation.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.court_reservation.entity.QCourtReservation;
import shop.mtcoding.sporting_server.topic.user_reservation.dto.QReservationListOutDTO;
import shop.mtcoding.sporting_server.topic.user_reservation.dto.ReservationListOutDTO;

@Repository
@RequiredArgsConstructor
@Transactional
public class CourtReservationRepositoryImpl implements CourtReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationListOutDTO> findReservationListByUserId(Long principalUserId) {
        QCourtReservation qCourtReservation = QCourtReservation.courtReservation;

        JPAQuery<ReservationListOutDTO> query = jpaQueryFactory
                .select(new QReservationListOutDTO(qCourtReservation.id,
                        qCourtReservation.courtPayment.stadiumCourt.title, qCourtReservation.reservationDate,
                        qCourtReservation.reservationTime, qCourtReservation.courtPayment.paymentAmount))
                .from(qCourtReservation)
                .where(qCourtReservation.user.id.eq(principalUserId));

        return query.fetch();
    }
}
