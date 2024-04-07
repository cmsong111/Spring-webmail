package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.user.Role;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JamesWebAdmin jamesWebAdmin;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("사용 가능한 아이디 테스트 - 사용 가능한 아이디")
    void isAvailableUserId() {
        //given
        given(userRepository.findById("test")).willReturn(Optional.of(new User()));

        //when
        boolean result = authService.isAvailableUserId("test");

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("사용 가능한 아이디 테스트 - 사용 불가능한 아이디")
    void isAvailableUserId2() {
        //given
        given(userRepository.findById("test")).willReturn(Optional.empty());

        //when
        boolean result = authService.isAvailableUserId("test");

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("로그인 테스트 - 로그인 성공")
    void authenticateSuccess() {
        //given
        given(userRepository.findById("test")).willReturn(Optional.of(new User
                ("test", "None", "encodedPassword", 1, Role.USER)));
        given(passwordEncoder.matches("rightPassword", "encodedPassword")).willReturn(true);

        //when
        boolean result = authService.authenticate(new LoginForm("test", "rightPassword"));

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("로그인 테스트 - 로그인 실패(아이디 없음)")
    void authenticateFailNoUser() {
        //given
        given(userRepository.findById("test")).willReturn(Optional.empty());

        //when
        boolean result = authService.authenticate(new LoginForm("test", "rightPassword"));

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("로그인 테스트 - 로그인 실패(비밀번호 불일치)")
    void authenticateFailWrongPassword() {
        //given
        given(userRepository.findById("test")).willReturn(Optional.of(new User
                ("test", "None", "encodedPassword", 1, Role.USER)));
        given(passwordEncoder.matches("wrongPassword", "encodedPassword")).willReturn(false);

        //when
        boolean result = authService.authenticate(new LoginForm("test", "wrongPassword"));

        //then
        assertFalse(result);
    }
}
