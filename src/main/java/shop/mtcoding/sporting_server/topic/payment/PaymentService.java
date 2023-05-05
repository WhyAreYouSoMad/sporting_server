package shop.mtcoding.sporting_server.topic.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtReservationStatus;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.util.BootPayPaymentUtils;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;
import shop.mtcoding.sporting_server.modules.court_payment.repository.CourtPaymentRepository;
import shop.mtcoding.sporting_server.modules.court_reservation.entity.CourtReservation;
import shop.mtcoding.sporting_server.modules.court_reservation.repository.CourtReservationRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest.ReceiptInDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final CourtPaymentRepository courtPaymentRepository;
    private final StadiumCourtRepository stadiumCourtRepository;
    private final CourtReservationRepository courtReservationRepository;
    private final PlayerInfoRepository playerInfoRepository;

    public void paymentAndReservation(Long courtId, String resDate, String resTime,
            PaymentRequest.ReceiptInDTO receiptDTO, Long id)
            throws JsonProcessingException {

        // OffsetDateTime타입으로 받은 날짜 정보를 LocalDate타입으로 저장하기 위한 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate resDateParse = LocalDate.parse(resDate, formatter);
        // DB에 저장할 receiptId 및 price 정보
        String receiptId = receiptDTO.getData().getReceiptId();
        Integer price = receiptDTO.getData().getPrice();

        // 코트가 존재하지 않으면 결제 취소
        Optional<StadiumCourt> stadiumCourtPS = stadiumCourtRepository.findById(courtId);
        if (!stadiumCourtPS.isPresent()) {
            BootPayPaymentUtils.cancelPayment("존재하지 않는 코트", receiptId);
        }

        // 화면에서는 닫혀있는 예약 불가능한 날짜를 포스트맨으로 요청하였을 때 결제 취소
        Optional<CourtReservation> courtReservationPS = courtReservationRepository.findByDateAndTime(resDateParse,
                resTime);
        if (courtReservationPS.isPresent()) {
            BootPayPaymentUtils.cancelPayment("예약할 수 없는 날짜", receiptId);
        }

        if (!stadiumCourtPS.get().getCourtPrice().equals(price)) {
            BootPayPaymentUtils.cancelPayment("결제 요청가격 확인필요", receiptId);
        }

        PlayerInfo playerInfoPS = playerInfoRepository.findByUserId(id).orElseThrow(() -> {
            throw new Exception400("유저 정보가 존재하지 않습니다 : 로그인 토큰 로직 재확인 필요");
        });
        CompanyInfo companyInfoPS = stadiumCourtPS.get().getStadium().getCompanyInfo();
        System.out.println("테스트 0 : ");
        // 결제 정보 DB 저장
        CourtPayment courtPayment = ReceiptInDTO.toPaymentEntity(receiptDTO, playerInfoPS, companyInfoPS,
                stadiumCourtPS.get());
        System.out.println("테스트 1 : ");
        courtPaymentRepository.save(courtPayment);
        System.out.println("테스트 2 : ");

        // 예약 정보 DB 저장
        CourtReservation courtReservation = ReceiptInDTO.toReservationEntity(playerInfoPS, resDateParse, resTime,
                courtPayment, stadiumCourtPS.get());
        System.out.println("테스트 3 : ");
        courtReservationRepository.save(courtReservation);
        System.out.println("테스트 4 : ");
    }
}
