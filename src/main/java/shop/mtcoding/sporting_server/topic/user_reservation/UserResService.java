package shop.mtcoding.sporting_server.topic.user_reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.court_reservation.repository.CourtReservationRepository;
import shop.mtcoding.sporting_server.topic.user_reservation.dto.ReservationListOutDTO;

@RequiredArgsConstructor
@Service
public class UserResService {

    private final CourtReservationRepository courtReservationRepository;

    public List<ReservationListOutDTO> getReservationList(Long id) {
        return courtReservationRepository.findReservationListByUserId(id);
    }

}
