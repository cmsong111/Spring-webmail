package deu.cse.spring_webmail.mail.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;


@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "JAMES_MAIL")
@DynamicUpdate
@SQLDelete(sql = "UPDATE JAMES_MAIL SET MAIL_IS_DELETED = 1 WHERE MAILBOX_ID = ? AND MAIL_UID = ?")
@SQLRestriction("MAIL_IS_DELETED = 0")
public class Mail {

    @EmbeddedId
    private MailKey mailKey;

    @JoinColumn(name = "MAILBOX_ID", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MailBox mailbox;


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

    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailKey implements Serializable {
        @Column(name = "MAILBOX_ID")
        private Long mailboxMailboxId;

        @Column(name = "MAIL_UID")
        private Long mailUid;
    }
}
