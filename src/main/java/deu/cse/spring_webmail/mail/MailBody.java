package deu.cse.spring_webmail.mail;

import jakarta.mail.Message;

import java.sql.Timestamp;
import java.util.List;

public record MailBody(
        Long mailboxId,
        Long mailUid,
        String subject,
        String mailFrom,
        Timestamp mailDate,
        String body,
        List<String> attachments
) {
}
