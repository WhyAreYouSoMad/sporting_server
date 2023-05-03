package shop.mtcoding.sporting_server.modules.court_payment.entity;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.PaymentType;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtPaymentStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "court_payment_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class CourtPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    @Column(name = "id")
    private Long id;

    @Comment("결제 방식 ex)카드결제/계좌이체/휴대폰결제/포인트")
    @Column(name = "payment_type")
    private String paymentType;

    @Comment("결제 총 금액")
    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("플레이어 정보테이블")
    @JoinColumn(name = "player_info_id")
    private PlayerInfo playerInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("기업 정보테이블")
    @JoinColumn(name = "company_info_id")
    private CompanyInfo companyInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("코트 테이블")
    @JoinColumn(name = "stadium_court_id")
    private StadiumCourt stadiumCourt;

    @Lob
    @Comment("결제 정보 고유데이터")
    @Column(name = "origin_data")
    private String originData;

    @Comment("해당 결제 receiptId")
    @Column(name = "receipt_id")
    private String receiptId;

    @Comment("해당 결제 구매일시")
    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;

    @Comment("해당 결제 요청일시")
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "created_at")
    @Comment("결제 내역 생성일")
    private LocalDateTime createdAt;

    @NonNull
    @Comment("결제 상태 (ex. 결제완료, 정산완료 / 결제취소, 환불완료)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourtPaymentStatus status;
}
