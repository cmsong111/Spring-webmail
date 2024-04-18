package deu.cse.spring_webmail.mail.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailBoxTypeTest {

    @Test
    @DisplayName("MailBoxType의 fromValue 메소드 테스트 - 1 입력")
    void fromValue() {
        MailBoxType mailBoxType = MailBoxType.fromValue(1);
        assertEquals(MailBoxType.INBOX, mailBoxType);
    }

    @Test
    @DisplayName("MailBoxType의 fromValue 메소드 테스트 - 2 입력")
    void fromValue2() {
        MailBoxType mailBoxType = MailBoxType.fromValue(2);
        assertEquals(MailBoxType.SENT, mailBoxType);
    }

    @Test
    @DisplayName("MailBoxType의 fromValue 메소드 테스트 - 3 입력")
    void fromValue3() {
        MailBoxType mailBoxType = MailBoxType.fromValue(3);
        assertEquals(MailBoxType.DRAFT, mailBoxType);
    }

    @Test
    @DisplayName("MailBoxType의 fromValue 메소드 테스트 - 4 입력")
    void fromValue4() {
        MailBoxType mailBoxType = MailBoxType.fromValue(4);
        assertEquals(MailBoxType.TRASH, mailBoxType);
    }

    @Test
    @DisplayName("MailBoxType의 fromValue 메소드 테스트 - 그 외 입력")
    void fromValue5() {
        MailBoxType mailBoxType = MailBoxType.fromValue(10);
        assertEquals(MailBoxType.INBOX, mailBoxType);
    }
}
