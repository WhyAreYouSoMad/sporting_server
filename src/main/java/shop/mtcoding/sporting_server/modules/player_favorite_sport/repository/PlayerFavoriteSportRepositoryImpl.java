package shop.mtcoding.sporting_server.modules.player_favorite_sport.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.QPlayerFavoriteSport;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerFavoriteSportResponseDTO;
import shop.mtcoding.sporting_server.topic.player.dto.QPlayerFavoriteSportResponseDTO;

@Transactional
@RequiredArgsConstructor
@Repository
public class PlayerFavoriteSportRepositoryImpl implements PlayerFavoriteSportCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PlayerFavoriteSportResponseDTO> findSportByuserId(Long userId) {
        QPlayerFavoriteSport qPlayerFavoriteSport = QPlayerFavoriteSport.playerFavoriteSport;

        JPAQuery<PlayerFavoriteSportResponseDTO> query = jpaQueryFactory
                .select(new QPlayerFavoriteSportResponseDTO(
                        qPlayerFavoriteSport.id, qPlayerFavoriteSport.category.sport))
                .from(qPlayerFavoriteSport)
                .where(qPlayerFavoriteSport.playerInfo.user.id.eq(userId));

        return query.fetch();
    }

}
