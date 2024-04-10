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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceJPATest {

    @InjectMocks
    private AuthServiceJPA authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용 가능한 아이디 테스트 - 사용 가능한 아이디")
    void isAvailableUserId() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.empty());
        // when
        boolean result = authService.isAvailableUserId("test");
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("사용 가능한 아이디 테스트 - 사용 불가능한 아이디")
    void isAvailableUserId2() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.of(new User()));
        // when
        boolean result = authService.isAvailableUserId("test");
        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("사용자 추가 테스트 - 사용 가능한 아이디")
    void addUser() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.empty());
        // when
        boolean result = authService.addUser(new LoginForm("test", "test"));
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("사용자 추가 테스트 - 사용 불가능한 아이디")
    void addUser2() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.of(new User()));
        // when
        boolean result = authService.addUser(new LoginForm("test", "test"));
        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("로그인 테스트 - 로그인 성공")
    void authenticateSuccess() {
        // given
        User user = new User("test", "None", "encodedPassword", 1, Role.USER);
        when(userRepository.findById("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rightPassword", "encodedPassword")).thenReturn(true);

        // when
        boolean result = authService.authenticate(new LoginForm("test", "rightPassword"));

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("로그인 테스트 - 로그인 실패(아이디 없음)")
    void authenticateFailNoUser() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.empty());

        // when
        boolean result = authService.authenticate(new LoginForm("test", "rightPassword"));

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("로그인 테스트 - 로그인 실패(비밀번호 불일치)")
    void authenticateFailWrongPassword() {
        // given
        User user = new User("test", "None", "encodedPassword", 1, Role.USER);
        when(userRepository.findById("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // when
        boolean result = authService.authenticate(new LoginForm("test", "wrongPassword"));

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("사용자 삭제 테스트 - 사용자 존재")
    void deleteUser() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.of(new User()));
        // when
        boolean result = authService.deleteUser("test");
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("사용자 삭제 테스트 - 사용자 미존재")
    void deleteUser2() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.empty());
        // when
        boolean result = authService.deleteUser("test");
        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 사용자 존재, 비밀번호 일치")
    void changePassword() {
        // given
        User user = new User("test", "None", "encodedPassword", 1, Role.USER);
        when(userRepository.findById("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rightPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        // when
        User result = authService.changePassword("test", "rightPassword", "newPassword");
        // then
        assertTrue(result.getPassword().equals("newEncodedPassword"));
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 사용자 미존재")
    void changePassword2() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.empty());
        // when then
        assertThrows(IllegalArgumentException.class, () -> authService.changePassword("test", "rightPassword", "newPassword"));
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 사용자 존재, 비밀번호 불일치")
    void changePassword3() {
        // given
        User user = new User("test", "None", "encodedPassword", 1, Role.USER);
        when(userRepository.findById("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);
        // when
        assertThrows(IllegalArgumentException.class, () -> authService.changePassword("test", "wrongPassword", "newPassword"));
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 사용자 존재, 비밀번호 일치, 비밀번호 변경 실패")
    void changePassword4() {
        // given
        User user = new User("test", "None", "encodedPassword", 1, Role.USER);
        when(userRepository.findById("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rightPassword", "encodedPassword")).thenReturn(true);
        // when
        User result = authService.changePassword("test", "rightPassword", "newPassword");
        // then
        assertTrue(result == null);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트(어드민 용) - 사용자 존재")
    void changePassword5() {
        // given
        User user = new User("test", "None", "encodedPassword", 1, Role.USER);
        when(userRepository.findById("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        // when
        User result = authService.changePassword("test", "newPassword");
        // then
        assertTrue(result.getPassword().equals("newEncodedPassword"));
    }


    @Test
    @DisplayName("비밀번호 변경 테스트(어드민 용) - 사용자 미존재")
    void changePassword6() {
        // given
        when(userRepository.findById("test")).thenReturn(Optional.empty());
        // when
        assertThrows(IllegalArgumentException.class, () -> authService.changePassword("test", "newPassword"));
    }

    @Test
    @DisplayName("유저 리스트 테스트")
    void getUserList() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(
                new User("test1", "None", "encodedPassword1", 1, Role.USER),
                new User("test2", "None", "encodedPassword2", 1, Role.USER)
        ));

        // when
        List<User> result = authService.getUserList();

        // then
        assertEquals(2, result.size());
    }
}

