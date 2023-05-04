package shop.mtcoding.sporting_server.modules.court_reservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtReservationStatus;
import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "court_reservation_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class CourtReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("유저 테이블")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("코트 결제 테이블")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_payment_id", unique = true)
    private CourtPayment courtPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("코트 테이블")
    @JoinColumn(name = "stadium_court_id")
    private StadiumCourt stadiumCourt;

    @NonNull
    @Comment("예약 날짜")
    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @NonNull
    @Comment("예약 시간")
    @Column(name = "reservation_time")
    private String reservationTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NonNull
    @Comment("예약 상태 (대기,승낙,거절)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourtReservationStatus status;
}
