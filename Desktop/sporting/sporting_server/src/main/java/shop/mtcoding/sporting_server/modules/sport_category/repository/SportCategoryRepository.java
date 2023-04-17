package shop.mtcoding.sporting_server.modules.sport_category.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;

public interface SportCategoryRepository extends JpaRepository<SportCategory, Long>, SportCategoryCustomRepository {

    Optional<SportCategory> findBySport(@Param("sport") String sport);
}
