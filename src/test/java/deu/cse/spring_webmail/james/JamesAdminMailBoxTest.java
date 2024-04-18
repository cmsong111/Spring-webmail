package deu.cse.spring_webmail.james;

import deu.cse.spring_webmail.james.dto.JamesMailBoxDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JamesAdminMailBoxTest {

    @InjectMocks
    static JamesAdminMailBox JamesAdminMailBox;

    @Mock
    RestTemplate restTemplate;

    @Test
    @DisplayName("메일박스 생성 테스트 - 성공")
    void createMailBox() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.PUT.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = JamesAdminMailBox.createMailBox(username, mailboxName);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("메일박스 생성 테스트 - 실패")
    void createMailBoxFail() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<String> failResponse = ResponseEntity.badRequest().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.PUT.getClass()), any(), eq(String.class))).willReturn(failResponse);

        // When
        boolean result = JamesAdminMailBox.createMailBox(username, mailboxName);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("메일박스 삭제 테스트 - 성공")
    void deleteMailBox() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.DELETE.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = JamesAdminMailBox.deleteMailBox(username, mailboxName);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("메일박스 삭제 테스트 - 실패")
    void deleteMailBoxFail() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<String> failResponse = ResponseEntity.badRequest().build();
        given(restTemplate.exchange(anyString(), any(HttpMethod.DELETE.getClass()), any(), eq(String.class))).willReturn(failResponse);

        // When
        boolean result = JamesAdminMailBox.deleteMailBox(username, mailboxName);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("메일박스 존재 여부 확인 테스트 - 성공")
    void testMailBoxExist() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<String> successResponse = ResponseEntity.ok().build();

        given(restTemplate.exchange(anyString(), any(HttpMethod.HEAD.getClass()), any(), eq(String.class))).willReturn(successResponse);

        // When
        boolean result = JamesAdminMailBox.testMailBoxExist(username, mailboxName);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("사용자 메일박스 리스트 조회 테스트 - 성공")
    void getMailBoxList() {
        // Given
        String username = "testuser";
        JamesMailBoxDto[] mailBoxList = new JamesMailBoxDto[1];
        JamesMailBoxDto jamesMailBoxDto = new JamesMailBoxDto();
        jamesMailBoxDto.setMailboxName("testmailbox");
        mailBoxList[0] = jamesMailBoxDto;
        ResponseEntity<JamesMailBoxDto[]> successResponse = ResponseEntity.ok(mailBoxList);

        given(restTemplate.getForEntity(anyString(), eq(JamesMailBoxDto[].class))).willReturn(successResponse);

        // When
        JamesMailBoxDto[] result = JamesAdminMailBox.getMailBoxList(username);

        // Then
        assertEquals(mailBoxList, result);
    }

    @Test
    @DisplayName("메일함에 포함된 메일 개수 조회 테스트 - 성공")
    void getMailCount() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        Integer mailCount = 1;
        ResponseEntity<Integer> successResponse = ResponseEntity.ok(mailCount);

        given(restTemplate.getForEntity(anyString(), eq(Integer.class))).willReturn(successResponse);

        // When
        Integer result = JamesAdminMailBox.getMailCount(username, mailboxName);

        // Then
        assertEquals(mailCount, result);
    }

    @Test
    @DisplayName("메일함에 포함된 메일 개수 조회 테스트 - 실패")
    void getMailCountFail() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<Integer> failResponse = ResponseEntity.badRequest().build();

        given(restTemplate.getForEntity(anyString(), eq(Integer.class))).willReturn(failResponse);

        // When
        Integer result = JamesAdminMailBox.getMailCount(username, mailboxName);

        // Then
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("메일함에 포함된 읽지 않은 메일 개수 조회 테스트 - 성공")
    void getUnseenMailCount() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        Integer unseenMailCount = 1;
        ResponseEntity<Integer> successResponse = ResponseEntity.ok(unseenMailCount);

        given(restTemplate.getForEntity(anyString(), eq(Integer.class))).willReturn(successResponse);

        // When
        Integer result = JamesAdminMailBox.getUnseenMailCount(username, mailboxName);

        // Then
        assertEquals(unseenMailCount, result);
    }

    @Test
    @DisplayName("메일함에 포함된 읽지 않은 메일 개수 조회 테스트 - 실패")
    void getUnseenMailCountFail() {
        // Given
        String mailboxName = "testmailbox";
        String username = "testuser";
        ResponseEntity<Integer> failResponse = ResponseEntity.badRequest().build();

        given(restTemplate.getForEntity(anyString(), eq(Integer.class))).willReturn(failResponse);

        // When
        Integer result = JamesAdminMailBox.getUnseenMailCount(username, mailboxName);

        // Then
        assertEquals(-1, result);
    }
}
