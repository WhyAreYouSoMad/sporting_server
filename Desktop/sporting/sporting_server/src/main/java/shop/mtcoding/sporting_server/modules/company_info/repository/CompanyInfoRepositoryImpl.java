package shop.mtcoding.sporting_server.modules.company_info.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.company_info.entity.QCompanyInfo;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyInfoResponseDTO;
import shop.mtcoding.sporting_server.topic.company.dto.QCompanyInfoResponseDTO;

@Repository
@RequiredArgsConstructor
@Transactional
public class CompanyInfoRepositoryImpl implements CompanyInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CompanyInfoResponseDTO findCompanyInfoByUserId(Long id) {
        QCompanyInfo qCompanyInfo = QCompanyInfo.companyInfo;

        JPAQuery<CompanyInfoResponseDTO> query = jpaQueryFactory
                .select(new QCompanyInfoResponseDTO(
                        qCompanyInfo.id, qCompanyInfo.tel, qCompanyInfo.businessAdress,
                        qCompanyInfo.businessNumber))
                .from(qCompanyInfo)
                .where(qCompanyInfo.user.id.eq(id));
        return query.fetchOne();
    }

}
