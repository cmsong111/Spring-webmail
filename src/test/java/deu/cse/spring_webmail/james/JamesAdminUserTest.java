package deu.cse.spring_webmail.james;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JamesAdminUserTest {

    @InjectMocks
    static JamesAdminUser jamesWebAdmin;

    @Mock
    private RestTemplate restTemplate;


    @Test
    @DisplayName("James 서버에 사용자 추가 테스트 - 성공")
    void createUserSuccess() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.PUT.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = jamesWebAdmin.createUser(userid, password);

        // Then
        assert (result);
    }

    @Test
    @DisplayName("James 서버에 사용자 추가 테스트 - 실패")
    void createUserFail() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        ResponseEntity<String> failResponse = ResponseEntity.badRequest().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.PUT.getClass()), any(), eq(String.class))).willReturn(failResponse);

        // When
        boolean result = jamesWebAdmin.createUser(userid, password);

        // Then
        assert (!result);
    }


    @Test
    @DisplayName("James 서버에 사용자 존재 여부 확인 테스트 - 이미 존재하는 사용자")
    void testUserExist() {
        // Given
        String userid = "testuser";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.HEAD.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = jamesWebAdmin.testUserExist(userid);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 존재 여부 확인 테스트 - 존재하지 않는 사용자")
    void testUserExist2() {
        // Given
        String userid = "testuser";
        given(restTemplate.exchange(anyString(), any(HttpMethod.HEAD.getClass()), any(), eq(String.class))).willAnswer(
                invocation -> {
                    throw new Exception();
                }
        );

        // When
        boolean result = jamesWebAdmin.testUserExist(userid);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 비밀번호 변경 테스트 - 성공")
    void changePassword() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.PUT.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = jamesWebAdmin.changePassword(userid, password);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 비밀번호 변경 테스트 - 실패")
    void changePasswordFail() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        ResponseEntity<String> failResponse = ResponseEntity.badRequest().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.PUT.getClass()), any(), eq(String.class))).willReturn(failResponse);

        // When
        boolean result = jamesWebAdmin.changePassword(userid, password);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 삭제 테스트 - 성공")
    void deleteUser() {
        // Given
        String userid = "testuser";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.DELETE.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = jamesWebAdmin.deleteUser(userid);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 삭제 테스트 - 실패")
    void deleteUserFail() {
        // Given
        String userid = "testuser";
        ResponseEntity<String> failResponse = ResponseEntity.badRequest().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.DELETE.getClass()), any(), eq(String.class))).willReturn(failResponse);

        // When
        boolean result = jamesWebAdmin.deleteUser(userid);

        // Then
        assertFalse(result);
    }
}
