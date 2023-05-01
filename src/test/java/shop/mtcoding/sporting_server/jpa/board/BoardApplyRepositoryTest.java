package shop.mtcoding.sporting_server.jpa.board;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
import shop.mtcoding.sporting_server.core.enums.field.status.BoardApplyStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.BoardStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.board.entity.Board;
import shop.mtcoding.sporting_server.modules.board_apply.entity.BoardApply;
import shop.mtcoding.sporting_server.modules.board_apply.repository.BoardApplyRepository;
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
public class BoardApplyRepositoryTest {

    @Autowired
    private BoardApplyRepository boardApplyRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach

    public void init() {
        em.createNativeQuery("ALTER TABLE board_apply_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        SportCategory sportCategoryPS = setUpSportCategory("축구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);
        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "111-01-00110", "부산시", "010-0000-0000", "insert테스트",
                setUpUser("cos", "cos@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        Stadium stadiumPS = setUpStadium(companyInfoPS, "서울 탁구장", "깔끔", "서울시", 129.5, 35.5,
                "020-2222-2222", sportCategoryPS, LocalTime.now(), LocalTime.now(), fileInfoPS, StadiumStatus.승인대기,
                LocalDateTime.now());
        User userPS = setUpUser("ssar", "ssar@naver.com", "1234", "player",
                LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기);
        StadiumCourt stadiumCourtPS = setUpStadiumCourt(stadiumPS, fileInfoPS, 40000, 4, "최신 시설 코트",
                LocalDateTime.now(), LocalDateTime.now(), StadiumCourtStatus.등록대기);
        Board boardPS = setUpBoard(userPS, stadiumCourtPS, sportCategoryPS, "탁구 복식 한 세트 모집", "재밌게 치실분 구해요~",
                LocalDateTime.now(),
                LocalDateTime.now(), 4, 1, 1, BoardAge.AGE_20, 20000, LocalDateTime.now(), LocalDateTime.now(),
                BoardStatus.모집중);
        // List<User> userListPS = setUpUsers();

        setUp(userPS, stadiumCourtPS, boardPS, 4, 1, 1, 25, LocalDateTime.now(), BoardApplyStatus.대기);
    }

    @Test
    void selectAll() {
        List<BoardApply> boardApplyList = boardApplyRepository.findAll();
        Assertions.assertNotEquals(boardApplyList.size(), 0);

        BoardApply boardApply = boardApplyList.get(0);
        Assertions.assertEquals(boardApply.getUser().getNickname(), "ssar");
    }

    @Test
    void selectAndUpdate() {
        var optionalBoardApply = this.boardApplyRepository.findById(1L);

        if (optionalBoardApply.isPresent()) {
            var result = optionalBoardApply.get();
            Assertions.assertEquals(result.getManPlayerCount(), 1);
            var manPlayerCount = 2;
            result.setManPlayerCount(manPlayerCount);

            BoardApply merge = entityManager.merge(result);
            Assertions.assertEquals(merge.getManPlayerCount(), 2);
        } else {
            Assertions.assertNotNull(optionalBoardApply.get());
        }
    }

    @Test
    void insertAndDelete() {
        List<User> userListPS = setUpUsers();

        CompanyInfo companyInfoPS = setUpCompanyInfo(
                "111-011-00110", "부산시1", "010-0000-0000", "insert테스트",
                setUpUser("cos1", "cos@naver.com", "1234", "company",
                        LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기),
                LocalDateTime.now());
        SportCategory sportCategoryPS = setUpSportCategory("야구", LocalDateTime.now());
        FileInfo fileInfoPS = setUpFileInfo(FileInfoSource.플레이어프로필);
        Stadium stadiumPS = setUpStadium(companyInfoPS, "서울 탁구장", "깔끔", "서울시", 129.5, 35.5,
                "020-2222-2222", sportCategoryPS, LocalTime.now(), LocalTime.now(), fileInfoPS, StadiumStatus.승인대기,
                LocalDateTime.now());
        User userPS = setUpUser("ssar1", "ssar@naver.com", "1234", "player",
                LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기);
        StadiumCourt stadiumCourtPS = setUpStadiumCourt(stadiumPS, fileInfoPS, 40000, 4, "최신 시설 코트",
                LocalDateTime.now(), LocalDateTime.now(), StadiumCourtStatus.등록대기);
        Board boardPS = setUpBoard(userPS, stadiumCourtPS, sportCategoryPS, "탁구 복식 한 세트 모집", "재밌게 치실분 구해요~",
                LocalDateTime.now(),
                LocalDateTime.now(), 4, 1, 1, BoardAge.AGE_20, 20000, LocalDateTime.now(), LocalDateTime.now(),
                BoardStatus.모집중);

        BoardApply boardApplyPS = setUp(userPS, stadiumCourtPS, boardPS, 4, 1, 1, 25, LocalDateTime.now(),
                BoardApplyStatus.대기);
        Optional<BoardApply> findBoardApply = this.boardApplyRepository.findById(boardApplyPS.getId());

        if (findBoardApply.isPresent()) {
            var result = findBoardApply.get();
            Assertions.assertEquals(result.getManPlayerCount(), 1);

            entityManager.remove(boardApplyPS);

            Optional<BoardApply> deleteBoardApply = this.boardApplyRepository.findById(boardApplyPS.getId());
            if (deleteBoardApply.isPresent()) {
                Assertions.assertNull(deleteBoardApply.get());
            } else {
                Assertions.assertNotNull(findBoardApply.get());
            }
        }
    }

    private BoardApply setUp(User users, StadiumCourt stadiumCourt, Board board, Integer personNum,
            Integer manPlayerCount, Integer womanPlayerCount, Integer age, LocalDateTime createdAt,
            BoardApplyStatus status) {

        BoardApply boardApply = new BoardApply();
        boardApply.setUser(users);
        boardApply.setStadiumCourt(stadiumCourt);
        boardApply.setBoard(board);
        boardApply.setPersonNum(personNum);
        boardApply.setManPlayerCount(manPlayerCount);
        boardApply.setWomanPlayerCount(womanPlayerCount);
        boardApply.setAge(age);
        boardApply.setCreatedAt(createdAt);
        boardApply.setStatus(status);
        return this.entityManager.persist(boardApply);
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

    private Board setUpBoard(User user, StadiumCourt stadiumCourt, SportCategory category, String title, String content,
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

    private SportCategory setUpSportCategory(String sport, LocalDateTime createdAt) {
        SportCategory sportCategory = new SportCategory();
        sportCategory.setSport(sport);
        sportCategory.setCreatedAt(createdAt);

        return this.entityManager.persist(sportCategory);
    }

    private FileInfo setUpFileInfo(FileInfoSource type) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
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

    private List<User> setUpUsers() {
        List<User> users = new ArrayList<>();

        // 사용자1
        User user1 = new User();
        user1.setNickname("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setRole("player");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        user1.setStatus(UserStatus.인증대기);
        users.add(user1);

        // 사용자2
        User user2 = new User();
        user2.setNickname("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setRole("player");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());
        user2.setStatus(UserStatus.인증대기);
        users.add(user2);

        // 사용자3
        User user3 = new User();
        user3.setNickname("user3");
        user3.setEmail("user3@example.com");
        user3.setPassword("password3");
        user3.setRole("player");
        user3.setCreatedAt(LocalDateTime.now());
        user3.setUpdatedAt(LocalDateTime.now());
        user3.setStatus(UserStatus.인증대기);
        users.add(user3);

        return users;
    }
}