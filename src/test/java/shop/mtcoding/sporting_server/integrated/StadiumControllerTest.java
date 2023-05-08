package shop.mtcoding.sporting_server.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.security.test.context.support.WithMockUser;
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
import shop.mtcoding.sporting_server.core.enums.field.fk_fields.SportCategoryType;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
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
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;

@DisplayName("경기장 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest
public class StadiumControllerTest extends AbstractControllerTest {

        private DummyEntity dummy = new DummyEntity();

        @Autowired
        private MockMvc mvc;
        @Autowired
        private ObjectMapper om;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private CompanyInfoRepository companyInfoRepository;
        @Autowired
        private FileInfoRepository fileInfoRepository;
        @Autowired
        private ProfileFileRepository profileFileRepository;
        @Autowired
        private SportCategoryRepository sportCategoryRepository;
        @Autowired
        private PlayerInfoRepository playerInfoRepository;
        @Autowired
        private StadiumCourtRepository stadiumCourtRepository;
        @Autowired
        private StadiumRepository stadiumRepository;
        @Autowired
        private EntityManager em;
        @MockBean
        private MyUserDetails myUserDetails;

        @BeforeEach
        public void setUp() {
                User companyUser = userRepository.save(dummy.newCompanyUser("cos", "cos"));
                FileInfo fileInfo = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.기업프로필));
                profileFileRepository.save(dummy.newProfileFile(fileInfo));
                CompanyInfo companyInfo = companyInfoRepository.save(dummy.newCompanyInfo(companyUser, fileInfo));

                User playerUser = userRepository.save(dummy.newPlayerUser("ssar", "ssar"));
                FileInfo fileInfoPlayer = fileInfoRepository.save(dummy.newFileInfo(FileInfoSource.플레이어프로필));
                playerInfoRepository.save(dummy.newPlayerInfo(playerUser, fileInfoPlayer));

                FileInfo fileInfoCourt = fileInfoRepository.save(new FileInfo(2L, FileInfoSource.코트사진));
                profileFileRepository.save(dummy.newProfileFileCourt(fileInfoCourt));

                FileInfo fileInfoStadium = fileInfoRepository.save(new FileInfo(3L, FileInfoSource.경기장사진));
                profileFileRepository.save(dummy.newProfileFileStadium(fileInfoStadium));

                SportCategory sportCategory1 = sportCategoryRepository
                                .save(SportCategory.builder().sport("볼링").createdAt(LocalDateTime.now()).build());

                Stadium stadium1 = stadiumRepository
                                .save(dummy.newStadium(companyInfo, sportCategory1, fileInfoStadium));

                stadiumCourtRepository.save(dummy.newStadiumCourt(stadium1, fileInfoCourt));
                stadiumCourtRepository.save(dummy.newStadiumCourt(stadium1, fileInfoCourt));

                em.clear();
        }

        @DisplayName("경기장 등록")
        @WithMockUser(username = "cos", roles = { "Company" })
        @Test
        public void stadium_save_test() throws Exception {
                // given
                String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());
                MyUserDetails myUserDetails = new MyUserDetails(
                                User.builder().id(1L).nickname("cos").role("COMPANY").build());
                StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO = new StadiumRequest.StadiumRegistrationInDTO();

                stadiumRegistrationInDTO.setName("볼링 경기장");
                stadiumRegistrationInDTO.setAddress("부산 진구");
                stadiumRegistrationInDTO.setCategory(SportCategoryType.볼링.toString());
                String requestBody = om.writeValueAsString(stadiumRegistrationInDTO);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/company/stadiums").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("경기장 리스트 조회(플레이어)")
        @WithMockUser(username = "ssar", roles = { "Player" })
        @Test
        public void stadium_findAll_test() throws Exception {
                // given
                String jwt = MyJwtProvider.create(User.builder().id(2L).nickname("ssar").role("PLAYER").build());

                String keyword = "볼링";

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/stadiums")
                                                .param("keyword", keyword)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("경기장 리스트 조회(기업)")
        @WithMockUser(username = "cos", roles = { "Company" })
        @Test
        public void stadium_company_findAll_test() throws Exception {
                // given
                String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());

                MyUserDetails myUserDetails = new MyUserDetails(
                                User.builder().id(1L).nickname("cos").role("COMPANY").build());

                String keyword = "야구";

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/company/stadiums")
                                                .param("keyword", keyword)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("경기장 수정 FORM")
        @WithMockUser(username = "cos", roles = { "Company" })
        @Test
        public void stadium_updateForm_test() throws Exception {
                // given
                String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());

                Long stadiumId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/company/stadium/" + stadiumId)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("경기장 디테일")
        @WithMockUser(username = "ssar", roles = { "Player" })
        @Test
        public void stadium_detail_test() throws Exception {
                // given
                String jwt = MyJwtProvider.create(User.builder().id(2L).nickname("ssar").role("PLAYER").build());

                Long stadiumId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/stadium/" + stadiumId)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("경기장 수정")
        @WithMockUser(username = "cos", roles = { "Company" })
        @Test
        public void stadium_update_test() throws Exception {
                // given
                String jwt = MyJwtProvider.create(User.builder().id(1L).nickname("cos").role("COMPANY").build());

                StadiumRequest.StadiumUpdateInDTO.CourtDTO.CourtFileDTO courtFileDTO1 = new StadiumRequest.StadiumUpdateInDTO.CourtDTO.CourtFileDTO();
                courtFileDTO1.setId(2L);
                courtFileDTO1.setFileBase64("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAaUAAAGy");

                List<StadiumRequest.StadiumUpdateInDTO.CourtDTO> courtDTO = new ArrayList<>();
                courtDTO.add(new StadiumRequest.StadiumUpdateInDTO.CourtDTO(1L, "COURT1", "COURT1 CONTENT", 5, 20000,
                                courtFileDTO1));

                StadiumRequest.StadiumUpdateInDTO.StadiumFileDTO stadiumFileDTO = new StadiumRequest.StadiumUpdateInDTO.StadiumFileDTO();
                stadiumFileDTO.setId(3L);
                stadiumFileDTO.setFileBase64("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA");

                StadiumRequest.StadiumUpdateInDTO stadiumUpdateInDTO = new StadiumRequest.StadiumUpdateInDTO();
                stadiumUpdateInDTO.setId(1L);
                stadiumUpdateInDTO.setAddress("부산 진구");
                stadiumUpdateInDTO.setStatus(StadiumStatus.운영중.toString());
                stadiumUpdateInDTO.setStartTime("12:00");
                stadiumUpdateInDTO.setEndTime("18:30");
                stadiumUpdateInDTO.setSport("볼링");
                stadiumUpdateInDTO.setSourceFile(stadiumFileDTO);
                stadiumUpdateInDTO.setCourts(courtDTO);

                String requestBody = om.writeValueAsString(stadiumUpdateInDTO);
                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/company/stadiums")
                                                .content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

}