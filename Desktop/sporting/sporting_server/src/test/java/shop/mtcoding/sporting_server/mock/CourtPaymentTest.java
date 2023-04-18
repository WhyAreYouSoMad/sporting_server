package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import shop.mtcoding.sporting_server.topic.payment.PaymentController;
import shop.mtcoding.sporting_server.topic.payment.PaymentService;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest.FormInDTO;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentResponse;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentResponse.FormOutDTO;

@WebMvcTest(PaymentController.class)
public class CourtPaymentTest {

        @Autowired
        MockMvc mvc;

        @MockBean
        private PaymentService paymentService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        @WithMockUser(username = "ssar", roles = { "USER" })
        @DisplayName("결제 페이지 테스트")
        void paymentFormTest() throws Exception {

                // given
                Long id = 3L;
                PaymentRequest.FormInDTO formInDTO = new FormInDTO(LocalDate.parse("2023-06-11"), "5");
                PaymentResponse.FormOutDTO formOutDTO = new FormOutDTO("a 농구장", "그물상태 양호 농구장",
                                LocalDate.parse("2023-06-11"), "5",
                                30000);

                given(this.paymentService.getForm(id, formInDTO))
                                .willReturn(formOutDTO);

                // When
                ResultActions resultActions = this.mvc.perform(
                                post("/api/user/payments/form/" + id)
                                                // .header(HttpHeaders.AUTHORIZATION, jwtTokenSetup())
                                                .with(csrf())
                                                .content(objectMapper.writeValueAsString(formInDTO))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.stadiumName").value("a 농구장"));
        }

}

// // Security 권한 처리
// public String jwtTokenSetup() {
// User loginUser =
// User.builder().id(1L).role("USER").password("1234").email("ssar@nate.com")
// .status(UserStatus.일반회원)
// .build();
// String jwt = MyJwtProvider.create(loginUser);
// return jwt;
// }