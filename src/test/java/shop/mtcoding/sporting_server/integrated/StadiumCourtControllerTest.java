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
import shop.mtcoding.sporting_server.core.dummy.StadiumCourtResDummy;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium.repository.StadiumRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;

@DisplayName("경기장 코트 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class StadiumCourtControllerTest extends AbstractControllerTest {
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    private StadiumCourtResDummy dummy = new StadiumCourtResDummy();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private ProfileFileRepository profileFileRepository;
    @Autowired
    private CompanyInfoRepository companyInfoRepository;
    @Autowired
    private SportCategoryRepository sportCategoryRepository;
    @Autowired
    private StadiumRepository stadiumRepository;
    @MockBean
    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() {
        User companyUser = userRepository.save(dummy.newCompanyUser("cos", "cos"));
        FileInfo fileInfo = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.기업프로필));
        profileFileRepository.save(dummy.newProfileFile(fileInfo));
        CompanyInfo companyInfo = companyInfoRepository.save(dummy.newCompanyInfo(companyUser, fileInfo));

        SportCategory sportCategory = sportCategoryRepository.save(dummy.newSportCategory());
        stadiumRepository.save(dummy.newStadium(companyInfo, sportCategory));
        em.clear();
    }

    @DisplayName("Stadium Court 등록")
    @Test
    void stadium_court_save_test() throws Exception {
        // given
        Long stadiumId = 1L;
        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());
        StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO = StadiumCourtRequest.StadiumCourtInDTO
                .builder()
                .courtProfile("")
                .title("테스트 제목")
                .content("테스트 내용")
                .capacity(10)
                .courtPrice(30000)
                .build();
        String requestBody = om.writeValueAsString(stadiumCourtInDTO);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/company/stadiums/" + stadiumId + "/court")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody + "테스트 끝");

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
