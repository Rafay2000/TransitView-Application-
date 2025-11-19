package hiof_project.ports.in;

import hiof_project.domain.service.AuthService;
import hiof_project.infrastructure.adapters.api.dto.RegisterDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    // Register
    @Test
    void register_ShouldReturnCreated_WhenUserIsRegistered() throws Exception {
        RegisterDTO dto = new RegisterDTO("ola@nordmann.no", "Password1");

        doNothing().when(authService).register(dto);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ola@nordmann.no\",\"password\":\"Password1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Bruker er registrert"));
    }
    @Test
    void login_ShouldReturnOk_WhenCredentialsAreCorrect() throws Exception {
        when(authService.login("ola@nordmann.no", "Password1"))
                .thenReturn(true);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ola@nordmann.no\",\"password\":\"Password1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Innlogging vellykket"));
    }
    @Test
    void login_ShouldReturnUnauthorized_WhenCredentialsAreWrong() throws Exception {
        when(authService.login("ola@nordmann.no", "Password1"))
                .thenThrow(new IllegalArgumentException("Feil epost eller passord"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ola@nordmann.no\",\"password\":\"Password1\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Feil epost eller passord"));
    }
}
