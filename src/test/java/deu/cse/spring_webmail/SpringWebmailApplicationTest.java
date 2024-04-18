package deu.cse.spring_webmail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class SpringWebmailApplicationTest {

    @Test
    @DisplayName("SpringWebmailApplication의 main 메소드 테스트")
    void main() {
        SpringWebmailApplication.main(new String[] {});
    }
}
