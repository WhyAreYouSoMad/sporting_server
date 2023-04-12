package shop.mtcoding.sporting_server.modules.stadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, Long>, StadiumCustomRepository {

}
