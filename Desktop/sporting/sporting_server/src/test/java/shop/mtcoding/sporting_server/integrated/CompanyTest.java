package shop.mtcoding.sporting_server.integrated;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//public class CompanyTest {
//    protected MockMvc mockMvc;
//    protected final ObjectMapper objectMapper = new ObjectMapper();


//    @Test
//    void joinCompany() throws Exception {
//        CompanyRequest.JoinCompanyDTO joinCompanyDTO = new CompanyRequest.JoinCompanyDTO("baseball", "cos@nate.com", "1234", "COMPANY",null, UserStatus.인증완료);
//        this.mockMvc.perform(
//                        post("/api/join/company")
//                                .content(objectMapper.writeValueAsString(joinCompanyDTO))
//                                .accept(MediaType.APPLICATION_JSON_VALUE)
//                                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                )
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document("join-company",
//                                requestFields(getCompRequestField()),
//                                responseFields(getNoticeResponseField(""))
//                        )
//                );
//
//    }
//}
