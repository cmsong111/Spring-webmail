package deu.cse.spring_webmail.mail;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "JAMES_MAIL")
public class Mail {
    @JoinColumn(name = "MAILBOX_ID")
    @ManyToOne
    private MailBox mailbox;
    @Id
    @Column(name = "MAIL_UID")
    private Long mailUid;

    @Column(name = "MAIL_IS_ANSWERED")
    private Boolean mailIsAnswered;

    @Column(name = "MAIL_BODY_START_OCTET")
    private Integer mailBodyStartOctet;

    @Column(name = "MAIL_CONTENT_OCTETS_COUNT")
    private Long mailContentOctetsCount;

    @Column(name = "MAIL_IS_DELETED")
    private Boolean mailIsDeleted;

    @Column(name = "MAIL_IS_DRAFT")
    private Boolean mailIsDraft;

    @Column(name = "MAIL_IS_FLAGGED")
    private Boolean mailIsFlagged;

    @Column(name = "MAIL_DATE")
    private Timestamp mailDate;

    @Column(name = "MAIL_MIME_TYPE")
    private String mailMimeType;

    @Column(name = "MAIL_MODSEQ")
    private Long mailModseq;

    @Column(name = "MAIL_IS_RECENT")
    private Boolean mailIsRecent;

    @Column(name = "MAIL_IS_SEEN")
    private Boolean mailIsSeen;

    @Column(name = "MAIL_MIME_SUBTYPE")
    private String mailMimeSubtype;

    @Column(name = "MAIL_TEXTUAL_LINE_COUNT")
    private Long mailTextualLineCount;

    @Lob
    @Column(name = "MAIL_BYTES", columnDefinition = "LONGBLOB")
    private Blob mailBytes;

    @Lob
    @Column(name = "HEADER_BYTES", columnDefinition = "MEDIUMBLOB")
    private Blob headerBytes;
}
