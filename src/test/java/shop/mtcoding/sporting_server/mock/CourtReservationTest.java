package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.topic.user_reservation.UserResController;
import shop.mtcoding.sporting_server.topic.user_reservation.UserResService;
import shop.mtcoding.sporting_server.topic.user_reservation.dto.ReservationListOutDTO;

@WebMvcTest(UserResController.class)
public class CourtReservationTest {
    @BeforeEach
    public void init() {
        User user = User.builder().id(1L).role("PLAYER").build();
        MyUserDetails myUserDetails = new MyUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                myUserDetails,
                myUserDetails.getPassword(),
                myUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserResService userResService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "ssar", roles = { "USER" })
    @DisplayName("결제 테스트")
    void payment() throws Exception {

        // given
        ReservationListOutDTO reservationOutDTO1 = new ReservationListOutDTO(1L, "name1", LocalDateTime.now(), 50000);
        ReservationListOutDTO reservationOutDTO2 = new ReservationListOutDTO(2L, "name2", LocalDateTime.now(), 30000);
        List<ReservationListOutDTO> reservationListOutDTO = new ArrayList();
        reservationListOutDTO.add(reservationOutDTO1);
        reservationListOutDTO.add(reservationOutDTO2);

        given(this.userResService.getReservationList(1L)).willReturn(reservationListOutDTO);
        // When
        ResultActions resultActions = this.mvc.perform(
                get("/api/user/reservations")
                        .with(csrf()));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("name1"));
    }
}
