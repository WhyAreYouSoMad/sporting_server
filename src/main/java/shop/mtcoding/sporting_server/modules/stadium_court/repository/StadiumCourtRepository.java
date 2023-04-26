package shop.mtcoding.sporting_server.modules.stadium_court.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public interface StadiumCourtRepository extends JpaRepository<StadiumCourt, Long>, StadiumCourtCustomRepository {

    Page<StadiumCourt> findByTitleContaining(String keyword, Pageable pageable);
}
