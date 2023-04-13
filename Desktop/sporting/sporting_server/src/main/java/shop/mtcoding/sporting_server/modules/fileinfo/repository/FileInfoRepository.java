package shop.mtcoding.sporting_server.modules.fileinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, FileInfoCustomRepository {

}
