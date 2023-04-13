package shop.mtcoding.sporting_server.jpa.user_player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user_favorite_sport.entity.UserFavoriteSport;
import shop.mtcoding.sporting_server.modules.user_favorite_sport.repository.UserFavoriteSportRepository;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
public class UserFavoriteSportRepositoryTest {

    @Autowired
    private UserFavoriteSportRepository userFavoriteSportRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {

        em.createNativeQuery("ALTER TABLE user_favorite_sport_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        setUp();
    }

    @Test
    void selectAll() {
        List<UserFavoriteSport> userFavoriteSportList = userFavoriteSportRepository.findAll();
        Assertions.assertNotEquals(userFavoriteSportList.size(), 0);

        UserFavoriteSport userFavoriteSport = userFavoriteSportList.get(0);
        System.out.println("테스트 : " + userFavoriteSport.getCategory().getSport());
        System.out.println("테스트 : " + userFavoriteSport.getUserInfo().getId());
        System.out.println("테스트 : " + userFavoriteSport.getUserInfo().getAddress());
        Assertions.assertEquals(userFavoriteSport.getCategory().getSport(), "축구");
        Assertions.assertEquals(userFavoriteSport.getUserInfo().getId(), 1);
    }

    @Test
    void selectAndUpdate() {
        var optionalUserFavoriteSport = this.userFavoriteSportRepository.findById(1L);

        if (optionalUserFavoriteSport.isPresent()) {
            var result = optionalUserFavoriteSport.get();
            Assertions.assertEquals(result.getCategory().getId(), 1);
            Assertions.assertEquals(result.getCategory().getSport(), "축구");

            var id = 2L;
            result.getCategory().setId(id);
            UserFavoriteSport merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getCategory().getId(), 2);
        } else {
            Assertions.assertNotNull(optionalUserFavoriteSport.get());
        }
    }

    @Test
    @Transactional
    void insertAndDelete() {
        UserFavoriteSport userFavoriteSport = setUp2();

        Optional<UserFavoriteSport> findUserFavoriteSport = this.userFavoriteSportRepository
                .findById(userFavoriteSport.getId());

        if (findUserFavoriteSport.isPresent()) {
            var result = findUserFavoriteSport.get();
            Assertions.assertEquals(result.getCategory().getSport(), "야구");
            Assertions.assertEquals(result.getCategory().getId(), 2);

            entityManager.remove(userFavoriteSport);

            Optional<UserFavoriteSport> deleteUserFavoriteSport = this.userFavoriteSportRepository
                    .findById(userFavoriteSport.getId());
            if (deleteUserFavoriteSport.isPresent()) {
                Assertions.assertNull(deleteUserFavoriteSport.get());
            }
        } else {
            Assertions.assertNotNull(findUserFavoriteSport.get());
        }
    }

    private UserFavoriteSport setUp() {

        UserFavoriteSport userFavoriteSport = new UserFavoriteSport();

        userFavoriteSport.setCategory(setUpSportCategory("축구", LocalDateTime.now()));
        userFavoriteSport.setUserInfo(setUpPlayerInfo(PlayerInfoGender.남자, PlayerInfoAge.AGE_20,
                PlayerInfoAddress.부산시, "000-0000-0000", LocalDateTime.now()));

        return this.entityManager.persist(userFavoriteSport);
    }

    private UserFavoriteSport setUp2() {

        UserFavoriteSport userFavoriteSport = new UserFavoriteSport();

        userFavoriteSport.setCategory(setUpSportCategory("야구", LocalDateTime.now()));
        userFavoriteSport.setUserInfo(setUpPlayerInfo2(PlayerInfoGender.남자, PlayerInfoAge.AGE_20,
                PlayerInfoAddress.부산시, "000-0000-0000", LocalDateTime.now()));

        return this.entityManager.persist(userFavoriteSport);
    }

    private SportCategory setUpSportCategory(String sport, LocalDateTime createdAt) {
        SportCategory sportCategory = new SportCategory();
        sportCategory.setSport(sport);
        sportCategory.setCreatedAt(createdAt);

        return this.entityManager.persist(sportCategory);
    }

    private PlayerInfo setUpPlayerInfo(PlayerInfoGender gender, PlayerInfoAge age, PlayerInfoAddress address,
            String tel, LocalDateTime updatedAt) {
        PlayerInfo playerInfo = new PlayerInfo();
        setUpUser("ssar", "ssar@naver.com", "1234", "player",
                LocalDateTime.now(), LocalDateTime.now(),
                UserStatus.인증대기);
        playerInfo.setGender(gender);
        playerInfo.setAge(age);
        playerInfo.setAddress(address);
        playerInfo.setTel(tel);
        playerInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(playerInfo);
    }

    private PlayerInfo setUpPlayerInfo2(PlayerInfoGender gender, PlayerInfoAge age, PlayerInfoAddress address,
            String tel, LocalDateTime updatedAt) {
        PlayerInfo playerInfo = new PlayerInfo();
        setUpUser("ssar2", "ssar2@naver.com", "1234", "player",
                LocalDateTime.now(), LocalDateTime.now(),
                UserStatus.인증대기);
        playerInfo.setGender(gender);
        playerInfo.setAge(age);
        playerInfo.setAddress(address);
        playerInfo.setTel(tel);
        playerInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(playerInfo);
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
