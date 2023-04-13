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
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.repository.PlayerFavoriteSportRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
public class PlayerFavoriteSportRepositoryTest {

    @Autowired
    private PlayerFavoriteSportRepository userFavoriteSportRepository;

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
        List<PlayerFavoriteSport> userFavoriteSportList = userFavoriteSportRepository.findAll();
        Assertions.assertNotEquals(userFavoriteSportList.size(), 0);

        PlayerFavoriteSport userFavoriteSport = userFavoriteSportList.get(0);
        System.out.println("테스트 : " + userFavoriteSport.getCategory().getSport());
        System.out.println("테스트 : " + userFavoriteSport.getPlayerInfo().getId());
        System.out.println("테스트 : " + userFavoriteSport.getPlayerInfo().getAddress());
        Assertions.assertEquals(userFavoriteSport.getCategory().getSport(), "축구");
    }

    @Test
    void selectAndUpdate() {
        var optionalUserFavoriteSport = this.userFavoriteSportRepository.findById(1L);

        if (optionalUserFavoriteSport.isPresent()) {
            var result = optionalUserFavoriteSport.get();
            Assertions.assertEquals(result.getCategory().getSport(), "축구");

            var id = 2L;
            result.getCategory().setId(id);
            PlayerFavoriteSport merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getCategory().getId(), 2);
        } else {
            Assertions.assertNotNull(optionalUserFavoriteSport.get());
        }
    }

    @Test
    @Transactional
    void insertAndDelete() {
        PlayerFavoriteSport userFavoriteSport = setUp2();

        Optional<PlayerFavoriteSport> findUserFavoriteSport = this.userFavoriteSportRepository
                .findById(userFavoriteSport.getId());

        if (findUserFavoriteSport.isPresent()) {
            var result = findUserFavoriteSport.get();
            Assertions.assertEquals(result.getCategory().getSport(), "야구");

            entityManager.remove(userFavoriteSport);

            Optional<PlayerFavoriteSport> deleteUserFavoriteSport = this.userFavoriteSportRepository
                    .findById(userFavoriteSport.getId());
            if (deleteUserFavoriteSport.isPresent()) {
                Assertions.assertNull(deleteUserFavoriteSport.get());
            }
        } else {
            Assertions.assertNotNull(findUserFavoriteSport.get());
        }
    }

    private PlayerFavoriteSport setUp() {

        PlayerFavoriteSport userFavoriteSport = new PlayerFavoriteSport();

        userFavoriteSport.setCategory(setUpSportCategory("축구", LocalDateTime.now()));
        userFavoriteSport.setPlayerInfo(setUpPlayerInfo(PlayerInfoGender.남자, PlayerInfoAge.AGE_20,
                PlayerInfoAddress.부산시, "000-0000-0000", LocalDateTime.now()));

        return this.entityManager.persist(userFavoriteSport);
    }

    private PlayerFavoriteSport setUp2() {

        PlayerFavoriteSport userFavoriteSport = new PlayerFavoriteSport();

        userFavoriteSport.setCategory(setUpSportCategory("야구", LocalDateTime.now()));
        userFavoriteSport.setPlayerInfo(setUpPlayerInfo2(PlayerInfoGender.남자, PlayerInfoAge.AGE_20,
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
