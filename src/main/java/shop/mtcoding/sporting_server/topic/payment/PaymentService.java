package shop.mtcoding.sporting_server.topic.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtPaymentStatus;
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
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final CourtPaymentRepository courtPaymentRepository;
    private final StadiumCourtRepository stadiumCourtRepository;
    private final CourtReservationRepository courtReservationRepository;
    private final PlayerInfoRepository playerInfoRepository;
    private final CompanyInfoRepository companyInfoRepository;

    // public PaymentResponse.FormOutDTO getForm(Long stadiumCourtId,
    // PaymentRequest.FormInDTO forInDTO) {
    // StadiumCourt stadiumCourtPS =
    // stadiumCourtRepository.findById(stadiumCourtId).orElseThrow(() -> {
    // throw new Exception400("해당 코트가 존재하지 않습니다.");
    // });

    // courtReservationRepository.findByDateAndTime(forInDTO.getReservationDate(),
    // forInDTO.getTime())
    // .ifPresent(reservation -> {
    // throw new Exception400("해당 일시에 예약이 불가능합니다.");
    // });

    // PaymentResponse.FormOutDTO formOutDTO = new FormOutDTO(stadiumCourtPS,
    // forInDTO);

    // return formOutDTO;
    // }
    public void getForm(Long courtId, String resDate, String resTime, PaymentRequest.ReceiptInDTO ReceiptDTO, Long id)
            throws JsonProcessingException {
        String restApiKey = "643f9df8755e27001ae57d0c";
        String privateKey = "Xh68nK2FMKk5JZSoPEOzuOA3d4N+nR0t3CGgGo7Jf/Y=";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate resDateParse = LocalDate.parse(resDate, formatter);
        String receiptId = ReceiptDTO.getData().getReceiptId();
        Integer price = ReceiptDTO.getData().getPrice();

        // 코트가 존재하지 않으면 결제 취소
        Optional<StadiumCourt> stadiumCourtPS = stadiumCourtRepository.findById(courtId);
        if (!stadiumCourtPS.isPresent()) {
            BootPayPaymentUtils.cancelPayment("존재하지 않는 코트", receiptId);
        }

        // 화면에서는 닫혀있는 예약 불가능한 날짜를 포스트맨으로 요청하였을 때 결제 취소
        Optional<CourtReservation> courtReservationPS = courtReservationRepository.findByDateAndTime(resDateParse,
                resTime);
        if (courtReservationPS.isPresent()) {
            BootPayPaymentUtils.cancelPayment("PrivateKey 값 확인필요", receiptId);
        }

        System.out.println("테스트 1: " + stadiumCourtPS.get().getCourtPrice());
        System.out.println("테스트 2: " + price);
        if (!stadiumCourtPS.get().getCourtPrice().equals(price)) {
            System.out.println("테스트 : 11");
            BootPayPaymentUtils.cancelPayment("결제 요청가격 확인필요", receiptId);
        }

        PlayerInfo playerInfoPS = playerInfoRepository.findByUserId(id).orElseThrow(() -> {
            throw new Exception400("유저 정보가 존재하지 않습니다 : 로그인 토큰 로직 재확인 필요");
        });
        CompanyInfo companyInfoPS = stadiumCourtPS.get().getStadium().getCompanyInfo();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        CourtPayment courtPayment = CourtPayment
                .builder()
                .paymentType(ReceiptDTO.getData().getMethod())
                .originData(om.writeValueAsString(ReceiptDTO))
                .playerInfo(playerInfoPS)
                .companyInfo(companyInfoPS)
                .paymentAmount(price)
                .stadiumCourt(stadiumCourtPS.get())
                .receiptId(receiptId)
                .status(CourtPaymentStatus.결제완료)
                .purchasedAt(ReceiptDTO.getData().getPurchasedAt().toLocalDateTime())
                .requestedAt(ReceiptDTO.getData().getRequestedAt().toLocalDateTime())
                .createdAt(LocalDateTime.now())
                .build();
        courtPaymentRepository.save(courtPayment);

        CourtReservation courtReservation = CourtReservation
                .builder()
                .user(playerInfoPS.getUser())
                .reservationDate(resDateParse)
                .reservationTime(resTime)
                .courtPayment(courtPayment)
                .createdAt(LocalDateTime.now())
                .status(CourtReservationStatus.승낙)
                .build();
        courtReservationRepository.save(courtReservation);

        // PaymentResponse.FormOutDTO formOutDTO = new FormOutDTO(stadiumCourtPS,
        // forInDTO);

    }
}
