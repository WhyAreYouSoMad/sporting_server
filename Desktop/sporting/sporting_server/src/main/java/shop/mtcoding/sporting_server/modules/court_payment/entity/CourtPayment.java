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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtPaymentStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.paymenttype.entity.PaymentType;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;

@Getter
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

    @NonNull
    @Comment("결제 방식 ex)Card, Bank, Mobile …")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type")
    private PaymentType paymentType;

    @NonNull
    @Comment("결제 총금액")
    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("플레이어 정보테이블")
    @JoinColumn(name = "player_info_id")
    private PlayerInfo playerInfo;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("기업 정보테이블")
    @JoinColumn(name = "company_info_id")
    private CompanyInfo companyInfo;

    @Lob
    @Comment("결제 정보 고유데이터")
    @Column(name = "origin_date")
    private String originDate;

    @Column(name = "created_at")
    @Comment("결제 내역 생성일")
    private LocalDateTime createdAt;

    @NonNull
    @Comment("결제 상태 (ex. 결제완료, 정산완료 / 결제실패, 결제취소, 환불완료)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourtPaymentStatus status;
}
