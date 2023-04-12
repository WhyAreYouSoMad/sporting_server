package shop.mtcoding.sporting_server.jpa.player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.jpa.user.UserRepositoryTest;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
public class PlayerInfoRepositoryTest {
    @Autowired
    private PlayerInfoRepository playerInfoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        setUpUser("ssar", "ssar@naver.com", "1234", "player", LocalDateTime.now(), LocalDateTime.now(),
                UserStatus.인증대기);
        setUpFileInfo(FileInfoSource.플레이어프로필);
        setUp(PlayerInfoGender.남자, PlayerInfoAge.AGE_20, PlayerInfoAddress.부산시, "123-4564-4561", LocalDateTime.now());
    }

    @Test
    @Transactional
    void selectAll() {
        List<PlayerInfo> playerInfoList = playerInfoRepository.findAll();
        Assertions.assertNotEquals(playerInfoList.size(), 0);

        PlayerInfo playerInfo = playerInfoList.get(0);
        Assertions.assertEquals(playerInfo.getGender(), PlayerInfoGender.남자);

    }

    @Test
    void selectAndUpdate() {
        var optionalPlayerInfo = this.playerInfoRepository.findById(1L);

        if (optionalPlayerInfo.isPresent()) {
            var result = optionalPlayerInfo.get();
            Assertions.assertEquals(result.getGender(), PlayerInfoGender.남자);

            var gender = PlayerInfoGender.비공개;
            result.setGender(gender);
            PlayerInfo merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getGender(), PlayerInfoGender.비공개);
        } else {
            Assertions.assertNotNull(optionalPlayerInfo.get());
        }
    }

    @Test
    void insertAndDelete() {
        PlayerInfo playerInfo = setUp(PlayerInfoGender.여자, PlayerInfoAge.AGE_40, PlayerInfoAddress.대구시, "000-0000-0000",
                LocalDateTime.now());
        Optional<PlayerInfo> findPlayerInfo = this.playerInfoRepository.findById(playerInfo.getId());

        if (findPlayerInfo.isPresent()) {
            var result = findPlayerInfo.get();
            Assertions.assertEquals(result.getAge(), PlayerInfoAge.AGE_40);

            entityManager.remove(playerInfo);

            Optional<PlayerInfo> deleteUser = this.playerInfoRepository.findById(playerInfo.getId());
            if (deleteUser.isPresent()) {
                Assertions.assertNull(deleteUser.get());
            }
        } else {
            Assertions.assertNotNull(findPlayerInfo.get());
        }
    }

    private PlayerInfo setUp(PlayerInfoGender gender, PlayerInfoAge age, PlayerInfoAddress address,
            String tel, LocalDateTime updatedAt) {
        PlayerInfo playerInfo = new PlayerInfo();

        playerInfo.setGender(gender);
        playerInfo.setAge(age);
        playerInfo.setAddress(address);
        playerInfo.setTel(tel);
        playerInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(playerInfo);
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
