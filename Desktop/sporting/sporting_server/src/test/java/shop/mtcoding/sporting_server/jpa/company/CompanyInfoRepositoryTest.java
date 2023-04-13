package shop.mtcoding.sporting_server.jpa.company;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
public class CompanyInfoRepositoryTest {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE company_info_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        User userPS = setUpUser("cos", "cos@naver.com", "1234", "company", LocalDateTime.now(),
                LocalDateTime.now(),
                UserStatus.인증대기);
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);
        setUp(userPS, "111-01-00110", "부산시", "010-0000-0000", "곽철용", fileInfoPS, LocalDateTime.now());
    }

    @Test
    void selectAll() {
        List<CompanyInfo> companyInfoList = companyInfoRepository.findAll();
        Assertions.assertNotEquals(companyInfoList.size(), 0);

        CompanyInfo companyInfo = companyInfoList.get(0);
        Assertions.assertEquals(companyInfo.getCeo(), "곽철용");
    }

    @Test
    void selectAndUpdate() {
        var optionalCompanyInfo = this.companyInfoRepository.findById(1L);

        if (optionalCompanyInfo.isPresent()) {
            var result = optionalCompanyInfo.get();
            Assertions.assertEquals(result.getCeo(), "곽철용");
            var ceo = "김정호";
            result.setCeo(ceo);

            CompanyInfo merge = entityManager.merge(result);
            Assertions.assertEquals(merge.getCeo(), "김정호");
        } else {
            Assertions.assertNotNull(optionalCompanyInfo.get());
        }
    }

    @Test
    void insertAndDelete() {
        User userPS = setUpUser("love", "love@nate.com", "1234", "company", LocalDateTime.now(),
                LocalDateTime.now(),
                UserStatus.인증대기);
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);
        CompanyInfo companyInfo = setUp(userPS, "222-02-00220", "서울시", "020-0000-0000",
                "insert테스트", fileInfoPS, LocalDateTime.now());
        Optional<CompanyInfo> findCompanyInfo = this.companyInfoRepository.findById(companyInfo.getId());

        if (findCompanyInfo.isPresent()) {
            var result = findCompanyInfo.get();
            Assertions.assertEquals(result.getCeo(), "insert테스트");

            entityManager.remove(companyInfo);

            Optional<CompanyInfo> deleteUser = this.companyInfoRepository.findById(companyInfo.getId());
            if (deleteUser.isPresent()) {
                Assertions.assertNull(deleteUser.get());
            }
        } else {
            Assertions.assertNotNull(findCompanyInfo.get());
        }
    }

    private CompanyInfo setUp(User user, String businessNumber, String businessAdress,
            String tel,
            String ceo, FileInfo fileInfo, LocalDateTime updatedAt) {
        CompanyInfo companyInfo = new CompanyInfo();

        companyInfo.setUser(user);
        companyInfo.setBusinessNumber(businessNumber);
        companyInfo.setBusinessAdress(businessAdress);
        companyInfo.setTel(tel);
        companyInfo.setCeo(ceo);
        companyInfo.setFileInfo(fileInfo);
        companyInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(companyInfo);
    }

    private FileInfo setUpFileInfo(FileInfoSource type) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
    }

    private User setUpUser(String nickname, String email, String password, String role, LocalDateTime createdAt,
            LocalDateTime updatedAt, UserStatus status) {

        User user = new User();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        user.setStatus(status);

        return this.entityManager.persist(user);
    }

}
