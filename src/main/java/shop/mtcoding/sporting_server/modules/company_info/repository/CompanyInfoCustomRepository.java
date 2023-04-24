package shop.mtcoding.sporting_server.modules.company_info.repository;

import org.springframework.data.repository.query.Param;

import shop.mtcoding.sporting_server.topic.company.dto.CompanyInfoResponseDTO;

public interface CompanyInfoCustomRepository {
    CompanyInfoResponseDTO findCompanyInfoByUserId(@Param("id") Long id);

}
