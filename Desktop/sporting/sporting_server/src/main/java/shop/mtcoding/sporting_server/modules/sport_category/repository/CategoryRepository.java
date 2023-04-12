package shop.mtcoding.sporting_server.modules.sport_category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;

public interface CategoryRepository extends JpaRepository<SportCategory, Integer>, CategoryCustomRepository {

}
