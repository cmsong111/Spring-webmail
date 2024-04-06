package deu.cse.spring_webmail.mail;

import jakarta.mail.internet.MimeMessage;

import java.sql.Timestamp;

public record MailDto(MailBox mailbox,
                      Long mailUid,
                      Boolean mailIsAnswered,
                      Integer mailBodyStartOctet,
                      Long mailContentOctetsCount,
                      Boolean mailIsDeleted,
                      Boolean mailIsDraft,
                      Boolean mailIsFlagged,
                      Timestamp mailDate,
                      String mailMimeType,
                      Long mailModseq,
                      Boolean mailIsRecent,
                      Boolean mailIsSeen,
                      String mailMimeSubtype,
                      Long mailTextualLineCount,
                      String mimeMessage,
                      MimeMessage headerBytes
) {
}
