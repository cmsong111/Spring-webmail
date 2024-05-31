package deu.cse.spring_webmail.mail.repository;

import deu.cse.spring_webmail.mail.entity.MailBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 메일함 관련 Repository
 */
@Repository
public interface MailBoxRepository extends JpaRepository<MailBox, Long> {

    /**
     * 사용자 이름으로 메일함 조회
     *
     * @param userName 사용자 이름
     * @return 사용자 이름에 해당하는 메일함 리스트
     */
    List<MailBox> findByUserName(String userName);

    /**
     * 사용자 이름과 메일함 이름으로 메일함 조회
     *
     * @param userName    사용자 이름
     * @param mailboxName 메일함 이름
     *                    (메일함 이름은 INBOX, Outbox, Sent ,Draft ,Trash 중 하나)
     * @return
     */
    Optional<MailBox> findByUserNameAndMailboxName(String userName, String mailboxName);
}
