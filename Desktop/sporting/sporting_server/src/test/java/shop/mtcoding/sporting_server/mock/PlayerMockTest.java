package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.topic.player.PlayerController;
import shop.mtcoding.sporting_server.topic.player.PlayerService;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse;

@WebMvcTest(PlayerController.class)
public class PlayerMockTest {

        @Autowired
        MockMvc mvc;

        @MockBean
        private PlayerService playerService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        @WithMockUser(username = "cos", roles = { "User" })
        @DisplayName("플레이어 회원가입 테스트")
        void savePlayer() throws Exception {
                // Given
                PlayerRequest.JoinInDTO joinInDTO = new PlayerRequest.JoinInDTO();
                PlayerResponse.JoinOutDto joinOutDto = new PlayerResponse.JoinOutDto(1L, "ssar", "ssar@nate.com",
                                "USER",
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
}
