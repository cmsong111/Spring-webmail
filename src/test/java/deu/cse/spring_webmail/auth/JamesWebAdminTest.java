package deu.cse.spring_webmail.auth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JamesWebAdminTest {

    @Autowired
    JamesWebAdmin jamesWebAdmin;

    @Test
    @DisplayName("James 서버에 사용자 추가 테스트")
    @Disabled("This test is disabled because it requires a running James server")
    void addUser() {
        String userid = "test";
        String password = "test";
        boolean result = jamesWebAdmin.addUser(userid, password);
        assertTrue(result);
    }
}
