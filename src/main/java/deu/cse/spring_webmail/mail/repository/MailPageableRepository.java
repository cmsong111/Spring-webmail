package deu.cse.spring_webmail.mail.repository;


import deu.cse.spring_webmail.mail.entity.Mail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메일 레파지토리 Pageable 인터페이스
 */
@Repository
public interface MailPageableRepository extends PagingAndSortingRepository<Mail, Mail.MailKey> {

    /**
     * 메일함 아이디로 메일을 찾아서 해당 메일함에 있는 모든 메일을 가져옴(휴지통, 스팸 포함)
     *
     * @param mailboxId 메일함 아이디
     * @return 메일함에 있는 모든 메일
     */
    List<Mail> findAllByMailbox_MailboxId(Long mailboxId, Pageable pageable);

    /**
     * 메일함 아이디로 메일을 찾아서 해당 메일함에 있는 모든 메일을 가져옴(휴지통 제외)
     *
     * @param mailboxId 메일함 아이디
     * @return 메일함에 있는 모든 메일
     */
    List<Mail> findAllByMailbox_MailboxIdAndMailIsDeleted(Long mailboxId, boolean isDeleted, Pageable pageable);

    /**
     * 메일함 아이디로 읽지 않은, 삭제되지 않은 메일을 가져옴
     *
     * @param mailboxId 메일함 아이디
     * @param isSeen    읽음 여부
     * @param isDeleted 삭제 여부
     */
    List<Mail> findAllByMailbox_MailboxIdAndMailIsSeenAndMailIsDeleted(Long mailboxId, boolean isSeen, boolean isDeleted, Pageable pageable);
}
