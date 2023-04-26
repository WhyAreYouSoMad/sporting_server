package shop.mtcoding.sporting_server.modules.stadium_court.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumCourtDTO;

public interface StadiumCourtCustomRepository {

    List<StadiumCourtDTO> findStadiumCourtByStadiumId(@Param("stadiumId") Long stadiumId);

}
