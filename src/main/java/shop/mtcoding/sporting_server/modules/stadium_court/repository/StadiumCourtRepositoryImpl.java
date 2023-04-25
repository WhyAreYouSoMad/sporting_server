package shop.mtcoding.sporting_server.modules.stadium_court.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.file.entity.QProfileFile;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.QStadiumCourt;
import shop.mtcoding.sporting_server.topic.stadium.dto.QCourtFileResponseDto;
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
        QProfileFile qFile = QProfileFile.profileFile;
        JPAQuery<StadiumCourtDTO> query = jpaQueryFactory
                .select(new QStadiumCourtDTO(qStadiumCourt.id, qStadiumCourt.title, qStadiumCourt.content,
                        qStadiumCourt.capacity, qStadiumCourt.courtPrice,
                        new QCourtFileResponseDto(qFile.id, qFile.fileUrl)))
                .from(qStadiumCourt)
                .leftJoin(qFile).on(qStadiumCourt.fileInfo.id.eq(qFile.fileInfo.id))
                .where(qStadiumCourt.stadium.id.eq(stadiumId));

        return query.fetch();
    }

}
