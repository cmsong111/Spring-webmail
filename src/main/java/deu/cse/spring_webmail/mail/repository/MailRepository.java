package deu.cse.spring_webmail.mail.repository;


import deu.cse.spring_webmail.mail.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findAllByMailbox_MailboxId(Long mailboxId);
}
