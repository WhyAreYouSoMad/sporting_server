package shop.mtcoding.sporting_server.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import shop.mtcoding.sporting_server.modules.company_info.repository.CompanyInfoRepository;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;

@DisplayName("기업 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CompanyControllerTest extends AbstractControllerTest {

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
    private CompanyInfoRepository companyInfoRepository;
    @Autowired
    private EntityManager em;
    @MockBean
    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() {
        User companyUser = userRepository.save(dummy.newCompanyUser("cos", "cos"));
        FileInfo fileInfo = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.기업프로필));
        profileFileRepository.save(dummy.newProfileFile(fileInfo));
        companyInfoRepository.save(dummy.newCompanyInfo(companyUser, fileInfo));
        em.clear();
    }

    @DisplayName("Company 회원가입 테스트")
    @Test
    void join_company_test() throws Exception {
        // given
        CompanyRequest.JoinInDTO joinInDTO = CompanyRequest.JoinInDTO
                .builder()
                .email("ssar2@nate.com")
                .password("1234")
                .passwordCon("1234")
                .build();

        String requestBody = om.writeValueAsString(joinInDTO);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/joinCompany")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("Company 정보수정 페이지 테스트")
    @Test
    void update_form_test() throws Exception {
        // given
        Long userId = 1L;
        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());
        // when
        ResultActions resultActions = mvc
                .perform(get("/api/company/" + userId)
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("Company 정보수정 테스트")
    @Test
    void update_test() throws Exception {
        // given
        String requestJSON = "{\n" +
                "  \"nickname\": \"cos update\",\n" +
                "  \"password\": \"1234\",\n" +
                "  \"tel\": \"010-1234-5678\",\n" +
                "  \"businessAddress\": \"부산 남구\",\n" +
                "  \"businessNumber\":\"1234-45-789542\",\n" +
                "  \"sourceFile\": {\n" +
                "    \"id\": 2,\n" +
                "    \"fileBase64\": \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAaUAAAGy\"\n" +
                "  }\n" +
                "}";

        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());
        // when
        ResultActions resultActions = mvc
                .perform(put("/api/company/update")
                        .content(requestJSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        // String responseBody = resultActions.andReturn().getResponse()
        // .getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}