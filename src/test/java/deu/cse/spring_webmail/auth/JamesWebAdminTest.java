package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.exception.CustomException;
import deu.cse.spring_webmail.james.JamesUsers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JamesWebAdminTest {

    @InjectMocks
    static JamesUsers jamesWebAdmin;

    @Mock
    private RestTemplate restTemplate;

    @Value("${james.admin.host}")
    private static String jamesWebAdminUrl;

    @Value("${james.admin.port}")
    private static Integer jamesWebAdminPort;


    @Test
    @DisplayName("James 서버에 사용자 추가 테스트")
    void addUserSuccess() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String url = jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";

        // 모의 객체를 사용하여 응답 설정
        ResponseEntity<String> successResponse = new ResponseEntity<>("User added successfully", HttpStatus.NO_CONTENT);
        given(restTemplate.exchange(eq(String.format(url, userid)), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class))).willReturn(successResponse);
        // When
        boolean result = jamesWebAdmin.createUser(userid, password);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 추가 실패 테스트")
    void addUserFail() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String url = jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";

        // 모의 객체를 사용하여 응답 설정
        ResponseEntity<String> failResponse = new ResponseEntity<>("Failed to add user", HttpStatus.BAD_REQUEST);
        given(restTemplate.exchange(eq(String.format(url, userid)), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class))).willThrow(new CustomException("Failed to add user" + failResponse.getStatusCode()));
        // When
        assertThrows(CustomException.class, () -> jamesWebAdmin.createUser(userid, password));
    }

    @Test
    @DisplayName("James 서버에 사용자 비밀번호 변경 테스트")
    void changePasswordSuccess() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String url = jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";

        // 모의 객체를 사용하여 응답 설정
        ResponseEntity<String> successResponse = new ResponseEntity<>("Password changed successfully", HttpStatus.NO_CONTENT);
        given(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class))).willReturn(successResponse);
        // When
        boolean result = jamesWebAdmin.changePassword(userid, password);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 비밀번호 변경 실패 테스트")
    void changePasswordFail() {
        // Given
        String userid = "testuser";
        String password = "testpassword";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String url = jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";

        // 모의 객체를 사용하여 응답 설정
        ResponseEntity<String> failResponse = new ResponseEntity<>("Failed to change password", HttpStatus.BAD_REQUEST);
        given(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class))).willThrow(new CustomException("Failed to change password" + failResponse.getStatusCode()));
        // When
        assertThrows(CustomException.class, () -> jamesWebAdmin.changePassword(userid, password));
    }

    @Test
    @DisplayName("James 서버에 사용자 삭제 테스트")
    void deleteUserSuccess() {
        // Given
        String userid = "testuser";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String url = jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";

        // 모의 객체를 사용하여 응답 설정
        ResponseEntity<String> successResponse = new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);
        given(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), any(), eq(String.class))).willReturn(successResponse);
        // When
        boolean result = jamesWebAdmin.deleteUser(userid);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("James 서버에 사용자 삭제 실패 테스트")
    void deleteUserFail() {
        // Given
        String userid = "testuser";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String url = jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";

        // 모의 객체를 사용하여 응답 설정
        ResponseEntity<String> failResponse = new ResponseEntity<>("Failed to delete user", HttpStatus.BAD_REQUEST);
        given(restTemplate.exchange(eq(String.format(url, userid)), eq(HttpMethod.DELETE), any(), eq(String.class))).willThrow(new CustomException("Failed to delete user" + failResponse.getStatusCode()));
        // When
        assertThrows(CustomException.class, () -> jamesWebAdmin.deleteUser(userid));
    }
}
