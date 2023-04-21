package shop.mtcoding.sporting_server.topic.stadium_court;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumCourtOutDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumCourtService {
    private final StadiumCourtRepository stadiumCourtRepository;
    private final StadiumRepository stadiumRepository;

    public StadiumCourtOutDTO save(StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO, Long stadiumId, Long id) {
        // Stadium stadiumPS = stadiumRepository.findById(stadiumId).orElseThrow(() -> {
        // throw new Exception400("경기장 정보를 업데이트해야 작성 가능합니다.");
        // });
        // if (stadiumPS.getCompanyInfo().getUser().getId() != id) {
        // throw new Exception400("등록할 권한이 없습니다");
        // }

        Stadium stadiumPS = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new Exception400("경기장 정보를 업데이트해야 작성 가능합니다."));
        if (stadiumPS.getCompanyInfo().getUser().getId() != id) {
            throw new Exception400("등록할 권한이 없습니다");
        }

        StadiumCourt stadiumCourtPS = stadiumCourtRepository
                .save(stadiumCourtInDTO.toEntity(stadiumPS, stadiumCourtInDTO));
        return new StadiumCourtOutDTO(stadiumCourtPS);
    }
}