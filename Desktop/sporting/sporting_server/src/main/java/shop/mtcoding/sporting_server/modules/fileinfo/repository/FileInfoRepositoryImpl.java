package shop.mtcoding.sporting_server.modules.fileinfo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.QFileInfo;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.QPlayerFavoriteSport;
import shop.mtcoding.sporting_server.topic.stadium.dto.FileInfoResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.QFileInfoResponseDTO;

@Transactional
@RequiredArgsConstructor
@Repository
public class FileInfoRepositoryImpl implements FileInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public FileInfoResponseDTO findStadiumById(Long id) {
        QFileInfo qFileInfo = QFileInfo.fileInfo;

        JPAQuery<FileInfoResponseDTO> query = jpaQueryFactory
                .select(new QFileInfoResponseDTO(qFileInfo.id, qFileInfo.type))
                .from(qFileInfo)
                .where(qFileInfo.fileInfo.id.eq(id));

        return query.fetchOne();
    }
}
