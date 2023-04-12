package shop.mtcoding.sporting_server.modules.paymenttype.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.paymenttype.entity.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer>, PaymentTypeCustomRepository {

}
