package shop.mtcoding.sporting_server.modules.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;

public interface ProfileFileRepository extends JpaRepository<ProfileFile, Long>, ProfileFileCustomRepository {

}
