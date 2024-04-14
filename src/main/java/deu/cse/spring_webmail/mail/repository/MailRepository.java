package deu.cse.spring_webmail.mail.repository;


import deu.cse.spring_webmail.mail.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메일 레파지토리 인터페이스
 */
@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    /**
     * 메일함 아이디로 메일을 찾아서 해당 메일함에 있는 모든 메일을 가져옴(휴지통, 스팸 포함)
     *
     * @param mailboxId 메일함 아이디
     * @return 메일함에 있는 모든 메일
     */
    List<Mail> findAllByMailbox_MailboxId(Long mailboxId);

    /**
     * 메일함 아이디로 메일을 찾아서 해당 메일함에 있는 모든 메일의 개수를 가져옴
     *
     * @param mailboxId 메일함 아이디
     * @param isDeleted 삭제된 메일 포함 여부
     * @return 메일함에 있는 모든 메일
     */
    Long countByMailbox_MailboxIdAndMailIsDeleted(Long mailboxId, boolean isDeleted);

    /**
     * 메일함 아이디로 읽지 않은, 삭제되지 않은 메일의 개수를 가져옴
     *
     * @param mailboxId 메일함 아이디
     * @param isSeen    읽음 여부
     * @param isDeleted 삭제 여부
     */
    Long countByMailbox_MailboxIdAndMailIsSeenAndMailIsDeleted(Long mailboxId, boolean isSeen, boolean isDeleted);
}
