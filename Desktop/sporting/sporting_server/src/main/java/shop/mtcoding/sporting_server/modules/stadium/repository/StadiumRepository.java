package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;

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

}
