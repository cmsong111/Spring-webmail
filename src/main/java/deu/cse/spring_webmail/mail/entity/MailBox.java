package deu.cse.spring_webmail.mail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "JAMES_MAILBOX")
public class MailBox {
    @Id
    @Column(name = "MAILBOX_ID")
    private Long mailboxId;

    @Column(name = "MAILBOX_HIGHEST_MODSEQ")
    private Long mailboxHighestModseq;

    @Column(name = "MAILBOX_LAST_UID")
    private Long mailboxLastUid;

    @Column(name = "MAILBOX_NAME", nullable = false)
    private String mailboxName;

    @Column(name = "MAILBOX_NAMESPACE", nullable = false)
    private String mailboxNamespace;

    @Column(name = "MAILBOX_UID_VALIDITY", nullable = false)
    private Long mailboxUidValidity;

    @Column(name = "USER_NAME")
    private String userName;
}
