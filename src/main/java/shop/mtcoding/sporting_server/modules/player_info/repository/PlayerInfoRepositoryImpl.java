package shop.mtcoding.sporting_server.modules.player_info.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.file.entity.QProfileFile;
import shop.mtcoding.sporting_server.modules.player_info.entity.QPlayerInfo;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerInfoResponseDTO;
import shop.mtcoding.sporting_server.topic.player.dto.QPlayerFileResponseDTO;
import shop.mtcoding.sporting_server.topic.player.dto.QPlayerInfoResponseDTO;

@Repository
@RequiredArgsConstructor
@Transactional
public class PlayerInfoRepositoryImpl implements PlayerInfoCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PlayerInfoResponseDTO findplayerInfoByuserId(Long userId) {
        QPlayerInfo qPlayerInfo = QPlayerInfo.playerInfo;
        QProfileFile qFile = QProfileFile.profileFile;
        JPAQuery<PlayerInfoResponseDTO> query = jpaQueryFactory
                .select(new QPlayerInfoResponseDTO(
                        qPlayerInfo.id, qPlayerInfo.tel, qPlayerInfo.gender, qPlayerInfo.age, qPlayerInfo.address,
                        new QPlayerFileResponseDTO(qFile.id, qFile.fileUrl)))
                .from(qPlayerInfo)
                .leftJoin(qFile).on(qPlayerInfo.fileInfo.id.eq(qFile.fileInfo.id))
                .where(qPlayerInfo.user.id.eq(userId));
        return query.fetchOne();
    }

}
