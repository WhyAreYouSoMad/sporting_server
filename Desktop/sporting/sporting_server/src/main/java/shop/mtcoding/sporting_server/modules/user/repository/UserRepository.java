package shop.mtcoding.sporting_server.modules.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.user.entity.User;

public interface UserRepository
                extends JpaRepository<User, Long>, UserCustomRepository {
        @Query("select u from User u where u.email = :email")
        Optional<User> findByEmail(@Param("email") String email);
}
