package deu.cse.spring_webmail.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailBoxRepository extends JpaRepository<MailBox, Long> {

    Optional<MailBox> findByUserName(String userName);
}