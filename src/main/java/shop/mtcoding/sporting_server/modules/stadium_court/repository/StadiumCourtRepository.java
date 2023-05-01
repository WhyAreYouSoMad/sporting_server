package shop.mtcoding.sporting_server.modules.stadium_court.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public interface StadiumCourtRepository extends JpaRepository<StadiumCourt, Long>, StadiumCourtCustomRepository {

    List<StadiumCourt> findByIdIn(List<Long> ids);

    List<StadiumCourt> findAllByStadiumId(@Param("stadiumId") Long stadiumId);

    @Query("SELECT COUNT(c) FROM StadiumCourt c WHERE c.id IN :ids")
    int countByIds(@Param("ids") List<Long> ids);

    Page<StadiumCourt> findByTitleContaining(String keyword, Pageable pageable);

}
