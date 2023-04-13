package shop.mtcoding.sporting_server.jpa.file;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
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
import shop.mtcoding.sporting_server.modules.file.entity.File;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
public class FileRepositoryTest {

    @Autowired
    private PlayerInfoRepository playerInfoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE file_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

    }

    private File setUp() {

        // return this.entityManager.persist();
        return null;
    }
}
