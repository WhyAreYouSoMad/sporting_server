package shop.mtcoding.sporting_server.jpa.court_reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.sporting_server.config.TextContextConfiguration;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtReservationStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;
import shop.mtcoding.sporting_server.modules.court_reservation.entity.CourtReservation;
import shop.mtcoding.sporting_server.modules.court_reservation.repository.CourtReservationRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
@Import(TextContextConfiguration.class)
public class CourtReservationRepositoryTest {

    @Autowired
    private CourtReservationRepository courtReservationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE court_reservation_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        setUp(null, LocalDate.now(), "9", LocalDateTime.now(), CourtReservationStatus.대기);
    }

    @Test
    @Transactional
    void selectAll() {
        List<CourtReservation> courtReservations = courtReservationRepository.findAll();
        Assertions.assertNotEquals(courtReservations.size(), 0);

        CourtReservation courtReservation = courtReservations.get(0);
        Assertions.assertEquals(courtReservation.getStatus(), CourtReservationStatus.대기);
    }

    @Test
    void selectAndUpdate() {
        var optionalCourtReservation = this.courtReservationRepository.findById(1L);

        if (optionalCourtReservation.isPresent()) {
            var result = optionalCourtReservation.get();
            Assertions.assertEquals(result.getStatus(), CourtReservationStatus.대기);

            var courtReservationSource = CourtReservationStatus.거절;
            result.setStatus(courtReservationSource);

            CourtReservation merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getStatus(), CourtReservationStatus.거절);
        } else {
            Assertions.assertNotNull(optionalCourtReservation.get());
        }
    }

    @Test
    void insertAndDelete() {
        CourtReservation courtReservation = setUp(null, LocalDate.now(), "13", LocalDateTime.now(),
                CourtReservationStatus.승낙);
        Optional<CourtReservation> findCourtReservation = this.courtReservationRepository
                .findById(courtReservation.getId());

        if (findCourtReservation.isPresent()) {
            var result = findCourtReservation.get();
            Assertions.assertEquals(result.getStatus(), CourtReservationStatus.승낙);

            entityManager.remove(courtReservation);

            Optional<CourtReservation> deleteCourtPayment = this.courtReservationRepository
                    .findById(courtReservation.getId());
            if (deleteCourtPayment.isPresent()) {
                Assertions.assertNull(deleteCourtPayment.get());
            }
        } else {
            Assertions.assertNotNull(findCourtReservation.get());
        }
    }

    private CourtReservation setUp(CourtPayment stadiumPayment, LocalDate reservationDate,
            String reservationTime, LocalDateTime createdAt, CourtReservationStatus status) {

        CourtReservation courtReservation = new CourtReservation();

        courtReservation.setUser(setUpUser("ssar", "ssar@naver.com", "1234", "role", LocalDateTime.now(),
                LocalDateTime.now(), UserStatus.인증완료));

        courtReservation.setCourtPayment(stadiumPayment);
        courtReservation.setReservationDate(reservationDate);
        courtReservation.setReservationTime(reservationTime);
        courtReservation.setCreatedAt(createdAt);
        courtReservation.setStatus(status);
        return this.entityManager.persist(courtReservation);
    }

    private User setUpUser(String nickname, String email, String password, String role, LocalDateTime createdAt,
            LocalDateTime updatedAt, UserStatus status) {

        User user = new User();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        user.setStatus(status);

        return this.entityManager.persist(user);
    }

}