package deu.cse.spring_webmail.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;

import java.sql.Timestamp;

/**
 * @param mailboxId    메일 박스 번호
 * @param mailUid      메일 고유키
 * @param mailIsSeen   메일 읽음여부
 * @param mailDate     메일 수신 일자
 * @param mailMimeType 메일 유형(text = 평문, multipart = 첨부파일)
 * @param message      메시지 헤더 객체
 */
public record MailHeader(
        Long mailboxId,
        Long mailUid,
        boolean mailIsSeen,
        Timestamp mailDate,
        String mailMimeType,
        MimeMessage message
) {
}
