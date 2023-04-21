package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.SportCategoryDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumCourtDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumDetailDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

public interface StadiumRepository extends JpaRepository<Stadium, Long>, StadiumCustomRepository {

        @Query("SELECT new shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO(st.id, ct.sport, st.name, sct.courtPrice) "
                        + "FROM Stadium st "
                        + "INNER JOIN StadiumCourt sct ON sct.stadium.id = st.id "
                        + "INNER JOIN st.category ct "
                        + "WHERE ct.sport = :sportKeyword "
                        + "GROUP BY st.id")
        List<StadiumListOutDTO> findStadiumListBySportKeyword(@Param("sportKeyword") String sportKeyword);

        @Query("SELECT new shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO(st.id, ct.sport, st.name) "
                        + "FROM Stadium st "
                        + "INNER JOIN st.category ct "
                        + "WHERE ct.sport = :sportKeyword and st.companyInfo.user.id = :pricipalCompanyId "
                        + "GROUP BY st.id")
        List<StadiumMyListOutDTO> findStadiumMyListBySportKeyword(@Param("sportKeyword") String sportKeyword,
                        @Param("pricipalCompanyId") Long pricipalCompanyId);

        // @Query("SELECT new
        // shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO(st.id,
        // st.name, st.address, st.status, st.startTime, st.endTime) "
        // + "FROM Stadium st "
        // + "WHERE st.id = :stadiumId")
        // StadiumUpdateFomrOutDTO findByStadiumId(@Param("stadiumId") Long stadiumId);

        // @Query("SELECT new
        // shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO(sc.id,
        // sc.title, sc.content, sc.capacity, sc.courtPrice, c.sport) "
        // + "FROM StadiumCourt sc " + "INNER JOIN sc.stadium s " + "INNER JOIN
        // s.category c "
        // + "WHERE s.id = :stadiumId")
        // List<CourtResponseDTO> findByStadiumIdForCourtList(@Param("stadiumId") Long
        // stadiumId);
}
