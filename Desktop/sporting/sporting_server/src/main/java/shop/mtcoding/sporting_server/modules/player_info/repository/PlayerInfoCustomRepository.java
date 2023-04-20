package shop.mtcoding.sporting_server.modules.player_info.repository;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.player.dto.PlayerInfoResponseDTO;

public interface PlayerInfoCustomRepository {
    PlayerInfoResponseDTO findplayerInfoByuserId(@Param("userId") Long userId);
}
