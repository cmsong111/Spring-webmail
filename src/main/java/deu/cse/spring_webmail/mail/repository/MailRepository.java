package deu.cse.spring_webmail.mail.repository;


import deu.cse.spring_webmail.mail.entity.Mail;
import deu.cse.spring_webmail.mail.entity.MailBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메일 레파지토리 인터페이스
 */
@Repository
public interface MailRepository extends JpaRepository<Mail, Mail.MailKey> {

    /**
     * 메일함 아이디로 메일을 찾아서 해당 메일함에 있는 모든 메일의 개수를 가져옴
     *
     * @param mailbox 메일함
     * @return 메일함에 있는 모든 메일
     */
    long countByMailbox(MailBox mailbox);


    /**
     * 메일함에서 읽음 여부에 따라 메일의 개수를 가져옴
     *
     * @param mailbox    메일함
     * @param mailIsSeen 메일 읽음 여부
     */
    long countByMailboxAndMailIsSeen(MailBox mailbox, Boolean mailIsSeen);


    /**
     * 진짜 삭제된 메일을 찾아서 해당 메일함에 있는 모든 메일을 가져옴
     *
     * @param mailboxId 메일함 아이디
     * @return 메일함에 있는 모든 메일
     */
    @Query(value = "select * from JAMES_MAIL where MAILBOX_ID = ?1 and MAIL_IS_DELETED = true", nativeQuery = true)
    List<Mail> findDeletedMailByMailBoxId(Long mailboxId);


}
