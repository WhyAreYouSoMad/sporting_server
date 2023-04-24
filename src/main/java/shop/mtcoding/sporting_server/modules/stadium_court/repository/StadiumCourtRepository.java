package shop.mtcoding.sporting_server.modules.stadium_court.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public interface StadiumCourtRepository extends JpaRepository<StadiumCourt, Long>, StadiumCourtCustomRepository {

}
