package shop.mtcoding.sporting_server.modules.player_favorite_sport.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.player.dto.PlayerFavoriteSportResponseDTO;

public interface PlayerFavoriteSportCustomRepository {
    List<PlayerFavoriteSportResponseDTO> findSportByuserId(@Param("userId") Long userId);
}
