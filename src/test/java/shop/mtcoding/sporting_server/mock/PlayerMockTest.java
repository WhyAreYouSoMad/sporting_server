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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.topic.player.PlayerController;
import shop.mtcoding.sporting_server.topic.player.PlayerService;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerFavoriteSportResponseDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerFileResponseDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerInfoResponseDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerUpdateFormOutDTO;

@WebMvcTest(PlayerController.class)
public class PlayerMockTest {

        @Autowired
        MockMvc mvc;

        @MockBean
        private PlayerService playerService;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        public void init() {
                MyLoginUser user = MyLoginUser.builder().id(1L).role("USER").build();
                MyUserDetails myUserDetails = new MyUserDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                                myUserDetails,
                                myUserDetails.getPassword(),
                                myUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        @Test
        @WithMockUser(username = "cos", roles = { "User" })
        @DisplayName("플레이어 회원가입 테스트")
        void savePlayer() throws Exception {
                // Given
                PlayerRequest.JoinInDTO joinInDTO = new PlayerRequest.JoinInDTO();
                PlayerResponse.JoinOutDto joinOutDto = new PlayerResponse.JoinOutDto(1L, "ssar", "ssar@nate.com",
                                "USER", "일반회원",
                                "22:00");

                given(this.playerService.회원가입(joinInDTO)).willReturn(joinOutDto);

                // When
                ResultActions resultActions = this.mvc.perform(
                                post("/api/joinPlayer")
                                                .with(csrf())
                                                .content(objectMapper.writeValueAsString(joinInDTO))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.email").value("ssar@nate.com"));
        }

        @Test
        @WithMockUser(username = "cos", roles = { "User" })
        @DisplayName("플레이어 정보 수정 페이지 테스트")
        void playerUpdateFormTest() throws Exception {
                // given
                Long userId = 1L;
                PlayerUpdateFormOutDTO playerUpdateFormOutDTO = new PlayerUpdateFormOutDTO(1L, "ssar123",
                                "ssar@nate.com", "1234");

                List<PlayerFavoriteSportResponseDTO> PlayerFavoriteSportResponseListDTO = new ArrayList();
                PlayerFavoriteSportResponseDTO PlayerFavoriteSportResponseDTO1 = new PlayerFavoriteSportResponseDTO(1L,
                                "축구");
                PlayerFavoriteSportResponseDTO PlayerFavoriteSportResponseDTO2 = new PlayerFavoriteSportResponseDTO(2L,
                                "농구");
                PlayerFavoriteSportResponseListDTO.add(PlayerFavoriteSportResponseDTO1);
                PlayerFavoriteSportResponseListDTO.add(PlayerFavoriteSportResponseDTO2);
                playerUpdateFormOutDTO.setPlayerFavoriteSport(PlayerFavoriteSportResponseListDTO);

                PlayerInfoResponseDTO playerInfoResponseDTO = new PlayerInfoResponseDTO(1L, "010-1001-1111",
                                PlayerInfoGender.남자, PlayerInfoAge.AGE_20,
                                PlayerInfoAddress.부산시, new PlayerFileResponseDTO(1L, "경기장 URL"));
                playerUpdateFormOutDTO.setPlayerInfo(playerInfoResponseDTO);

                given(this.playerService.getUpdateForm(userId))
                                .willReturn(playerUpdateFormOutDTO);

                // When
                ResultActions resultActions = this.mvc.perform(
                                get("/api/user/updateform")
                                                .with(csrf()));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.nickname").value("ssar123"));
        }
}
