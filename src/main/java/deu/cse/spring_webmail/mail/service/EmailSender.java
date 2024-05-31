package deu.cse.spring_webmail.mail.service;

import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 메일 전송 서비스로직을 포함하는 클래스
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    /**
     * 메일을 보내는 메소드 (첨부파일 포함)
     *
     * @param from           보내는 사람 주소
     * @param to             받는 사람 주소
     * @param cc             참조 주소
     * @param subject        제목
     * @param body           내용
     * @param multipartFiles 첨부파일
     * @return 메일 전송 성공 여부
     */
    public String sendEmail(String from, String to, String cc, String subject, String body, List<MultipartFile> multipartFiles) {
        try {
            MimeMessage message = createMimeMessage(from, to, cc, subject, body, multipartFiles);
            javaMailSender.send(message);
            return "메일 전송 성공";
        } catch (MessagingException | IOException e) {
            log.error(e.toString());
            return "메일 전송 실패";
        }
    }

    /**
     * 임시 보관함에 메일을 보내는 메소드(자기 자신에게 보내는 메일)
     * IMAP 서비스 활용
     *
     * @param from           보내는 사람 주소
     *                       (사용자 이름)
     * @param to             받는 사람 주소
     * @param cc             참조 주소
     * @param subject        제목
     * @param body           내용
     * @param multipartFiles 첨부파일
     * @return 메일 전송 성공 여부 메시지
     */
    public String saveTempMail(String from, String to, String cc, String subject, String body, List<MultipartFile> multipartFiles) {
        try {
            // 사용자 정보 조회
            User user = userRepository.findById(from).orElseThrow();

            // 메일함 정보 조회
            Session session = Session.getInstance(new Properties(), null);
            session.setDebug(true);

            // IMAP 프로토콜을 사용하여 메일함 오픈(143: IMAP 포트)
            Store store = session.getStore("imap");
            store.connect("localhost", 143, user.getUserName(), user.getPassword());

            // 메일함 오픈
            Folder folder = store.getFolder(MailBoxType.DRAFT.getMailBoxName());

            // 메일 작성
            MimeMessage message = createMimeMessage(from, to, cc, subject, body, multipartFiles);

            // 임시 보관함에 메일 추가
            folder.open(Folder.READ_WRITE);
            folder.appendMessages(new Message[]{message});
            folder.close(true);
            store.close();
            return "임시 보관함에 메일 저장 성공";
        } catch (MessagingException | IOException e) {
            log.error(e.toString());
            return "임시 보관함에 메일 저장 실패";
        }
    }

    /**
     * MimeMessage 객체를 생성하는 메소드
     *
     * @param username       사용자 이름
     * @param to             받는 사람
     * @param cc             참조
     * @param subject        제목
     * @param body           본문
     * @param multipartFiles 첨부파일
     * @return MimeMessage 객체
     * @throws MessagingException MimeMessage 객체 생성 실패
     * @throws IOException        첨부파일 처리 실패
     */
    protected MimeMessage createMimeMessage(String username, String to, String cc, String subject, String body, List<MultipartFile> multipartFiles) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, multipartFiles.isEmpty(), "UTF-8");
        // 보내는 사람 설정
        String from = username + "@localhost";
        mimeMessageHelper.setFrom(from);

        // 받는 사람 설정
        String[] toList = to.split(",");
        mimeMessageHelper.setTo(toList);

        // 참조 설정
        if (cc != null && !cc.isEmpty()) {
            String[] ccList = cc.split(",");
            mimeMessageHelper.setCc(ccList);
        }

        // 제목 설정
        mimeMessageHelper.setSubject(subject);

        // 보낸 날짜 설정
        mimeMessageHelper.setSentDate(new Date());

        // 본문 설정
        if (multipartFiles.isEmpty()) {
            // 본문과 첨부파일이 없는 경우
            message.setText(body);
        } else {
            // 본문과 첨부파일이 있는 경우
            Multipart multipart = new MimeMultipart();
            BodyPart messageContentBody = new MimeBodyPart();
            messageContentBody.setContent(body, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageContentBody);

            // 첨부파일 추가
            for (MultipartFile multipartFile : multipartFiles) {
                BodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(multipartFile.getInputStream(), multipartFile.getContentType());

                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(multipartFile.getOriginalFilename());
                multipart.addBodyPart(messageBodyPart);
            }

            // 본문과 첨부파일을 MimeMessage에 설정
            message.setContent(multipart);
        }

        return message;
    }
}
