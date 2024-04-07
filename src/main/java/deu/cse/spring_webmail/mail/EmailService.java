package deu.cse.spring_webmail.mail;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    /**
     * 메일을 보내는 메소드
     *
     * @param from    보내는 사람 주소
     * @param to      받는 사람 주소
     * @param cc      참조 주소
     * @param subject 제목
     * @param body    내용
     * @return 메일 전송 성공 여부
     */
    public boolean sendMail(String from, String to, String cc, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            if (cc != null && !cc.isEmpty())
                mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error(e.toString());
            return false;
        }
    }

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
    public boolean sendEmail(String from, String to, String cc, String subject, String body, List<MultipartFile> multipartFiles) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            if (cc != null && !cc.isEmpty())
                mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setSentDate(new Date());

            // 본문과 첨부파일이 담길 MimeBodyPart 객체 생성
            Multipart multipart = new MimeMultipart();
            BodyPart messageContentBody = new MimeBodyPart();
            messageContentBody.setContent(body, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageContentBody);


            // 첨부파일
            for (MultipartFile multipartFile : multipartFiles) {
                BodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(multipartFile.getInputStream(), multipartFile.getContentType());

                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(multipartFile.getOriginalFilename());
                multipart.addBodyPart(messageBodyPart);
            }

            // 본문과 첨부파일을 MimeMessage에 설정
            message.setContent(multipart);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException | IOException e) {
            log.error(e.toString());
            return false;
        }
    }
}
