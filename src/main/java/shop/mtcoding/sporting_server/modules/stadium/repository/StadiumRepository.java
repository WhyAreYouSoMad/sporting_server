package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.admin.stadium.dto.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.admin.stadium.dto.AdminWaitStadiumListOutDto;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;

public interface StadiumRepository extends JpaRepository<Stadium, Long>, StadiumCustomRepository {

        @Query("select s from Stadium s where s.name = :name")
        Optional<Stadium> findByName(@Param("name") String name);

        @Query("SELECT new shop.mtcoding.sporting_server.admin.stadium.dto.AdminStadiumListOutDto(st.id, st.name, st.tel, ft.fileUrl, st.createdAt) "
                        + "FROM Stadium st "
                        + "INNER JOIN st.category ct "
                        + "INNER JOIN ProfileFile ft on ft.fileInfo.id = st.fileInfo.id "
                        + "WHERE st.status = '운영중'")
        Page<AdminStadiumListOutDto> findAllForAdmin(Pageable pageable);

        @Query("SELECT new shop.mtcoding.sporting_server.admin.stadium.dto.AdminStadiumListOutDto(st.id, st.name, st.tel, ft.fileUrl, st.createdAt) "
                        + "FROM Stadium st "
                        + "INNER JOIN st.category ct "
                        + "INNER JOIN ProfileFile ft on ft.fileInfo.id = st.fileInfo.id "
                        + "WHERE st.status = '운영중' and st.name LIKE %:name%")
        Page<AdminStadiumListOutDto> findAllForAdminByName(Pageable pageable, @Param("name") String name);

        @Query("SELECT new shop.mtcoding.sporting_server.admin.stadium.dto.AdminWaitStadiumListOutDto(st.id, st.name, st.status, st.createdAt) "
                        + "FROM Stadium st "
                        + "INNER JOIN st.category ct "
                        + "WHERE st.status = '승인대기' and st.name LIKE %:name%")
        Page<AdminWaitStadiumListOutDto> findAllWaitForAdminByName(Pageable pageable, @Param("name") String name);

        @Query("SELECT new shop.mtcoding.sporting_server.admin.stadium.dto.AdminWaitStadiumListOutDto(st.id, st.name, st.status, st.createdAt) "
                        + "FROM Stadium st "
                        + "INNER JOIN st.category ct "
                        + "WHERE st.status = '승인대기'")
        Page<AdminWaitStadiumListOutDto> findAllWaitForAdmin(Pageable pageable);

        @Query("SELECT new shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO(st.id, ct.sport, st.name, sct.courtPrice, ft.id, ft.fileUrl) "
                        + "FROM Stadium st "
                        + "INNER JOIN StadiumCourt sct ON sct.stadium.id = st.id "
                        + "INNER JOIN st.category ct "
                        + "INNER JOIN ProfileFile ft ON st.fileInfo.id = ft.fileInfo.id "
                        + "WHERE ct.sport = :sportKeyword "
                        + "GROUP BY st.id")
        List<StadiumListOutDTO> findStadiumListBySportKeyword(@Param("sportKeyword") String sportKeyword);

        // @Query("SELECT new
        // shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO(st.id,
        // ct.sport, st.name, "
        // + "new
        // shop.mtcoding.sporting_server.topic.stadium.dto.StadiumFileResponseDTO(ft.id,
        // ft.fileUrl)) "
        // + "FROM Stadium st "
        // + "INNER JOIN st.category ct "
        // + "INNER JOIN File ft ON st.fileInfo.id = ft.fileInfo.id "
        // + "WHERE ct.sport = :sportKeyword and st.companyInfo.user.id =
        // :pricipalCompanyId "
        // + "GROUP BY st.id")
        // List<StadiumMyListOutDTO>
        // findStadiumMyListBySportKeyword(@Param("sportKeyword") String sportKeyword,
        // @Param("pricipalCompanyId") Long pricipalCompanyId);

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
