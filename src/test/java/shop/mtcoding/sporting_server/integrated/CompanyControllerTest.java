// package shop.mtcoding.sporting_server.integrated;

// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import
// org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.jdbc.Sql;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
// import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;

// @DisplayName("기업 API")
// @AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort =
// 8080)
// @ActiveProfiles("test")
// @Sql("classpath:db/teardown.sql")
// @AutoConfigureMockMvc
// @SpringBootTest
// public class CompanyControllerTest {
// protected MockMvc mockMvc;
// protected final ObjectMapper objectMapper = new ObjectMapper();

// @Test
// void joinCompany() throws Exception {
// CompanyRequest.JoinCompanyDTO joinCompanyDTO = new
// CompanyRequest.JoinCompanyDTO("baseball", "cos@nate.com",
// "1234",
// "COMPANY", null, UserStatus.인증완료);
// this.mockMvc.perform(
// post("/api/join/company")
// .content(objectMapper.writeValueAsString(joinCompanyDTO))
// .accept(ㅐ.APPLICATION_JSON_VALUE)
// .contentType(MediaType.APPLICATION_JSON_VALUE))
// .andExpect(status().isOk())
// .andDo(print())
// .andDo(
// document("join-company",
// requestFields(getCompRequestField()),
// responseFields(getNoticeResponseField(""))));

// }
// }