package deu.cse.spring_webmail.mail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 * 메일 엔티티
 */
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

    /**
     * 메일 키
     */
    @EmbeddedId
    private MailKey mailKey;

    /**
     * 메일함
     */
    @JoinColumn(name = "MAILBOX_ID", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MailBox mailbox;

    /**
     * 메일 제목
     */
    @Column(name = "MAIL_IS_ANSWERED")
    private Boolean mailIsAnswered;

    @Column(name = "MAIL_BODY_START_OCTET")
    private Integer mailBodyStartOctet;

    @Column(name = "MAIL_CONTENT_OCTETS_COUNT")
    private Long mailContentOctetsCount;

    /**
     * 메일 삭제 여부
     * <p>Soft Delete 기법을 사용하고 있기 때문에, 해당 속성이 true가 된다면 JPA 쿼리 시 보이지 않음</p>
     */
    @Column(name = "MAIL_IS_DELETED")
    private Boolean mailIsDeleted;

    @Column(name = "MAIL_IS_DRAFT")
    private Boolean mailIsDraft;

    /**
     * 메일 중요 여부
     */
    @Column(name = "MAIL_IS_FLAGGED")
    private Boolean mailIsFlagged;

    /**
     * 메일 수발신 시간
     */
    @Column(name = "MAIL_DATE")
    private Timestamp mailDate;

    /**
     * 메일 유형
     * <p>메일의 MIME 타입을 나타냅니다.</p>
     * <p>Plain Text, HTML, Multipart 등이 있습니다.</p>
     */
    @Column(name = "MAIL_MIME_TYPE")
    private String mailMimeType;

    @Column(name = "MAIL_MODSEQ")
    private Long mailModseq;

    @Column(name = "MAIL_IS_RECENT")
    private Boolean mailIsRecent;

    /**
     * 메일 읽음 여부 (Seen)
     */
    @Column(name = "MAIL_IS_SEEN")
    private Boolean mailIsSeen;

    @Column(name = "MAIL_MIME_SUBTYPE")
    private String mailMimeSubtype;

    @Column(name = "MAIL_TEXTUAL_LINE_COUNT")
    private Long mailTextualLineCount;

    /**
     * 메일 바디 (내용)
     * <p>메일의 내용을 저장합니다.</p>
     * <p>첨부파일도 함께 저장됩니다.</p>
     */
    @Lob
    @Column(name = "MAIL_BYTES", columnDefinition = "LONGBLOB")
    private Blob mailBytes;

    /**
     * 메일 헤더
     * <p>메일의 헤더를 저장합니다.</p>
     */
    @Lob
    @Column(name = "HEADER_BYTES", columnDefinition = "MEDIUMBLOB")
    private Blob headerBytes;

    /**
     * 메일 키 클래스 (복합키)
     * <p>메일의 복합키를 정의합니다.</p>
     * <p>메일함 ID와 메일 UID로 구성됩니다.</p>
     * <p>메일함 ID는 외래키로 사용되므로, 메일함 엔티티와 연관관계를 맺습니다.</p>
     * <p>메일 UID는 메일함의 메일을 구분하는 식별자입니다.</p>
     */
    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailKey implements Serializable {
        /**
         * 메일함 ID
         */
        @Column(name = "MAILBOX_ID")
        private Long mailboxMailboxId;

        /**
         * 메일 UID
         */
        @Column(name = "MAIL_UID")
        private Long mailUid;
    }
}
