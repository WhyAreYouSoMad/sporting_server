package shop.mtcoding.sporting_server.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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
import shop.mtcoding.sporting_server.core.dummy.DummyEntity;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.jwt.MyJwtProvider;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.repository.PlayerFavoriteSportRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest;

@DisplayName("플레이어 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PlayerControllerTest extends AbstractControllerTest {
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    private DummyEntity dummy = new DummyEntity();

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
    private PlayerFavoriteSportRepository playerFavoriteSportRepository;
    @Autowired
    private SportCategoryRepository sportCategoryRepository;
    @Autowired
    private EntityManager em;
    @MockBean
    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() {
        User playerUser = userRepository.save(dummy.newPlayerUser("ssar", "ssar"));
        FileInfo fileInfo = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.플레이어프로필));
        profileFileRepository.save(dummy.newPlayerProfileFile(fileInfo));
        PlayerInfo playerInfo = playerInfoRepository.save(dummy.newPlayerInfo(playerUser, fileInfo));
        SportCategory sportCategory = sportCategoryRepository.save(dummy.newSportCategory());
        playerFavoriteSportRepository.save(dummy.newFavoriteSport(sportCategory, playerInfo));
        em.clear();
    }

    @DisplayName("Player 회원가입")
    @Test
    void join_player_test() throws Exception {
        // given
        PlayerRequest.JoinInDTO joinInDTO = PlayerRequest.JoinInDTO
                .builder()
                .email("ssar1@nate.com")
                .password("1234")
                .build();

        String requestBody = om.writeValueAsString(joinInDTO);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/joinPlayer")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @DisplayName("Player 정보수정 페이지")
    @Test
    void player_update_form_test() throws Exception {
        // given
        Long userId = 1L;
        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("ssar").role("PLAYER").build());

        // when
        ResultActions resultActions = mvc
                .perform(get("/api/user/" + userId)
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("Player 정보수정")
    @Test
    void player_update_test() throws Exception {
        // given
        String requestJSON = "{\n" +
                "  \"nickname\": \"ssar1 update\",\n" +
                "  \"password\": \"12345\",\n" +
                "  \"tel\": \"010-111-1111\",\n" +
                "  \"gender\": \"여자\",\n" +
                "  \"age\": \"AGE_40\",\n" +
                "  \"address\": \"서울시1\",\n" +
                "  \"sourceFile\": {\n" +
                "   \"id\": 2,\n" +
                "   \"fileBase64\": \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAaUAAAGy\"\n" +
                "  },\n" +
                "  \"sportList\": [\n" +
                "     {\n" +
                "       \"sport\": \"축구\"\n" +
                "     }\n" +
                "  ]\n" +
                "}";
        String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("ssar").role("PLAYER").build());

        // when
        ResultActions resultActions = mvc
                .perform(put("/api/user/update")
                        .content(requestJSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
