package ohjelmistoprojekti1.projekti;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AppUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateNewUser() throws Exception {
        String newUserJson = """
                {
                    "username": "testuser",
                    "passwordHash": "hashedpassword",
                    "role": "LIPUNMYYJÄ"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.role").value("LIPUNMYYJÄ"));
    }

    @Test
    void shouldReturnConflictWhenUsernameAlreadyExists() throws Exception {
        String userJson = """
                {
                    "username": "duplicateUser",
                    "passwordHash": "hashedpassword",
                    "role": "LIPUNMYYJÄ"
                }
                """;

        // Luodaan käyttäjä ensimmäisen kerran
        mockMvc.perform(post("/api/users")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());

        // Yritetään luoda sama käyttäjä uudelleen
        mockMvc.perform(post("/api/users")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict());
    }
}