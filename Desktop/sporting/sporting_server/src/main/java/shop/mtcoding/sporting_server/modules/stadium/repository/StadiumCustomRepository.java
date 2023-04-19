package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

// QueryDsl 매핑 인터페이스
public interface StadiumCustomRepository {

    List<CourtResponseDTO> findCourtsByStadiumId(@Param("stadiumId") Long stadiumId);

    StadiumUpdateFomrOutDTO findByStadiumId(@Param("stadiumId") Long stadiumId);
}
