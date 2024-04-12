package deu.cse.spring_webmail.mail.dto;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    private Long mailboxMailboxId;
    private Long mailUid;
    private Boolean mailIsAnswered;
    private Integer mailBodyStartOctet;
    private Long mailContentOctetsCount;
    private Boolean mailIsDeleted;
    private Boolean mailIsDrat;
    private Boolean mailIslagged;
    private Timestamp mailDate;
    private String mailMimeType;
    private Long mailModseq;
    private Boolean mailIsRecent;
    private Boolean mailIsSeen;
    private String mailMimeSubtype;
    private Long mailTextualLineCount;
    private MimeMessage mimeMessage;
    private String mailContent;
    private List<String> attachments;

}
