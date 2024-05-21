package deu.cse.spring_webmail.admin;

import deu.cse.spring_webmail.james.JamesAdminMailBox;
import deu.cse.spring_webmail.james.JamesAdminUser;
import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.user.Role;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSetting {

    private final JamesAdminMailBox jamesAdminMailBox;
    private final JamesAdminUser jamesAdminUser;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 프로그램 시작시 admin 계정 생성 및 관련 메일박스 생성
     * admin 계정이 존재하지 않을 경우 실행<br>
     * Password의 경우 data.sql에 암호화 된 비밀번호로 업데이트 되기 때문에 임의의 비밀번호로 생성
     */
    @PostConstruct
    public void init() {
        if (jamesAdminUser.testUserExist("admin")) {
            log.info("admin user already exists");
        } else {
            log.info("admin user does not exist");
            // For James
            jamesAdminUser.createUser("admin", "admin");
            jamesAdminMailBox.createMailBox("admin", MailBoxType.INBOX.getMailBoxName());
            jamesAdminMailBox.createMailBox("admin", MailBoxType.SENT.getMailBoxName());
            jamesAdminMailBox.createMailBox("admin", MailBoxType.TRASH.getMailBoxName());
            jamesAdminMailBox.createMailBox("admin", MailBoxType.DRAFT.getMailBoxName());

            // For Spring Database
            User user = userRepository.findByUserName("admin").get();
            user.setPassword(passwordEncoder.encode("admin"));
            List<Role> roles = user.getRoles();
            roles.add(Role.ADMIN);
            roles.add(Role.USER);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
