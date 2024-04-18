package deu.cse.spring_webmail.mail.repository;


import deu.cse.spring_webmail.mail.entity.Mail;
import deu.cse.spring_webmail.mail.entity.MailBox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메일 레파지토리 Pageable 인터페이스
 */
@Repository
public interface MailPageableRepository extends PagingAndSortingRepository<Mail, Mail.MailKey> {

    List<Mail> findAllByMailbox(MailBox mailbox, Pageable pageable);
}
