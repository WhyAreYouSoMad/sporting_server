package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.topic.stadium.StadiumController;
import shop.mtcoding.sporting_server.topic.stadium.StadiumService;
import shop.mtcoding.sporting_server.topic.stadium.dto.SportCategoryDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumDetailOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumFileResponseDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumRequest.StadiumRegistrationInDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumResponse.StadiumRegistrationOutDTO;

@WebMvcTest(StadiumController.class)
public class StadiumPlayerTest {

        @Autowired
        MockMvc mvc;

        @MockBean
        private StadiumService stadiumService;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        public void init() {
                MyLoginUser user = MyLoginUser.builder().id(1L).role("PLAYER").build();
                MyUserDetails myUserDetails = new MyUserDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                                myUserDetails,
                                myUserDetails.getPassword(),
                                myUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        @Test
        @WithMockUser(username = "cos", roles = { "Company" })
        @DisplayName("경기장 등록 테스트")
        void stadiumRegistrationTest() throws Exception {

                // given
                Long id = 1L;
                StadiumRequest.StadiumRegistrationInDTO stadiumRegistrationInDTO = new StadiumRegistrationInDTO("경기장1",
                                "부산시",
                                "농구");
                StadiumResponse.StadiumRegistrationOutDTO stadiumRegistrationOutDTO = new StadiumRegistrationOutDTO(5L,
                                "경기장1",
                                "부산시", "농구", "09:00", "18:00");

                given(this.stadiumService.save(id, stadiumRegistrationInDTO))
                                .willReturn(stadiumRegistrationOutDTO);

                // When
                ResultActions resultActions = this.mvc.perform(
                                post("/api/company/stadiums")
                                                .with(csrf())
                                                .content(objectMapper.writeValueAsString(stadiumRegistrationInDTO))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));

                // String responseBody =
                // resultActions.andReturn().getResponse().getContentAsString();
                // System.out.println("테스트 : " + responseBody);

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.name").value("경기장1"));
        }

        @Test
        @WithMockUser(username = "ssar", roles = { "Player" })
        @DisplayName("경기장 디테일 페이지")
        void stadiumDetailTest() throws Exception {

                // given
                Long stadiumId = 1L;

                StadiumDetailOutDTO stadiumDetailDTO = new StadiumDetailOutDTO(LocalTime.of(9, 0), LocalTime.of(18, 0),
                                "a 야구장", 35.1846, 128.9863, StadiumAddress.부산시,
                                new StadiumFileResponseDTO(1L, "경기장 URL"));
                SportCategoryDTO category = new SportCategoryDTO(1L, "야구");

                given(this.stadiumService.detail(stadiumId))
                                .willReturn(stadiumDetailDTO);

                // When
                ResultActions resultActions = this.mvc.perform(
                                get("/api/user/detail/" + stadiumId)
                                                .with(csrf()));

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.name").value("a 야구장"));
        }

}