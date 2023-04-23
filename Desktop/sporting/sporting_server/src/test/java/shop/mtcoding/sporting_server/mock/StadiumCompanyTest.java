package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.topic.stadium.StadiumController;
import shop.mtcoding.sporting_server.topic.stadium.StadiumService;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtFileResponseDto;
import shop.mtcoding.sporting_server.topic.stadium.dto.CourtResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumFileResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumUpdateFomrOutDTO;

@WebMvcTest(StadiumController.class)
public class StadiumCompanyTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private StadiumService stadiumService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        MyLoginUser user = MyLoginUser.builder().id(4L).role("COMPANY").build();
        MyUserDetails myUserDetails = new MyUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                myUserDetails,
                myUserDetails.getPassword(),
                myUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(username = "cos", roles = { "Company" })
    @DisplayName("카테고리별 경기장 리스트 조회")
    void findAllList() throws Exception {

        // given
        String keyword = "농구";
        List<StadiumListOutDTO> stadiumListOutDTO = new ArrayList();

        stadiumListOutDTO.add(new StadiumListOutDTO(1L, "농구", "농구장1", 20000));
        stadiumListOutDTO.add(new StadiumListOutDTO(2L, "농구", "농구장2", 40000));

        given(this.stadiumService.findKeywordList(keyword))
                .willReturn(stadiumListOutDTO);

        // When
        ResultActions resultActions = this.mvc.perform(
                get("/api/user/stadiums" + "?keyword=" + keyword)
                        .with(csrf()));

        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();
        // System.out.println("테스트 : " + responseBody);

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[1].name").value("농구장2"));
    }

    @Test
    @WithMockUser(username = "cos", roles = { "Company" })
    @DisplayName("카테고리별 내가 등록한 경기장 리스트 조회")
    void findAllMyList() throws Exception {

        // given
        String keyword = "배구";
        Long pricipalCompanyId = 4L;
        List<StadiumMyListOutDTO> stadiumMyListOutDTO = new ArrayList();

        stadiumMyListOutDTO.add(new StadiumMyListOutDTO(6L, "배구", "a 배구장"));
        stadiumMyListOutDTO.add(new StadiumMyListOutDTO(7L, "배구", "b 배구장"));
        stadiumMyListOutDTO.add(new StadiumMyListOutDTO(8L, "배구", "c 배구장"));

        given(this.stadiumService.findKeywordMyList(pricipalCompanyId, keyword))
                .willReturn(stadiumMyListOutDTO);

        // When
        ResultActions resultActions = this.mvc.perform(
                get("/api/company/mystadiums" + "?keyword=" + keyword)
                        .with(csrf()));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[1].name").value("b 배구장"));
    }

    @Test
    @WithMockUser(username = "cos", roles = { "Company" })
    @DisplayName("경기장 수정 페이지 진입 테스트")
    void stadiumUpdateFormTest() throws Exception {

        // given
        Long stadiumId = 3L;
        Long companyUserId = 4L;
        StadiumUpdateFomrOutDTO stadiumUpdateFomrOutDTO = new StadiumUpdateFomrOutDTO(3L, "a 농구장",
                StadiumAddress.울산시, StadiumStatus.운영중,
                LocalTime.of(9, 0), LocalTime.of(18, 0), new StadiumFileResponseDTO(1L, "경기장 URL"));

        List<CourtResponseDTO> CourtResponseListDTO = new ArrayList();
        CourtResponseDTO courtResponseDTO1 = new CourtResponseDTO(3L, "a 농구장(코트1)",
                "그물상태 양호 농구장", 10, 30000,
                "농구", new CourtFileResponseDto(1L, "코트 URL1"));
        CourtResponseDTO courtResponseDTO2 = new CourtResponseDTO(4L, "a 농구장(코트2)",
                "코트상태 양호 농구장", 10, 30000,
                "농구", new CourtFileResponseDto(2L, "코트 URL2"));
        CourtResponseListDTO.add(courtResponseDTO1);
        CourtResponseListDTO.add(courtResponseDTO2);

        stadiumUpdateFomrOutDTO.setCourt(CourtResponseListDTO);

        given(this.stadiumService.getUpdateForm(companyUserId, stadiumId))
                .willReturn(stadiumUpdateFomrOutDTO);

        // When
        ResultActions resultActions = this.mvc.perform(
                get("/api/company/mystadiums/updateform/" + stadiumId)
                        .with(csrf()));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("a 농구장"));
    }
}
