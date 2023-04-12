package shop.mtcoding.sporting_server.modules.company_info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long>, CompanyInfoCustomRepository {

}
