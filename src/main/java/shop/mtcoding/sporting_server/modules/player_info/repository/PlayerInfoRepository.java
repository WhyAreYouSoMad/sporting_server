package shop.mtcoding.sporting_server.modules.player_info.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;

public interface PlayerInfoRepository
        extends JpaRepository<PlayerInfo, Long>, PlayerInfoCustomRepository {

    Optional<PlayerInfo> findByUserId(Long userId);
}
