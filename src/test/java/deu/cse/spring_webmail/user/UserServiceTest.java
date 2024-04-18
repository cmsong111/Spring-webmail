package deu.cse.spring_webmail.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 정보 로드 테스트 - 성공")
    void loadUserByUsername() {
        // Given
        String username = "testuser";
        List<Role> roles = List.of(Role.USER);
        User user = User.builder()
                .userName(username)
                .password("testpassword")
                .roles(roles)
                .build();

        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Then
        assertEquals(user.getUserName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        for (Role role : roles) {
            assertTrue(user.getRoles().contains(role));
        }
    }

    @Test
    @DisplayName("사용자 정보 로드 테스트 - 실패")
    void loadUserByUsernameFail() {
        // Given
        String username = "testuser";
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.loadUserByUsername(username));
    }
}
