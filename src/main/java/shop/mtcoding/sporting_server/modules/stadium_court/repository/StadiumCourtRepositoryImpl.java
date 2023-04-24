package shop.mtcoding.sporting_server.modules.stadium_court.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.QStadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.topic.stadium.dto.QStadiumCourtDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumCourtDTO;

@Repository
@RequiredArgsConstructor
@Transactional
public class StadiumCourtRepositoryImpl implements StadiumCourtCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StadiumCourtDTO> findStadiumCourtByStadiumId(Long stadiumId) {
        QStadiumCourt qStadiumCourt = QStadiumCourt.stadiumCourt;

        JPAQuery<StadiumCourtDTO> query = jpaQueryFactory
                .select(new QStadiumCourtDTO(qStadiumCourt.id, qStadiumCourt.title, qStadiumCourt.content,
                        qStadiumCourt.capacity, qStadiumCourt.courtPrice, qStadiumCourt.fileInfo))
                .from(qStadiumCourt)
                .where(qStadiumCourt.stadium.id.eq(stadiumId));

        return query.fetch();
    }

}
