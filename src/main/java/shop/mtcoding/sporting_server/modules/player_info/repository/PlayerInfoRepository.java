package shop.mtcoding.sporting_server.modules.player_info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;

public interface PlayerInfoRepository
                extends JpaRepository<PlayerInfo, Long>, PlayerInfoCustomRepository {

}
