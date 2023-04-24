package shop.mtcoding.sporting_server.modules.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;

public interface FileRepository extends JpaRepository<ProfileFile, Long>, FileCustomRepository {

}
