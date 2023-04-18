package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.topic.stadium.StadiumController;
import shop.mtcoding.sporting_server.topic.stadium.StadiumService;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumListOutDTO;
import shop.mtcoding.sporting_server.topic.stadium.dto.StadiumMyListOutDTO;

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
}
