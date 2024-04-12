package deu.cse.spring_webmail.auth;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = {AuthController.class})
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
class AuthControllerTest {


    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("회원가입 페이지 이동 테스트")
    void signup() throws Exception {
        mockMvc.perform(get("/auth/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/sign_up"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 처리 테스트")
    void signupDo() throws Exception {
        // Given
        String username = "testuser";
        String password = "testpassword";
        when(authService.addUser(new LoginForm(username, password))).thenReturn(true);
        // When & Then
        mockMvc.perform(post("/auth/signup.do")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("로그인 실패 페이지 이동 테스트")
    void loginFail() throws Exception {
        mockMvc.perform(get("/auth/login_fail"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login_fail"))
                .andExpect(status().isOk());
    }
}
