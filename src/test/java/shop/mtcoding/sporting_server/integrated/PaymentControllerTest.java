package shop.mtcoding.sporting_server.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.AbstractControllerTest;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dummy.ComapnyPaymentResDummy;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.stadium_court.repository.StadiumCourtRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@DisplayName("결제 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest
public class PaymentControllerTest extends AbstractControllerTest {

    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    private ComapnyPaymentResDummy dummy = new ComapnyPaymentResDummy();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
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
    private EntityManager em;
    @MockBean
    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() {
        User playerUser = userRepository.save(dummy.newPlayerUser("ssar", "ssar"));
        FileInfo fileInfo1 = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.플레이어프로필));
        profileFileRepository.save(dummy.newProfileFile(fileInfo1));
        playerInfoRepository.save(dummy.newPlayerInfo(playerUser, fileInfo1));

        User companyUser = userRepository.save(dummy.newCompanyUser("cos", "cos"));
        FileInfo fileInfo2 = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.기업프로필));
        profileFileRepository.save(dummy.newProfileFile(fileInfo2));
        CompanyInfo companyInfo = companyInfoRepository.save(dummy.newCompanyInfo(companyUser, fileInfo2));

        SportCategory sportCategory = sportCategoryRepository.save(dummy.newSportCategory());
        Stadium stadium = stadiumRepository.save(dummy.newStadium(companyInfo, sportCategory));

        FileInfo fileInfo3 = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.코트사진));
        profileFileRepository.save(dummy.newProfileFile(fileInfo2));
        stadiumCourtRepository.save(dummy.newStadiumCourt(stadium, fileInfo3));
        em.clear();
    }

    @DisplayName("결제 테스트")
    @Test
    void payment_test() throws Exception {
        // given
        String requestJSON = "{\n" +
                "\"event\": \"done\",\n" +
                "\"data\": {\n" +
                " \"receipt_id\": \"RC12345\",\n" +
                " \"order_id\": \"OD67890\",\n" +
                " \"price\": 40000,\n" +
                " \"tax_free\": 0,\n" +
                " \"cancelled_price\": 0,\n" +
                " \"cancelled_tax_free\": 0,\n" +
                " \"order_name\": \"Product A\",\n" +
                " \"company_name\": \"Company X\",\n" +
                " \"gateway_url\": \"https://www.example.com/pay\",\n" +
                " \"metadata\": {},\n" +
                " \"sandbox\": true,\n" +
                " \"pg\": \"kakaopay\",\n" +
                " \"method\": \"card\",\n" +
                " \"method_symbol\": \"VISA\",\n" +
                " \"method_origin\": \"USA\",\n" +
                " \"method_origin_symbol\": \"USD\",\n" +
                " \"purchased_at\": \"2023-05-03T12:34:56.789Z\",\n" +
                " \"cancelled_at\": null,\n" +
                " \"requested_at\": \"2023-05-03T12:34:56.789Z\",\n" +
                " \"status_locale\": \"en_US\",\n" +
                " \"receipt_url\": \"https://www.example.com/receipts/RC12345\",\n" +
                " \"status\": 1,\n" +
                " \"kakao_money_data\": {\n" +
                "     \"tid\": \"T1234567890\",\n" +
                "     \"cancel_tid\": \"\"\n" +
                " }\n" +
                "}\n" +
                ", \"bootpay_event\": \"true\"\n" +
                "}";
        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("ssar").role("PLAYER").build());
        // when
        ResultActions resultActions = mvc
                .perform(post("/api/user/payments?courtId=1&resDate=20230506&resTime=6")
                        .content(requestJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
