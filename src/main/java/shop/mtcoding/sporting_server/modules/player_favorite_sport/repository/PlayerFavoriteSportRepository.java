package shop.mtcoding.sporting_server.modules.player_favorite_sport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;

public interface PlayerFavoriteSportRepository
                extends JpaRepository<PlayerFavoriteSport, Long>, PlayerFavoriteSportCustomRepository {

        List<PlayerFavoriteSport> findByIdIn(List<Long> favoriteSportIdList);

        List<PlayerFavoriteSport> findAllByPlayerInfo(PlayerInfo playerInfo);

        void deleteByPlayerInfoIdAndCategoryIdIn(Long playerInfoId, List<Long> categoryIds);

        @Query("SELECT fs FROM PlayerFavoriteSport fs WHERE fs.playerInfo = :playerInfo")
        List<PlayerFavoriteSport> findFavoriteSportByPlayerInfo(@Param("playerInfo") PlayerInfo playerInfo);

}
