package shop.mtcoding.sporting_server.modules.company_info.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long>, CompanyInfoCustomRepository {

    // Optional<CompanyInfo> findByUser(@Param("user_id") User user);
    // @EntityGraph(attributePaths = "user")
    // Optional<CompanyInfo> findWithUserByUserId(Long userId);

    Optional<CompanyInfo> findByUserId(Long userId);
}
