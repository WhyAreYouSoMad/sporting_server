package shop.mtcoding.sporting_server.modules.player_favorite_sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;

public interface PlayerFavoriteSportRepository
        extends JpaRepository<PlayerFavoriteSport, Long>, PlayerFavoriteSportCustomRepository {

}
