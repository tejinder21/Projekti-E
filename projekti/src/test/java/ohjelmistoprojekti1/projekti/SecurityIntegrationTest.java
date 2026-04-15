package ohjelmistoprojekti1.projekti;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenNoAuth_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenValidUser_thenAuthorized() throws Exception {
        mockMvc.perform(get("/api/events")
                .with(httpBasic("seller", "seller123")))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidPassword_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/events")
                .with(httpBasic("seller", "wrong")))
                .andExpect(status().isUnauthorized());
    }
}