package deu.cse.spring_webmail.mail;

import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public boolean sendMail(String to, String cc, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setFrom(to);
            mimeMessageHelper.setTo(to);
            if (cc != null && !cc.isEmpty())
                mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error("sendMail() error: {}", e);
            return false;
        }
    }


    public boolean sendEmail(String to, String cc, String subject, String body, List<MultipartFile> multipartFiles) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

            mimeMessageHelper.setFrom(to);
            mimeMessageHelper.setTo(to);
            if (cc != null && !cc.isEmpty())
                mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            MimeMultipart mimeMultipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html; charset=utf-8");
            mimeMultipart.addBodyPart(bodyPart);

            // 첨부파일
            for (MultipartFile multipart : multipartFiles) {
                if (multipart != null && !multipart.isEmpty()) {
                    mimeMessageHelper.addAttachment(multipart.getOriginalFilename(), multipart);
                }
            }
            message.setContent(mimeMultipart);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error("sendEmail() error: {}", e);
            return false;
        }
    }
}
