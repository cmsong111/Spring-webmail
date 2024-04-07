package deu.cse.spring_webmail.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final MailMapper mailMapper;

    MailRepository mailRepository;
    MailBoxRepository mailBoxRepository;

    public List<MailHeader> getMailsByUserName(String userName) {
        MailBox mailBox = getMailBoxByUserName(userName);
        List<MailHeader> mailHeaders = new ArrayList<>();

        if (mailBox == null) {
            return mailHeaders;
        }

        for (Mail mail : getMailsByMailBoxId(mailBox.getMailboxId())) {
            MailHeader mailDto = mailMapper.toMailHeader(mail);
            mailHeaders.add(mailDto);
        }
        return mailHeaders;
    }

    public MailBody getMail(Long id) {
        Mail mail = mailRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Mail not found for id: " + id)
        );


        mail.setMailIsSeen(true);
        mailRepository.save(mail);

        List<String> attachments = new ArrayList<>();
        String body = "";

        try {
            InputStream mailHeaderStream = mail.getHeaderBytes().getBinaryStream();
            InputStream mailBodyStream = mail.getMailBytes().getBinaryStream();
            InputStream mailStream = new InputStream() {
                @Override
                public int read() throws IOException {
                    if (mailHeaderStream.available() > 0) {
                        return mailHeaderStream.read();
                    } else {
                        return mailBodyStream.read();
                    }
                }
            };

            Session session = Session.getInstance(new Properties());

            MimeMessage mimeMessage = new MimeMessage(session, mailStream);


            if (mimeMessage.getContent() instanceof MimeMultipart multipart) {
                log.info("Multipart: {}", multipart.getCount());
                for (int i = 0; i < multipart.getCount(); i++) {
                    MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                    log.info("BodyPart: {}", bodyPart.getContent());
                    log.info("BodyPart Type: {}", bodyPart.getContentType());

                    if (bodyPart.getContentType().startsWith("text")) {
                        body = bodyPart.getContent().toString();
                    } else {
                        attachments.add(bodyPart.getFileName());
                    }
                }
            } else {
                body = mimeMessage.getContent().toString();
            }
            // 아래 String[] 모두 합치기
            //mimeMessage.getFrom()

            StringBuffer from = new StringBuffer();
            for (int i = 0; i < mimeMessage.getFrom().length; i++) {
                from.append(mimeMessage.getFrom()[i].toString());
                if (i < mimeMessage.getFrom().length - 1) {
                    from.append(", ");
                }
            }
            return new MailBody(mail.getMailbox().getMailboxId(), mail.getMailUid(), from.toString(), mimeMessage.getSubject(), mail.getMailDate(), body, attachments);


        } catch (SQLException | MessagingException | IOException e) {
            log.error(e.toString());
        }
        return null;
    }

    public MailBox getMailBoxByUserName(String userName) {
        return mailBoxRepository.findByUserName(userName).orElse(null);
    }

    List<Mail> getMailsByMailBoxId(Long mailBoxId) {
        return mailRepository.findAllByMailbox_MailboxId(mailBoxId);
    }
}
