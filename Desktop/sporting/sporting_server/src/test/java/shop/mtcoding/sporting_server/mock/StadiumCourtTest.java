package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.topic.stadium_court.StadiumCourtController;
import shop.mtcoding.sporting_server.topic.stadium_court.StadiumCourtService;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtRequest.StadiumCourtInDTO;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumCourtOutDTO;

@WebMvcTest(StadiumCourtController.class)
public class StadiumCourtTest {

        @Autowired
        MockMvc mvc;

        @MockBean
        private StadiumCourtService stadiumCourtService;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        public void init() {
                MyLoginUser user = MyLoginUser.builder().id(3L).role("COMPANY").build();
                MyUserDetails myUserDetails = new MyUserDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                                myUserDetails,
                                myUserDetails.getPassword(),
                                myUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        @Test
        @WithMockUser(username = "cos", roles = { "Company" })
        @DisplayName("코트 등록 테스트")
        void stadiumCOuTest() throws Exception {

                // given
                Long stadiumId = 1L;
                FileInfo fileInfo = new FileInfo(10L, FileInfoSource.코트사진);
                StadiumCourtRequest.StadiumCourtInDTO stadiumCourtInDTO = new StadiumCourtInDTO(fileInfo, "야구장코트",
                                "코트가좋아요", 20, 50000);
                StadiumCourtResponse.StadiumCourtOutDTO stadiumCourtOutDTO = new StadiumCourtOutDTO(1L, 1L, fileInfo,
                                "야구장코트", "코트가좋아요", 20, 50000, LocalDateTime.now(), LocalDateTime.now(),
                                StadiumCourtStatus.등록대기);

                given(this.stadiumCourtService.save(stadiumCourtInDTO, stadiumId))
                                .willReturn(stadiumCourtOutDTO);

                // When
                ResultActions resultActions = this.mvc.perform(
                                post("/api/company/stadiums/court/" + stadiumId)
                                                .with(csrf())
                                                .content(objectMapper.writeValueAsString(stadiumCourtInDTO))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));

                // String responseBody =
                // resultActions.andReturn().getResponse().getContentAsString();
                // System.out.println("테스트 : " + responseBody);

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.content").value("코트가좋아요"));
        }

}
