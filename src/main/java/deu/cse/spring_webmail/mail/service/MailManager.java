package deu.cse.spring_webmail.mail.service;

import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.mail.entity.Mail;
import deu.cse.spring_webmail.mail.entity.MailBox;
import deu.cse.spring_webmail.mail.repository.MailBoxRepository;
import deu.cse.spring_webmail.mail.repository.MailRepository;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import jakarta.mail.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.angus.mail.imap.IMAPFolder;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailManager {

    private final UserRepository userRepository;
    private final MailRepository mailRepository;
    private final MailBoxRepository mailBoxRepository;

    /**
     * 메일 보관함을 이동하는 메소드
     * (예: 스팸메일함 -> 받은메일함)
     *
     * @param userName    사용자 아이디
     * @param mailId      메일 아이디
     * @param toMailBox   이동할 메일함
     * @param fromMailBox 이전 메일함
     */
    public void moveMail(String userName, Long mailId, MailBoxType toMailBox, MailBoxType fromMailBox) throws MessagingException, GeneralSecurityException {
        // 사용자 정보 조회
        User user = userRepository.findById(userName).orElseThrow();

        // 메일함 정보 조회
        Properties props = new Properties();
        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        // IMAP 프로토콜을 사용하여 메일함 오픈(143: IMAP 포트)
        Store store = session.getStore("imap");
        store.connect("localhost", 143, user.getUserName(), user.getPassword());

        // 메일함 오픈
        IMAPFolder fromFolder = (IMAPFolder) store.getFolder(fromMailBox.getMailBoxName());
        IMAPFolder toFolder = (IMAPFolder) store.getFolder(toMailBox.getMailBoxName());
        fromFolder.open(Folder.READ_WRITE);
        toFolder.open(Folder.READ_WRITE);


        // 메일 이동
        fromFolder.moveMessages(new Message[]{fromFolder.getMessageByUID(mailId)}, toFolder);

        // 메일함 닫기
        fromFolder.close(true);
        toFolder.close(true);
        store.close();
    }

    /**
     * 메일을 삭제하는 메소드 (휴지통에서 삭제 시 해당 메소드 호출)
     *
     * @param userName  사용자 아이디
     * @param toMailBox 삭제할 메일함
     * @param mailId    메일 아이디
     */
    public void deleteMail(String userName, MailBoxType toMailBox, Long mailId) {
        MailBox mailBox = mailBoxRepository.findByUserNameAndMailboxName(userName, toMailBox.getMailBoxName()).orElseThrow();
        Mail.MailKey mailKey = new Mail.MailKey(mailId, mailBox.getMailboxId());

        Mail mail = mailRepository.findById(mailKey).orElseThrow();
        mailRepository.delete(mail);
    }
}
