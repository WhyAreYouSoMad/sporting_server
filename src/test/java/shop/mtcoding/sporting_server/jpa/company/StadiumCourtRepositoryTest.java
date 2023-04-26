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
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Import(TextContextConfiguration.class)
public class StadiumCourtRepositoryTest {
    @Autowired
    private StadiumCourtRepository stadiumCourtRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE stadium_court_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "111-01-00110", "부산시", "010-0000-0000", "insert테스트",
                setUpUser("cos", "cos@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        SportCategory sportCategoryPS = setUpSportCategory("축구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);

        Stadium stadium = setUpStadium(companyInfoPS, "서면 탁구장", "신설", StadiumAddress.부산시, 129.3, 35.3, "010-1111-1111",
                sportCategoryPS,
                LocalTime.now(), LocalTime.now(),
                fileInfoPS, StadiumStatus.승인대기, LocalDateTime.now());

        setUp(stadium, fileInfoPS, 50000, 4, "신설 탁구장", LocalDateTime.now(), LocalDateTime.now(),
                StadiumCourtStatus.등록대기);
    }

    @Test
    void selectAll() {
        List<StadiumCourt> stadiumCourtList = stadiumCourtRepository.findAll();
        Assertions.assertNotEquals(stadiumCourtList.size(), 0);

        StadiumCourt stadiumCourt = stadiumCourtList.get(0);
        Assertions.assertEquals(stadiumCourt.getStadium().getCompanyInfo().getBusinessNumber(), "111-01-00110");
    }

    @Test
    void selectAndUpdate() {
        var optionalStadiumCourt = this.stadiumCourtRepository.findById(1L);

        if (optionalStadiumCourt.isPresent()) {
            var result = optionalStadiumCourt.get();
            Assertions.assertEquals(result.getContent(), "신설 탁구장");
            var content = "새로 오픈!";
            result.setContent(content);

            StadiumCourt merge = entityManager.merge(result);
            Assertions.assertEquals(merge.getContent(), "새로 오픈!");
        } else {
            Assertions.assertNotNull(optionalStadiumCourt.get());
        }
    }

    @Test
    void insertAndDelete() {
        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "222-02-00220", "울산시", "020-0000-0000", "insert테스트2",
                setUpUser("love", "love@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        SportCategory sportCategoryPS = setUpSportCategory("야구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.코트사진);

        Stadium stadium = setUpStadium(companyInfoPS, "울산 야구장", "깨끗", StadiumAddress.울산시, 129.4, 35.6, "010-2222-2222",
                sportCategoryPS,
                LocalTime.now(), LocalTime.now(),
                fileInfoPS, StadiumStatus.승인대기, LocalDateTime.now());

        StadiumCourt stadiumCourtPS = setUp(stadium, fileInfoPS, 100000, 18, "신설 야구장", LocalDateTime.now(),
                LocalDateTime.now(),
                StadiumCourtStatus.등록대기);

        Optional<StadiumCourt> findStadiumCourt = this.stadiumCourtRepository.findById(stadiumCourtPS.getId());

        if (findStadiumCourt.isPresent()) {
            var result = findStadiumCourt.get();
            Assertions.assertEquals(result.getStadium().getName(), "울산 야구장");

            entityManager.remove(stadiumCourtPS);

            Optional<StadiumCourt> deleteStadiumCourt = this.stadiumCourtRepository.findById(stadiumCourtPS.getId());
            if (deleteStadiumCourt.isPresent()) {
                Assertions.assertNull(deleteStadiumCourt.get());
            }
        } else {
            Assertions.assertNotNull(findStadiumCourt.get());
        }
    }

    private StadiumCourt setUp(Stadium stadium, FileInfo fileInfo, Integer courtPrice,
            Integer capacity, String content, LocalDateTime createdAt, LocalDateTime updatedAt,
            StadiumCourtStatus status) {

        StadiumCourt stadiumCourt = new StadiumCourt();
        stadiumCourt.setStadium(stadium);
        stadiumCourt.setFileInfo(fileInfo);
        stadiumCourt.setCourtPrice(courtPrice);
        stadiumCourt.setCapacity(capacity);
        stadiumCourt.setContent(content);
        stadiumCourt.setCreatedAt(createdAt);
        stadiumCourt.setUpdatedAt(updatedAt);
        stadiumCourt.setStatus(status);

        return this.entityManager.persist(stadiumCourt);
    }

    private Stadium setUpStadium(CompanyInfo companyInfo, String name, String description, StadiumAddress address,
            Double lat,
            Double lon, String tel, SportCategory sportCategory,
            LocalTime startTime, LocalTime endTime, FileInfo fileInfo, StadiumStatus status, LocalDateTime updatedAt) {

        Stadium stadium = new Stadium();

        stadium.setCompanyInfo(companyInfo);
        stadium.setName(name);
        stadium.setDescription(description);
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

    private FileInfo setUpFileInfo(FileInfoSource type) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
    }

}
