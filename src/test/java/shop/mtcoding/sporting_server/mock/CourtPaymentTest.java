package shop.mtcoding.sporting_server.mock;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import shop.mtcoding.sporting_server.core.jwt.MyLoginUser;
import shop.mtcoding.sporting_server.topic.payment.PaymentController;
import shop.mtcoding.sporting_server.topic.payment.PaymentService;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest.ReceiptInDTO;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest.ReceiptInDTO.ReceiptDataDTO;
import shop.mtcoding.sporting_server.topic.payment.dto.PaymentRequest.ReceiptInDTO.ReceiptDataDTO.Metadata;

@WebMvcTest(PaymentController.class)
public class CourtPaymentTest {
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

        @Autowired
        MockMvc mvc;

        @MockBean
        private PaymentService paymentService;

        @Autowired
        private ObjectMapper objectMapper;

        // @Test
        // @WithMockUser(username = "ssar", roles = { "USER" })
        // @DisplayName("결제 페이지 테스트")
        // void paymentFormTest() throws Exception {

        // // given
        // Long id = 3L;
        // PaymentRequest.FormInDTO formInDTO = new
        // FormInDTO(LocalDate.parse("2023-06-11"), "5");
        // PaymentResponse.FormOutDTO formOutDTO = new FormOutDTO("a 농구장", "그물상태 양호
        // 농구장",
        // LocalDate.parse("2023-06-11"), "5",
        // 30000);

        // given(this.paymentService.getForm(id, formInDTO))
        // .willReturn(formOutDTO);

        // // When
        // ResultActions resultActions = this.mvc.perform(
        // post("/api/user/payments/form/" + id)
        // // .header(HttpHeaders.AUTHORIZATION, jwtTokenSetup())
        // .with(csrf())
        // .content(objectMapper.writeValueAsString(formInDTO))
        // .contentType(MediaType.APPLICATION_JSON_VALUE));
        // String responseBody =
        // resultActions.andReturn().getResponse().getContentAsString();

        // // Then
        // resultActions
        // .andExpect(status().isOk())
        // .andExpect(jsonPath("$.data.stadiumName").value("a 농구장"));
        // }

        @Test
        @WithMockUser(username = "ssar", roles = { "USER" })
        @DisplayName("결제 테스트")
        void paymentFormTest() throws Exception {

                // given
                Long id = 3L;
                ReceiptDataDTO.CardData cardData = new ReceiptDataDTO.CardData();
                cardData.setTid("T1234567890");
                cardData.setCancelTid("");

                Metadata metadata = new Metadata();

                OffsetDateTime purchasedAt = OffsetDateTime.parse("2023-05-03T12:34:56.789Z");
                OffsetDateTime cancelledAt = null;
                OffsetDateTime requestedAt = OffsetDateTime.parse("2023-05-03T12:34:56.789Z");

                ReceiptDataDTO receiptDataDTO = new ReceiptDataDTO(
                                "RC12345", "OD67890", 40000, 0, 0, 0, "Product A", "Company X",
                                "https://www.example.com/pay", metadata, true, "kakaopay", "card",
                                "VISA", "USA", "USD", purchasedAt, cancelledAt, requestedAt, "en_US",
                                "https://www.example.com/receipts/RC12345", 1,
                                cardData);

                ReceiptInDTO receiptInDTO = new ReceiptInDTO("done", receiptDataDTO, "true");

                // paymentService.paymentAndReservation(1L, "2022-05-03", "7", receiptInDTO,
                // 1L);
                Mockito.doNothing().when(paymentService).paymentAndReservation(
                                Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
                                Mockito.any(ReceiptInDTO.class), Mockito.anyLong());

                String content = objectMapper.writeValueAsString(receiptInDTO);
                // When
                ResultActions resultActions = this.mvc.perform(
                                post("/api/user/payments?courtId=1&resDate=20230506&resTime=6")
                                                // .header(HttpHeaders.AUTHORIZATION, jwtTokenSetup())
                                                .with(csrf())
                                                .content(content)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();

                // Then
                resultActions
                                .andExpect(status().isOk());
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