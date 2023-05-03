package shop.mtcoding.sporting_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import shop.mtcoding.sporting_server.AbstractControllerTest;
import shop.mtcoding.sporting_server.core.auth.MyUserDetails;
import shop.mtcoding.sporting_server.core.dummy.DummyEntity;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

import javax.persistence.EntityManager;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@SpringBootTest
public class StadiumControllerTest {

    private DummyEntity dummy = new DummyEntity();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;
    @MockBean
    private MyUserDetails myUserDetails;

    @BeforeEach
    public void setUp() {
        userRepository.save(dummy.newPlayerUser("ssar", "ssar"));
        userRepository.save(dummy.newCompanyUser("cos", "cos"));

        em.clear();
    }

}
