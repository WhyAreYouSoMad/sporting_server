package shop.mtcoding.sporting_server.modules.stadium.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.adminuser.dto.stadium.AdminStadiumListOutDto;
import shop.mtcoding.sporting_server.adminuser.dto.stadium.QAdminStadiumListOutDto;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.modules.file.entity.QProfileFile;
import shop.mtcoding.sporting_server.modules.sport_category.entity.QSportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.QStadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.QStadiumCourt;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QCourtFileResponseDto;
import shop.mtcoding.sporting_server.topic.stadium.dto.QCourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QSportCategoryDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QStadiumDetailOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QStadiumFileResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QStadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QStadiumUpdateFomrOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.SportCategoryDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumDetailOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

// StadiumCustomRepository 에서 생성한 인터페이스를 여기서 구현
@Repository
@RequiredArgsConstructor
@Transactional
public class StadiumRepositoryImpl implements StadiumCustomRepository {

        private final JPAQueryFactory jpaQueryFactory;

        // @Override
        // public Page<AdminStadiumListOutDto> findAllForAdmin(Pageable pageable) {
        // if (!(pageable instanceof PageRequest)) {
        // pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        // }
        // QStadium qStadium = QStadium.stadium;
        // QSportCategory qSportCategory = QSportCategory.sportCategory;
        // QProfileFile qFile = QProfileFile.profileFile;

        // List<AdminStadiumListOutDto> content = jpaQueryFactory
        // .select(new QAdminStadiumListOutDto(qStadium.id, qStadium.name,
        // qSportCategory.sport, qFile.fileUrl, qStadium.createdAt))
        // .from(qStadium)
        // .leftJoin(qSportCategory).on(qSportCategory.id.eq(qStadium.category.id))
        // .leftJoin(qFile).on(qFile.fileInfo.id.eq(qStadium.fileInfo.id))
        // .where(qStadium.status.eq(StadiumStatus.운영중))
        // .offset(pageable.getOffset())
        // .limit(pageable.getPageSize())
        // .fetch();

        // long total = jpaQueryFactory
        // .selectFrom(qStadium)
        // .where(qStadium.status.eq(StadiumStatus.운영중))
        // .fetchCount();

        // return new PageImpl<>(content, pageable, total);
        // }

        @Override
        public List<StadiumMyListOutDTO> findStadiumMyListBySportKeyword(String sportKeyword, Long principalCompanyId) {
                QStadium qStadium = QStadium.stadium;
                QProfileFile qFile = QProfileFile.profileFile;
                JPAQuery<StadiumMyListOutDTO> query = jpaQueryFactory
                                .select(new QStadiumMyListOutDTO(qStadium.id, qStadium.category.sport, qStadium.name,
                                                new QStadiumFileResponseDTO(qFile.id, qFile.fileUrl)))
                                .from(qStadium)
                                .leftJoin(qFile).on(qStadium.fileInfo.id.eq(qFile.fileInfo.id))
                                .where(qStadium.category.sport.eq(sportKeyword),
                                                qStadium.companyInfo.user.id.eq(principalCompanyId))
                                .groupBy(qStadium.id);
                return query.fetch();
        }

        @Override
        public List<CourtResponseDTO> findCourtsByStadiumId(Long stadiumId) {
                QStadiumCourt qStadiumCourt = QStadiumCourt.stadiumCourt;
                QProfileFile qFile = QProfileFile.profileFile;
                JPAQuery<CourtResponseDTO> query = jpaQueryFactory
                                .select(new QCourtResponseDTO(qStadiumCourt.id, qStadiumCourt.title,
                                                qStadiumCourt.content, qStadiumCourt.capacity,
                                                qStadiumCourt.courtPrice, qStadiumCourt.stadium.category.sport,
                                                new QCourtFileResponseDto(qFile.id, qFile.fileUrl)))
                                .from(qStadiumCourt)
                                .leftJoin(qFile).on(qStadiumCourt.fileInfo.id.eq(qFile.fileInfo.id))
                                .where(qStadiumCourt.stadium.id.eq(stadiumId));

                return query.fetch();
        }

        @Override
        public StadiumUpdateFomrOutDTO findByStadiumId(Long stadiumId) {
                QStadium qStadium = QStadium.stadium;
                QProfileFile qFile = QProfileFile.profileFile;
                JPAQuery<StadiumUpdateFomrOutDTO> query = jpaQueryFactory
                                .select(new QStadiumUpdateFomrOutDTO(qStadium.id, qStadium.name, qStadium.address,
                                                qStadium.status,
                                                qStadium.startTime, qStadium.endTime,
                                                new QStadiumFileResponseDTO(qFile.id, qFile.fileUrl)))
                                .from(qStadium)
                                .leftJoin(qFile).on(qStadium.fileInfo.id.eq(qFile.fileInfo.id))
                                .where(qStadium.id.eq(stadiumId));

                return query.fetchOne();
        }

        @Override
        public StadiumDetailOutDTO findByStadiumId2(Long id) {
                QStadium qStadium = QStadium.stadium;
                QProfileFile qFile = QProfileFile.profileFile;
                JPAQuery<StadiumDetailOutDTO> query = jpaQueryFactory
                                .select(new QStadiumDetailOutDTO(qStadium.startTime, qStadium.endTime, qStadium.name,
                                                qStadium.lat, qStadium.lon, qStadium.address,
                                                new QStadiumFileResponseDTO(qFile.id, qFile.fileUrl)))
                                .from(qStadium)
                                .leftJoin(qFile).on(qStadium.fileInfo.id.eq(qFile.fileInfo.id))
                                .where(qStadium.id.eq(id));

                return query.fetchOne();
        }

        @Override
        public SportCategoryDTO findCategoryByStadiumId(Long categoryId) {
                QSportCategory qSportCategory = QSportCategory.sportCategory;

                JPAQuery<SportCategoryDTO> query = jpaQueryFactory
                                .select(new QSportCategoryDTO(qSportCategory.id, qSportCategory.sport))
                                .from(qSportCategory)
                                .where(qSportCategory.id.eq(categoryId));

                return query.fetchOne();
        }

}
