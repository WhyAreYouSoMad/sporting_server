package shop.mtcoding.sporting_server.jpa.court_reservation;

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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.sporting_server.config.TextContextConfiguration;
import shop.mtcoding.sporting_server.core.enums.field.etc.PaymentType;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;
import shop.mtcoding.sporting_server.core.enums.field.status.CourtPaymentStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;
import shop.mtcoding.sporting_server.modules.court_payment.repository.CourtPaymentRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
@Import(TextContextConfiguration.class)
public class CourtPaymentRepositoryTest {
    @Autowired
    private CourtPaymentRepository courtPaymentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE court_payment_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        setUp(PaymentType.계좌이체, 55555, "오리진 데이터", LocalDateTime.now(), CourtPaymentStatus.결제완료);
    }

    @Test
    @Transactional
    void selectAll() {
        List<CourtPayment> courtPaymentList = courtPaymentRepository.findAll();
        Assertions.assertNotEquals(courtPaymentList.size(), 0);

        CourtPayment courtPayment = courtPaymentList.get(0);
        Assertions.assertEquals(courtPayment.getPaymentAmount(), 55555);
    }

    @Test
    void selectAndUpdate() {
        var optionalCourtPayment = this.courtPaymentRepository.findById(1L);

        if (optionalCourtPayment.isPresent()) {
            var result = optionalCourtPayment.get();
            Assertions.assertEquals(result.getPaymentAmount(), 55555);

            var courtPaymentSource = 1;
            result.setPaymentAmount(courtPaymentSource);

            CourtPayment merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getPaymentAmount(), 1);
        } else {
            Assertions.assertNotNull(optionalCourtPayment.get());
        }
    }

    @Test
    void insertAndDelete() {
        CourtPayment courtPayment = setUp2(PaymentType.포인트, 5000, "오리진 데이터2", LocalDateTime.now(),
                CourtPaymentStatus.결제완료);
        System.out.println("테스트 : " + courtPayment.getId());
        Optional<CourtPayment> findCourtPayment = this.courtPaymentRepository.findById(courtPayment.getId());

        if (findCourtPayment.isPresent()) {
            var result = findCourtPayment.get();
            Assertions.assertEquals(result.getPaymentAmount(), 5000);

            entityManager.remove(courtPayment);

            Optional<CourtPayment> deleteCourtPayment = this.courtPaymentRepository.findById(courtPayment.getId());
            if (deleteCourtPayment.isPresent()) {
                Assertions.assertNull(deleteCourtPayment.get());
            }
        } else {
            Assertions.assertNotNull(findCourtPayment.get());
        }
    }

    private CourtPayment setUp(PaymentType paymentType, Integer paymentAmount, String originDate,
            LocalDateTime createdAt, CourtPaymentStatus status) {

        CourtPayment courtPayment = new CourtPayment();

        courtPayment.setPlayerInfo(setUpPlayerInfo(PlayerInfoGender.남자, PlayerInfoAge.AGE_30, "부산시",
                "000-1234-5678", LocalDateTime.now()));
        courtPayment.setCompanyInfo(
                setUpCompanyInfo("1234-4567-789", "부산시 어딘가", "000-0000-0000", "박지연", LocalDateTime.now()));

        courtPayment.setPaymentType(paymentType);
        courtPayment.setPaymentAmount(paymentAmount);
        courtPayment.setOriginData(originDate);
        courtPayment.setCreatedAt(createdAt);
        courtPayment.setStatus(status);

        return this.entityManager.persist(courtPayment);
    }

    private CourtPayment setUp2(PaymentType paymentType, Integer paymentAmount, String originDate,
            LocalDateTime createdAt, CourtPaymentStatus status) {

        CourtPayment courtPayment = new CourtPayment();

        courtPayment.setPlayerInfo(setUpPlayerInfo(PlayerInfoGender.남자, PlayerInfoAge.AGE_30, "부산시",
                "000-1234-5678", LocalDateTime.now()));
        courtPayment.setCompanyInfo(
                setUpCompanyInfo("1234-2567-789", "천안시 어딘가", "000-0000-0000", "박지연", LocalDateTime.now()));

        courtPayment.setPaymentType(paymentType);
        courtPayment.setPaymentAmount(paymentAmount);
        courtPayment.setOriginData(originDate);
        courtPayment.setCreatedAt(createdAt);
        courtPayment.setStatus(status);

        return this.entityManager.persist(courtPayment);
    }

    private PlayerInfo setUpPlayerInfo(PlayerInfoGender gender, PlayerInfoAge age, String address,
            String tel, LocalDateTime updatedAt) {
        PlayerInfo playerInfo = new PlayerInfo();

        playerInfo.setUser(setUpUser("ssar", "ssar@naver.com", "1234", "role", LocalDateTime.now(), LocalDateTime.now(),
                UserStatus.일반회원));

        playerInfo.setGender(gender);
        playerInfo.setAge(age);
        playerInfo.setAddress(address);
        playerInfo.setTel(tel);
        playerInfo.setUpdatedAt(LocalDateTime.now());

        return this.entityManager.persist(playerInfo);
    }

    private CompanyInfo setUpCompanyInfo(String businessNumber, String businessAddress, String tel, String ceo,
            LocalDateTime updatedAt) {
        CompanyInfo companyInfo = new CompanyInfo();

        companyInfo.setUser(setUpUser2("NAVER", "naver@naver.com", "1234", "role", LocalDateTime.now(),
                LocalDateTime.now(), UserStatus.기업회원));

        companyInfo.setBusinessNumber(businessNumber);
        companyInfo.setBusinessAddress(businessAddress);
        companyInfo.setTel(tel);
        companyInfo.setCeo(ceo);
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

    private User setUpUser2(String nickname, String email, String password, String role, LocalDateTime createdAt,
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
