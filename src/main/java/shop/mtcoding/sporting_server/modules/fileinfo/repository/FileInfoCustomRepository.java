package shop.mtcoding.sporting_server.modules.fileinfo.repository;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.stadium.dto.FileInfoResponseDTO;

public interface FileInfoCustomRepository {
    FileInfoResponseDTO findStadiumById(@Param("id") Long id);
}
