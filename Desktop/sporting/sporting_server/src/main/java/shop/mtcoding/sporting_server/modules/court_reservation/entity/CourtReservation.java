package shop.mtcoding.sporting_server.modules.court_reservation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import shop.mtcoding.sporting_server.core.enums.field.status.CourtReservationStatus;
import shop.mtcoding.sporting_server.modules.board.entity.Board;
import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Getter
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

    @NonNull
    @Comment("공고 테이블")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Comment("코트 결제 테이블")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_payment_id")
    private CourtPayment stadiumPayment;

    @NonNull
    @Comment("경기 시작시간")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NonNull
    @Comment("경기 종료시간")
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NonNull
    @Comment("예약 상태 (대기,승낙,거절)")
    @Column(name = "status")
    private CourtReservationStatus status;
}
