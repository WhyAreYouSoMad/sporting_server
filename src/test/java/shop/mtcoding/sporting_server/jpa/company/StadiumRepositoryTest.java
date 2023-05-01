package shop.mtcoding.sporting_server.jpa.company;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import shop.mtcoding.sporting_server.config.TextContextConfiguration;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Import(TextContextConfiguration.class)
public class StadiumRepositoryTest {
    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE stadium_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "111-01-00110", "부산시", "010-0000-0000", "insert테스트",
                setUpUser("cos", "cos@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        SportCategory sportCategoryPS = setUpSportCategory("축구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);

        setUp(companyInfoPS, "서면 탁구장", "신설", "부산시", 129.3, 35.3, "010-1111-1111", sportCategoryPS,
                LocalTime.now(), LocalTime.now(),
                fileInfoPS, StadiumStatus.승인대기, LocalDateTime.now());
    }

    @Test
    void selectAll() {
        List<Stadium> stadiumList = stadiumRepository.findAll();
        Assertions.assertNotEquals(stadiumList.size(), 0);

        Stadium stadium = stadiumList.get(0);
        Assertions.assertEquals(stadium.getCompanyInfo().getBusinessAdress(), "부산시");
    }

    @Test
    void selectAndUpdate() {
        var optionalStadium = this.stadiumRepository.findById(1L);

        if (optionalStadium.isPresent()) {
            var result = optionalStadium.get();
            Assertions.assertEquals(result.getCategory().getSport(), "축구");
            var sport = "테니스";
            result.getCategory().setSport(sport);

            Stadium merge = entityManager.merge(result);
            Assertions.assertEquals(merge.getCategory().getSport(), "테니스");
        } else {
            Assertions.assertNotNull(optionalStadium.get());
        }
    }

    @Test
    void insertAndDelete() {
        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "222-02-00220", "서울시", "020-0000-0000", "insert테스트",
                setUpUser("love", "love@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        SportCategory sportCategoryPS = setUpSportCategory("탁구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);

        Stadium stadiumPS = setUp(companyInfoPS, "서울 탁구장", "깔끔", "서울시", 129.5, 35.5, "020-2222-2222",
                sportCategoryPS,
                LocalTime.now(), LocalTime.now(),
                fileInfoPS, StadiumStatus.승인대기, LocalDateTime.now());

        Optional<Stadium> findStadium = this.stadiumRepository.findById(stadiumPS.getId());

        if (findStadium.isPresent()) {
            var result = findStadium.get();
            Assertions.assertEquals(result.getCategory().getSport(), "탁구");

            entityManager.remove(stadiumPS);

            Optional<Stadium> deleteStadium = this.stadiumRepository.findById(stadiumPS.getId());
            if (deleteStadium.isPresent()) {
                Assertions.assertNull(deleteStadium.get());
            }
        } else {
            Assertions.assertNotNull(findStadium.get());
        }
    }

    private Stadium setUp(CompanyInfo companyInfo, String name, String description, String address, Double lat,
            Double lon, String tel, SportCategory sportCategory,
            LocalTime startTime, LocalTime endTime, FileInfo fileInfo, StadiumStatus status, LocalDateTime updatedAt) {

        Stadium stadium = new Stadium();

        stadium.setCompanyInfo(companyInfo);
        stadium.setName(name);
        stadium.setDescription(description);
        stadium.setAddress(address);
        stadium.setLat(lat);
        stadium.setLon(lon);
        stadium.setTel(tel);
        stadium.setCategory(sportCategory);
        stadium.setStartTime(startTime);
        stadium.setEndTime(endTime);
        stadium.setFileInfo(fileInfo);
        stadium.setStatus(status);
        stadium.setUpdatedAt(updatedAt);

        return this.entityManager.persist(stadium);
    }

    private FileInfo setUpFileInfo(FileInfoSource type) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
    }

    private SportCategory setUpSportCategory(String sport, LocalDateTime createdAt) {
        SportCategory sportCategory = new SportCategory();

        sportCategory.setSport(sport);
        sportCategory.setCreatedAt(createdAt);

        return this.entityManager.persist(sportCategory);
    }

    private CompanyInfo setUpCompanyInfo(String businessNumber, String businessAdress,
            String tel,
            String ceo, User user, LocalDateTime updatedAt) {
        CompanyInfo companyInfo = new CompanyInfo();

        companyInfo.setBusinessNumber(businessNumber);
        companyInfo.setBusinessAdress(businessAdress);
        companyInfo.setTel(tel);
        companyInfo.setCeo(ceo);
        companyInfo.setUser(user);
        companyInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(companyInfo);
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