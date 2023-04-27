package shop.mtcoding.sporting_server.modules.company_info.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.company_info.entity.QCompanyInfo;
import shop.mtcoding.sporting_server.modules.file.entity.QProfileFile;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.QFileInfo;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyFileResponseDTO;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyInfoResponseDTO;
import shop.mtcoding.sporting_server.topic.company.dto.QCompanyFileResponseDTO;
import shop.mtcoding.sporting_server.topic.company.dto.QCompanyInfoResponseDTO;

@Repository
@RequiredArgsConstructor
@Transactional
public class CompanyInfoRepositoryImpl implements CompanyInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CompanyInfoResponseDTO findCompanyInfoByUserId(Long id) {
        QCompanyInfo qCompanyInfo = QCompanyInfo.companyInfo;
        QProfileFile qFile = QProfileFile.profileFile;

        JPAQuery<CompanyInfoResponseDTO> query = jpaQueryFactory
                .select(new QCompanyInfoResponseDTO(
                        qCompanyInfo.id, qCompanyInfo.tel, qCompanyInfo.businessAdress,
                        qCompanyInfo.businessNumber, new QCompanyFileResponseDTO(qFile.id, qFile.fileUrl)))
                .from(qCompanyInfo)
                .leftJoin(qFile).on(qCompanyInfo.fileInfo.id.eq(qFile.fileInfo.id))
                .where(qCompanyInfo.user.id.eq(id));

        return query.fetchOne();
    }

}
