package shop.mtcoding.sporting_server.topic.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.modules.court_payment.repository.CourtPaymentRepository;
import shop.mtcoding.sporting_server.modules.court_reservation.repository.CourtReservationRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

}
