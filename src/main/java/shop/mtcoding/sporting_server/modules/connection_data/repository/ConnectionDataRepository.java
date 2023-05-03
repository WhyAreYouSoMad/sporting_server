package shop.mtcoding.sporting_server.modules.connection_data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.modules.connection_data.entity.ConnectionData;

public interface ConnectionDataRepository extends JpaRepository<ConnectionData, Long>, ConnectionDataCustomRepository {
    List<ConnectionData> findByRoleAndYearAndMonth(String role, Integer year, Integer month);

    // @Query("SELECT c.cnt FROM ConnectionData c WHERE c.role = :role AND
    // CONCAT(c.year, c.month) IN :yearMonthList")
    // List<Integer> findByRoleAndYearMonthIn(@Param("role") String role,
    // @Param("yearMonthList") String yearMonthList);

    @Query("SELECT c.cnt FROM ConnectionData c WHERE c.role = :role AND CONCAT(c.year, LPAD(c.month, 2, '0')) IN :yearMonthList")
    List<Integer> findByRoleAndYearMonthIn(@Param("role") String role,
            @Param("yearMonthList") List<String> yearMonthList);
}
