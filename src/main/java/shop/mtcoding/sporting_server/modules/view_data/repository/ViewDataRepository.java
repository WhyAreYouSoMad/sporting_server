package shop.mtcoding.sporting_server.modules.view_data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.connection_data.repository.ConnectionDataCustomRepository;
import shop.mtcoding.sporting_server.modules.view_data.entity.ViewData;

public interface ViewDataRepository extends JpaRepository<ViewData, Long>, ConnectionDataCustomRepository {
    List<ViewData> findByYearAndMonth(Integer year, Integer month);

    Optional<ViewData> findByYearAndMonthAndStadium_Id(@Param("year") Integer currentYear,
            @Param("month") Integer currentMonth, @Param("Stadium_id") Long id);
}
