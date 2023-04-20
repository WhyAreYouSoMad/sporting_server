package shop.mtcoding.sporting_server.modules.user.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.user.entity.QUser;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerUpdateFormOutDTO;
import shop.mtcoding.sporting_server.topic.player.dto.QPlayerUpdateFormOutDTO;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PlayerUpdateFormOutDTO findByUserId(Long userId) {
        QUser qUser = QUser.user;

        JPAQuery<PlayerUpdateFormOutDTO> query = jpaQueryFactory
                .select(new QPlayerUpdateFormOutDTO(
                        qUser.id, qUser.nickname, qUser.email, qUser.password))
                .from(qUser)
                .where(qUser.id.eq(userId));
        return query.fetchOne();
    }

}
