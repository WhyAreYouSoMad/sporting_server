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
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentResponse.FormOutDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final CourtPaymentRepository courtPaymentRepository;
    private final StadiumCourtRepository stadiumCourtRepository;
    private final CourtReservationRepository courtReservationRepository;

    public PaymentResponse.FormOutDTO getForm(Long stadiumCourtId, PaymentRequest.FormInDTO forInDTO) {
        StadiumCourt stadiumCourtPS = stadiumCourtRepository.findById(stadiumCourtId).orElseThrow(() -> {
            throw new Exception400("해당 코트가 존재하지 않습니다.");
        });

        courtReservationRepository.findByDateAndTime(forInDTO.getReservationDate(), forInDTO.getTime())
                .ifPresent(reservation -> {
                    throw new Exception400("해당 일시에 예약이 불가능합니다.");
                });

        PaymentResponse.FormOutDTO formOutDTO = new FormOutDTO(stadiumCourtPS, forInDTO);

        return formOutDTO;
    }
}
