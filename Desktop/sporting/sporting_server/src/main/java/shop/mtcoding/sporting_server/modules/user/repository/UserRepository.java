package shop.mtcoding.sporting_server.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.user.entity.User;

public interface UserRepository
        extends JpaRepository<User, Long>, UserCustomRepository {

}
