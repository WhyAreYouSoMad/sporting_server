package shop.mtcoding.sporting_server.mock;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
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
import shop.mtcoding.sporting_server.topic.company.CompanyController;
import shop.mtcoding.sporting_server.topic.company.CompanyService;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyInfoResponseDTO;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyRequest;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyResponse;
import shop.mtcoding.sporting_server.topic.company.dto.CompanyUpdateFormOutDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerFavoriteSportResponseDTO;

@WebMvcTest(CompanyController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CompanyTest {

        @Autowired
        MockMvc mvc;

        @MockBean
        private CompanyService companyService;

        private final ObjectMapper objectMapper = new ObjectMapper();

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
        @DisplayName("company 회원가입")
        @WithMockUser(username = "ssar", roles = { "USER" })
        void joinCompany() throws Exception {

                // given
                CompanyRequest.JoinInDTO joinInDTO = new CompanyRequest.JoinInDTO();
                CompanyResponse.JoinDTO joinOutDTO = new CompanyResponse.JoinDTO(1L, "1234", "sdif@sdflk.dsf",
                                "인증대기", "ddd");
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

        @Test
        @WithMockUser(username = "cos", roles = { "User" })
        @DisplayName("컴퍼니 정보 수정 페이지 테스트")
        void CompanyUpdateFormTest() throws Exception {
                // given
                Long id = 3L;
                CompanyUpdateFormOutDTO companyUpdateFormOutDTO = new CompanyUpdateFormOutDTO(3L, "baseball451",
                                "cos@nate.com", "1234");

                CompanyInfoResponseDTO companyInfoResponseDTO = new CompanyInfoResponseDTO(1L, "010-1001-2222",
                                "부산시 연제구", "111-11-11111");
                companyUpdateFormOutDTO.setCompanyInfo(companyInfoResponseDTO);

                given(this.companyService.getUpdateForm(id))
                                .willReturn(companyUpdateFormOutDTO);

                // When
                ResultActions resultActions = this.mvc.perform(
                                get("/api/company/updateform")
                                                .with(csrf()));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // Then
                resultActions
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.nickname").value("baseball451"));
        }
}
