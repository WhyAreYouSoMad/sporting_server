package shop.mtcoding.sporting_server.topic.stadium;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.exception.Exception403;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse.StadiumRegistrationOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final SportCategoryRepository sportCategoryRepository;

    public StadiumRegistrationOutDTO save(Long id, StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO) {

        CompanyInfo companyInfoPS = companyInfoRepository.findByUserId(id).orElseThrow(() -> {
            throw new Exception400("유저정보를 업데이트해야 작성 가능합니다.");
        });

        SportCategory sportCategoryPS = sportCategoryRepository.findBySport(stadiumRegistrationInDTO.getCategory())
                .orElseThrow(() -> {
                    throw new Exception400("해당 스포츠 카테고리가 존재하지 않습니다.");
                });

        Stadium stadiumPS = stadiumRepository
                .save(stadiumRegistrationInDTO.toEntity(companyInfoPS, stadiumRegistrationInDTO, sportCategoryPS));

        return new StadiumRegistrationOutDTO(stadiumPS);
    }

    public List<StadiumListOutDTO> findKeywordList(String keyword) {
        List<StadiumListOutDTO> stadiumListOut = stadiumRepository.findStadiumListBySportKeyword(keyword);

        return stadiumListOut;
    }

    public List<StadiumMyListOutDTO> findKeywordMyList(Long pricipalCompanyId, String keyword) {
        List<StadiumMyListOutDTO> stadiumMyListOut = stadiumRepository.findStadiumMyListBySportKeyword(keyword,
                pricipalCompanyId);

        return stadiumMyListOut;
    }

    public StadiumUpdateFomrOutDTO getUpdateForm(Long id, Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 경기장입니다.");
        });
        if (!stadium.getCompanyInfo().getUser().getId().equals(id)) {
            throw new Exception403("수정 권한이 없습니다.");
        }

        StadiumUpdateFomrOutDTO stadiumUpdateFomrOutDTO = stadiumRepository.findByStadiumId(stadiumId);
        stadiumUpdateFomrOutDTO.setCourt(stadiumRepository.findByStadiumIdForCourtList(stadiumId));

        return stadiumUpdateFomrOutDTO;
    }

}
