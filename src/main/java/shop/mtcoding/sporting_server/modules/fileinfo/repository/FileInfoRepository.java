package shop.mtcoding.sporting_server.modules.fileinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.topic.stadium.dto.FileInfoResponseDTO;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, FileInfoCustomRepository {

}
