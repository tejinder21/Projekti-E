package ohjelmistoprojekti1.projekti;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjektiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void whenNoAuth_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/api/events")).andExpect(status().isUnauthorized());
	}

	@Test
	void whenValidUser_thenAuthorized() throws Exception {
		mockMvc.perform(get("/api/events").with(httpBasic("user", "password"))).andExpect(status().isOk());
	}

	@Test
	void whenInvalidUser_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/api/events").with(httpBasic("user", "wrong"))).andExpect(status().isUnauthorized());
	}

}
