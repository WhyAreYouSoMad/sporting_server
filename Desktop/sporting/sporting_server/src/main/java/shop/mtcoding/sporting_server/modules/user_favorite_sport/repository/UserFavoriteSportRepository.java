package shop.mtcoding.sporting_server.modules.user_favorite_sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.user_favorite_sport.entity.UserFavoriteSport;

public interface UserFavoriteSportRepository
                extends JpaRepository<UserFavoriteSport, Long>, UserFavoriteSportCustomRepository {

}
