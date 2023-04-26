package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.adminuser.dto.stadium.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.SportCategoryDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumDetailOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

// QueryDsl 매핑 인터페이스
public interface StadiumCustomRepository {
    public List<StadiumMyListOutDTO> findStadiumMyListBySportKeyword(String sportKeyword, Long principalCompanyId);

    List<CourtResponseDTO> findCourtsByStadiumId(@Param("stadiumId") Long stadiumId);

    StadiumUpdateFomrOutDTO findByStadiumId(@Param("stadiumId") Long stadiumId);

    StadiumDetailOutDTO findByStadiumId2(@Param("categoryId") Long categoryId);

    SportCategoryDTO findCategoryByStadiumId(@Param("stadiumId") Long stadiumId);

    // Page<AdminStadiumListOutDto> findAllForAdmin(Pageable pageable);
    // List<StadiumCourtDTO> findStadiumCourtByStadiumId(@Param("stadiumId") Long
    // stadiumId);
}
