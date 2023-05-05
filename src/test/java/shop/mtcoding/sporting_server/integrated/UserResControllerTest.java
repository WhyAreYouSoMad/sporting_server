package shop.mtcoding.sporting_server.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.AbstractControllerTest;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dummy.ComapnyPaymentResDummy;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.court_payment.entity.CourtPayment;
import shop.mtcoding.sporting_server.modules.court_payment.repository.CourtPaymentRepository;
import shop.mtcoding.sporting_server.modules.court_reservation.repository.CourtReservationRepository;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@DisplayName("예약 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest
public class UserResControllerTest extends AbstractControllerTest {
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    private ComapnyPaymentResDummy dummy = new ComapnyPaymentResDummy();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private ProfileFileRepository profileFileRepository;
    @Autowired
    private PlayerInfoRepository playerInfoRepository;
    @Autowired
    private CompanyInfoRepository companyInfoRepository;
    @Autowired
    private SportCategoryRepository sportCategoryRepository;
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private StadiumCourtRepository stadiumCourtRepository;
    @Autowired
    private CourtPaymentRepository courtPaymentRepository;
    @Autowired
    private CourtReservationRepository courtReservationRepository;
    @Autowired
    private EntityManager em;
    @MockBean
    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        User playerUser = userRepository.save(dummy.newPlayerUser("ssar", "ssar"));
        FileInfo fileInfo1 = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.플레이어프로필));
        profileFileRepository.save(dummy.newProfileFile(fileInfo1));
        PlayerInfo playerInfo = playerInfoRepository.save(dummy.newPlayerInfo(playerUser, fileInfo1));

        User companyUser = userRepository.save(dummy.newCompanyUser("cos", "cos"));
        FileInfo fileInfo2 = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.기업프로필));
        profileFileRepository.save(dummy.newProfileFile(fileInfo2));
        CompanyInfo companyInfo = companyInfoRepository.save(dummy.newCompanyInfo(companyUser, fileInfo2));

        SportCategory sportCategory = sportCategoryRepository.save(dummy.newSportCategory());
        Stadium stadium = stadiumRepository.save(dummy.newStadium(companyInfo, sportCategory));

        FileInfo fileInfo3 = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.코트사진));
        profileFileRepository.save(dummy.newProfileFile(fileInfo2));
        StadiumCourt stadiumCourt = stadiumCourtRepository.save(dummy.newStadiumCourt(stadium, fileInfo3));

        CourtPayment courtPayment1 = courtPaymentRepository
                .save(dummy.newCourtPayment(playerInfo, companyInfo, stadiumCourt, 40000));
        CourtPayment courtPayment2 = courtPaymentRepository
                .save(dummy.newCourtPayment(playerInfo, companyInfo, stadiumCourt, 40000));
        CourtPayment courtPayment3 = courtPaymentRepository
                .save(dummy.newCourtPayment(playerInfo, companyInfo, stadiumCourt, 40000));

        courtReservationRepository.save(dummy.newCourtReservation(playerInfo, "11", courtPayment1, stadiumCourt));
        courtReservationRepository.save(dummy.newCourtReservation(playerInfo, "12", courtPayment2, stadiumCourt));
        courtReservationRepository.save(dummy.newCourtReservation(playerInfo, "13", courtPayment3, stadiumCourt));

        em.clear();
    }

    @DisplayName("예약 리스트 출력 테스트")
    @Test
    void reservation_test() throws Exception {
        // given

        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("ssar").role("PLAYER").build());
        // when
        ResultActions resultActions = mvc
                .perform(get("/api/user/reservations")
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        String responseBody = resultActions.andReturn().getResponse()
                .getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
