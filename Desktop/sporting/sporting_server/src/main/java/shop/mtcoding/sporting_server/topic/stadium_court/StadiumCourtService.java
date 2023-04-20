package shop.mtcoding.sporting_server.topic.stadium_court;

import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumCourtOutDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumCourtService {
    private final StadiumCourtRepository stadiumCourtRepository;
    private final StadiumRepository stadiumRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final FileInfoRepository fileInfoRepository;

    public StadiumCourtOutDTO save(StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO,
            Long id, Long stadiumId) {
        CompanyInfo companyInfoPS = companyInfoRepository.findByUserId(id).orElseThrow(() -> {
            throw new Exception400("유저정보를 업데이트해야 작성 가능합니다.");
        });
        Stadium stadiumPS = stadiumRepository.findById(stadiumId).orElseThrow(() -> {
            throw new Exception400("경기장 정보를 업데이트해야 작성 가능합니다.");
        });

        StadiumCourt stadiumCourtPS = stadiumCourtRepository.save(stadiumCourtInDTO.toEntity(stadiumPS));

        return new StadiumCourtOutDTO(stadiumCourtPS);
    }
}
