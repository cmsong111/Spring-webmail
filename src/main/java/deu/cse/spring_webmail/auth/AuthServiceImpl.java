package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.james.JamesAdminMailBox;
import deu.cse.spring_webmail.james.JamesAdminUser;
import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.user.Role;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * James Web Admin 이용한 사용자 인증 서비스
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JamesAdminUser jamesWebAdmin;
    private final JamesAdminMailBox jamesAdminMailBox;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailableUserId(String userid) {
        return !jamesWebAdmin.testUserExist(userid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addUser(LoginForm loginForm) {
        // If the user already exists, return false
        if (jamesWebAdmin.testUserExist(loginForm.username())) {
            return false;
        }

        // Password encoding and user creation
        String encodedPassword = passwordEncoder.encode(loginForm.password());
        jamesWebAdmin.createUser(loginForm.username(), encodedPassword);

        // Create a mailbox for the user
        for (MailBoxType mailboxType : MailBoxType.values()) {
            jamesAdminMailBox.createMailBox(loginForm.username(), mailboxType.getMailBoxName());
        }

        // 사용자 역할 설정
        User user = userRepository.findById(loginForm.username()).orElseThrow();
        user.getRoles().add(Role.USER);
        userRepository.save(user);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authenticate(LoginForm loginForm) {
        User user = userRepository.findById(loginForm.username()).orElse(null);
        return user != null && passwordEncoder.matches(loginForm.password(), user.getPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUser(String userid) {
        if (userRepository.findById(userid).isPresent()) {
            jamesWebAdmin.deleteUser(userid);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User changePassword(String userid, String newPassword) {
        User user = userRepository.findById(userid).orElse(null);
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            if (jamesWebAdmin.changePassword(userid, encodedPassword)) {
                user.setPassword(encodedPassword);
                return user;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User changePassword(String userid, String oldPassword, String newPassword) {
        User user = userRepository.findById(userid).orElse(null);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            jamesWebAdmin.changePassword(userid, encodedPassword);
            return userRepository.findById(userid).orElse(null);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
