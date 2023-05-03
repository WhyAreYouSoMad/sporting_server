package shop.mtcoding.sporting_server.jpa.board;

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
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.sporting_server.config.TextContextConfiguration;
import shop.mtcoding.sporting_server.core.enums.field.etc.BoardAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.BoardStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.board.entity.Board;
import shop.mtcoding.sporting_server.modules.board.repository.BoardRepository;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
@Import(TextContextConfiguration.class)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        User userPS = setUpUser("ssar", "ssar@naver.com", "1234", "player",
                LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기);

        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "111-01-00110", "부산시", "010-0000-0000", "insert테스트",
                setUpUser("cos", "cos@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());

        SportCategory sportCategoryPS = setUpSportCategory("탁구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);

        Stadium stadiumPS = setUpStadium(companyInfoPS, "서울 탁구장", "깔끔", "서울시", 129.5, 35.5,
                "020-2222-2222", sportCategoryPS, LocalTime.now(), LocalTime.now(), fileInfoPS, StadiumStatus.승인대기,
                LocalDateTime.now());

        StadiumCourt stadiumCourtPS = setUpStadiumCourt(stadiumPS, fileInfoPS, 40000, 4, "최신 시설 코트",
                LocalDateTime.now(), LocalDateTime.now(), StadiumCourtStatus.등록대기);

        setUp(userPS, stadiumCourtPS, sportCategoryPS, "탁구 복식 한 세트 모집", "재밌게 치실분 구해요~", LocalDateTime.now(),
                LocalDateTime.now(), 4, 1, 1, BoardAge.AGE_20, 20000, LocalDateTime.now(), LocalDateTime.now(),
                BoardStatus.모집중);
    }

    @Test
    void selectAll() {
        List<Board> boardList = boardRepository.findAll();
        Assertions.assertNotEquals(boardList.size(), 0);

        Board board = boardList.get(0);
        Assertions.assertEquals(board.getUser().getNickname(), "ssar");
    }

    @Test
    void selectAndUpdate() {
        var optionalBoard = this.boardRepository.findById(1L);

        if (optionalBoard.isPresent()) {
            var result = optionalBoard.get();
            Assertions.assertEquals(result.getTitle(), "탁구 복식 한 세트 모집");
            var title = "축구 복식 한 세트 모집";
            result.setTitle(title);

            Board merge = entityManager.merge(result);
            Assertions.assertEquals(merge.getTitle(), "축구 복식 한 세트 모집");
        } else {
            Assertions.assertNotNull(optionalBoard.get());
        }
    }

    @Test
    void insertAndDelete() {
        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "111-011-00110", "부산시1", "010-0000-0000", "insert테스트",
                setUpUser("cos1", "cos@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        User userPS = setUpUser("dddd", "ssar@naver.com", "1234", "player",
                LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기);
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);
        SportCategory sportCategoryPS = setUpSportCategory("축구", LocalDateTime.now());
        Stadium stadiumPS = setUpStadium(companyInfoPS, "서울 탁구장", "깔끔", "서울시", 129.5, 35.5,
                "020-2222-2222", sportCategoryPS, LocalTime.now(), LocalTime.now(), fileInfoPS, StadiumStatus.승인대기,
                LocalDateTime.now());
        StadiumCourt stadiumCourtPS = setUpStadiumCourt(stadiumPS, fileInfoPS, 40000, 4, "최신 시설 코트",
                LocalDateTime.now(), LocalDateTime.now(), StadiumCourtStatus.등록대기);

        Board boardPS = setUp(userPS, stadiumCourtPS, sportCategoryPS, "탁구 복식 한 세트 모집", "재밌게 치실분 구해요~",
                LocalDateTime.now(),
                LocalDateTime.now(), 4, 1, 1, BoardAge.AGE_20, 20000, LocalDateTime.now(), LocalDateTime.now(),
                BoardStatus.모집중);
        Optional<Board> findBoard = this.boardRepository.findById(boardPS.getId());

        if (findBoard.isPresent()) {
            var result = findBoard.get();
            Assertions.assertEquals(result.getTitle(), "탁구 복식 한 세트 모집");

            entityManager.remove(boardPS);

            Optional<Board> deleteBoard = this.boardRepository.findById(boardPS.getId());
            if (deleteBoard.isPresent()) {
                Assertions.assertNull(deleteBoard.get());
            } else {
                Assertions.assertNotNull(findBoard.get());
            }
        }

    }

    private Board setUp(User user, StadiumCourt stadiumCourt, SportCategory category, String title, String content,
            LocalDateTime startTime, LocalDateTime endTime, Integer attendanceCapacity, Integer manPlayerCount,
            Integer womanPlayerCount, BoardAge age, Integer boardPrice, LocalDateTime createdAt,
            LocalDateTime updatedAt, BoardStatus status) {

        Board board = new Board();
        board.setUser(user);
        board.setStadiumCourt(stadiumCourt);
        board.setCategory(category);
        board.setTitle(title);
        board.setContent(content);
        board.setStartTime(startTime);
        board.setEndTime(endTime);
        board.setAttendanceCapacity(attendanceCapacity);
        board.setManPlayerCount(manPlayerCount);
        board.setWomanPlayerCount(womanPlayerCount);
        board.setAge(age);
        board.setBoardPrice(boardPrice);
        board.setCreatedAt(createdAt);
        board.setUpdatedAt(updatedAt);
        board.setStatus(status);

        return this.entityManager.persist(board);
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

    private StadiumCourt setUpStadiumCourt(Stadium stadium, FileInfo fileInfo, Integer courtPrice, Integer capacity,
            String content, LocalDateTime createdAt, LocalDateTime updatedAt, StadiumCourtStatus status) {

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

    // private File setUpFile(String fileName, String fileUrl, FileStatus status) {
    // File file = new File();

    // file.setFileInfo(setUpFileInfo(FileInfoSource.코트사진));
    // file.setFileName(fileName);
    // file.setFileUrl(fileUrl);
    // file.setStatus(status);

    // return this.entityManager.persist(file);
    // }

    private FileInfo setUpFileInfo(FileInfoSource type) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
    }

    private Stadium setUpStadium(CompanyInfo companyInfo, String name, String description, String address,
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

    private CompanyInfo setUpCompanyInfo(String businessNumber, String businessAddress,
            String tel,
            String ceo, User user, LocalDateTime updatedAt) {
        CompanyInfo companyInfo = new CompanyInfo();

        companyInfo.setBusinessNumber(businessNumber);
        companyInfo.setBusinessAddress(businessAddress);
        companyInfo.setTel(tel);
        companyInfo.setCeo(ceo);
        companyInfo.setUser(user);
        companyInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(companyInfo);
    }

    private SportCategory setUpSportCategory(String sport, LocalDateTime createdAt) {
        SportCategory sportCategory = new SportCategory();
        sportCategory.setSport(sport);
        sportCategory.setCreatedAt(createdAt);

        return this.entityManager.persist(sportCategory);
    }

}