package shop.mtcoding.sporting_server.jpa.user_player;

import java.time.LocalDateTime;
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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
public class SportCategoryRepositoryTest {

    @Autowired
    private SportCategoryRepository sportCategoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {

        em.createNativeQuery("ALTER TABLE sport_category_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        setUp("축구", LocalDateTime.now());
    }

    @Test
    void selectAll() {
        List<SportCategory> SportCategoryList = sportCategoryRepository.findAll();
        Assertions.assertNotEquals(SportCategoryList.size(), 0);

        SportCategory sportCategory = SportCategoryList.get(0);
        Assertions.assertEquals(sportCategory.getSport(), "축구");
    }

    @Test
    void selectAndUpdate() {
        var optionalSportCategory = this.sportCategoryRepository.findById(1);

        if (optionalSportCategory.isPresent()) {
            var result = optionalSportCategory.get();
            Assertions.assertEquals(result.getSport(), "축구");

            var sport = "야구";
            result.setSport(sport);
            SportCategory merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getSport(), "야구");
        } else {
            Assertions.assertNotNull(optionalSportCategory.get());
        }
    }

    @Test
    void insertAndDelete() {
        SportCategory sportCategory = setUp("볼링", LocalDateTime.now());
        Optional<SportCategory> findSportCategory = this.sportCategoryRepository.findById(sportCategory.getId());

        if (findSportCategory.isPresent()) {
            var result = findSportCategory.get();
            Assertions.assertEquals(result.getSport(), "볼링");

            entityManager.remove(sportCategory);

            Optional<SportCategory> deleteSportCategory = this.sportCategoryRepository.findById(sportCategory.getId());
            if (deleteSportCategory.isPresent()) {
                Assertions.assertNull(deleteSportCategory.get());
            }
        } else {
            Assertions.assertNotNull(findSportCategory.get());
        }
    }

    private SportCategory setUp(String sport, LocalDateTime createdAt) {
        SportCategory sportCategory = new SportCategory();
        sportCategory.setSport(sport);
        sportCategory.setCreatedAt(createdAt);

        return this.entityManager.persist(sportCategory);
    }

}
