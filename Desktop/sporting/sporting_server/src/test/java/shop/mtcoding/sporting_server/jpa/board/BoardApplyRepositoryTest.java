package shop.mtcoding.sporting_server.jpa.board;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.sporting_server.modules.board_apply.entity.BoardApply;
import shop.mtcoding.sporting_server.modules.board_apply.repository.BoardApplyRepository;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
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

        setUp();
    }

    private BoardApply setUp() {

        BoardApply boardApply = new BoardApply();

        return this.entityManager.persist(boardApply);
    }
}