package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.stadium.entity.QStadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.QStadiumCourt;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QCourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QStadiumUpdateFomrOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

// StadiumCustomRepository 에서 생성한 인터페이스를 여기서 구현
@Repository
@RequiredArgsConstructor
@Transactional
public class StadiumRepositoryImpl implements StadiumCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CourtResponseDTO> findCourtsByStadiumId(Long stadiumId) {
        QStadiumCourt qStadiumCourt = QStadiumCourt.stadiumCourt;

        JPAQuery<CourtResponseDTO> query = jpaQueryFactory
                .select(new QCourtResponseDTO(qStadiumCourt.id, qStadiumCourt.title,
                        qStadiumCourt.content,
                        qStadiumCourt.capacity, qStadiumCourt.courtPrice,
                        qStadiumCourt.stadium.category.sport))
                .from(qStadiumCourt)
                .where(qStadiumCourt.stadium.id.eq(stadiumId));

        return query.fetch();
    }

    @Override
    public StadiumUpdateFomrOutDTO findByStadiumId(Long stadiumId) {
        QStadium qStadium = QStadium.stadium;

        JPAQuery<StadiumUpdateFomrOutDTO> query = jpaQueryFactory
                .select(new QStadiumUpdateFomrOutDTO(qStadium.id, qStadium.name,
                        qStadium.address, qStadium.status,
                        qStadium.startTime, qStadium.endTime))
                .from(qStadium)
                .where(qStadium.id.eq(stadiumId));

        return query.fetchOne();
    }

}
