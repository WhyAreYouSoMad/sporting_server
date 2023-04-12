package shop.mtcoding.sporting_server.modules.court_payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;

public interface CourtPaymentRepository extends JpaRepository<CourtPayment, Long>, CourtPaymentCustomRepository {

}
