package deu.cse.spring_webmail.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 메일 설정을 위한 클래스
 */
@Configuration
public class MailConfig {

    /**
     * JavaMailSender 빈을 생성합니다.
     *
     * @return JavaMailSender 빈
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(587);
        mailSender.setJavaMailProperties(getProperties());
        return mailSender;
    }

    /**
     * 메일 속성을 설정합니다.
     *
     * @return 메일 속성
     */
    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.smtp.host", "localhost");
        return properties;
    }
}
