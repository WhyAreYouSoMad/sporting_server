package shop.mtcoding.sporting_server.mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.sporting_server.topic.user.UserController;
import shop.mtcoding.sporting_server.topic.user.UserService;
import shop.mtcoding.sporting_server.topic.user.dto.UserResponse;

@WebMvcTest(UserController.class)
public class UserTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "cos", roles = { "User" })
    @DisplayName("유저 상세 정보 조회 테스트")
    public void UserDetailTest() throws Exception {

        // Given
        Long userId = 1L;
        UserResponse.UserDetailOutDto userDetailOutDto = new UserResponse.UserDetailOutDto();
        userDetailOutDto.setId(userId);
        userDetailOutDto.setEmail("ssar@nate.com");
        userDetailOutDto.setRole("PLAYER");
        given(userService.getUser(eq(userId), any())).willReturn(userDetailOutDto);

        // When
        ResultActions resultActions = mvc.perform(get("/api/user/{id}", userId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.email").value("ssar@nate.com"));
    }
}
