package shop.mtcoding.sporting_server.jpa.user_player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        setUp("ssar", "ssar@naver.com", "1234", "player", LocalDateTime.now(), LocalDateTime.now(), UserStatus.인증대기);
    }

    @Test
    void selectAll() {
        List<User> users = userRepository.findAll();
        Assertions.assertNotEquals(users.size(), 0);

        User user = users.get(0);
        Assertions.assertEquals(user.getNickname(), "ssar");
    }

    @Test
    void selectAndUpdate() {
        var optionalUser = this.userRepository.findById(1L);

        if (optionalUser.isPresent()) {
            var result = optionalUser.get();
            Assertions.assertEquals(result.getNickname(), "ssar");

            var nickName = "ssar123";
            result.setNickname(nickName);
            User merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getNickname(), "ssar123");
        } else {
            Assertions.assertNotNull(optionalUser.get());
        }
    }

    @Test
    void insertAndDelete() {
        User user = setUp("love", "love@nate.com", "1234", "player", LocalDateTime.now(), LocalDateTime.now(),
                UserStatus.인증대기);
        Optional<User> findUser = this.userRepository.findById(user.getId());

        if (findUser.isPresent()) {
            var result = findUser.get();
            Assertions.assertEquals(result.getNickname(), "love");

            entityManager.remove(user);

            Optional<User> deleteUser = this.userRepository.findById(user.getId());
            if (deleteUser.isPresent()) {
                Assertions.assertNull(deleteUser.get());
            }
        } else {
            Assertions.assertNotNull(findUser.get());
        }
    }

    private User setUp(String nickname, String email, String password, String role, LocalDateTime createdAt,
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
