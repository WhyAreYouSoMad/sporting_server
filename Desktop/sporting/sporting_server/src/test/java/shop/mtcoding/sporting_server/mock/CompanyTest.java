package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.core.enums.role.RoleType;
import shop.mtcoding.sporting_server.topic.company.CompanyController;
import shop.mtcoding.sporting_server.topic.company.CompanyService;

import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;


@WebMvcTest(CompanyController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CompanyTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("company 회원가입")
    @WithMockUser(username = "ssar", roles = { "USER" })
    void joinCompany() throws Exception {


        // given
        CompanyRequest.JoinInDTO joinInDTO = new CompanyRequest.JoinInDTO();
        CompanyResponse.JoinDTO joinOutDTO = new  CompanyResponse.JoinDTO("1234","1234","sdif@sdflk.dsf","컴퍼니");
        given(this.companyService.회원가입(joinInDTO)).willReturn(joinOutDTO);

        // When
        ResultActions perform = this.mvc.perform(
                post("/api/joinCompany")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(joinInDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
                String responseBody = perform.andReturn().getResponse().getContentAsString();

        // Then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("1234"));

    }
}
