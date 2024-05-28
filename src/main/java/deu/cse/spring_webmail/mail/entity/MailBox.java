package deu.cse.spring_webmail.mail.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 메일함 엔티티
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "JAMES_MAILBOX")
public class MailBox {
    /**
     * 메일함 ID
     */
    @Id
    @Column(name = "MAILBOX_ID", nullable = false)
    private Long mailboxId;

    @Column(name = "MAILBOX_HIGHEST_MODSEQ", nullable = false)
    private Long mailboxHighestModseq;

    @Column(name = "MAILBOX_LAST_UID")
    private Long mailboxLastUid;

    /**
     * 메일함 이름
     */
    @Column(name = "MAILBOX_NAME", nullable = false)
    private String mailboxName;

    @Column(name = "MAILBOX_NAMESPACE", nullable = false)
    private String mailboxNamespace;

    @Column(name = "MAILBOX_UID_VALIDITY", nullable = false)
    private Long mailboxUidValidity;

    /**
     * 소유자 이름
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 메일 목록(1:N)
     */
    @OneToMany(mappedBy = "mailbox", fetch = FetchType.LAZY)
    private List<Mail> mails;
}
