package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.user.Role;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JamesWebAdmin jamesWebAdmin;


    @Override
    public boolean isAvailableUserId(String userid) {
        return userRepository.findById(userid).isEmpty();
    }

    @Override
    public boolean addUser(LoginForm loginForm) {
        // 이미 사용중인 아이디인 경우 null 반환
        if (!isAvailableUserId(loginForm.username())) {
            return false;
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(loginForm.password());
        jamesWebAdmin.addUser(loginForm.username(), encodedPassword);

        // 사용자 역할 설정
        User user = userRepository.findById(loginForm.username()).orElse(null);
        user.setRole(Role.USER);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean authenticate(LoginForm loginForm) {
        User user = userRepository.findById(loginForm.username()).orElse(null);
        return user != null && passwordEncoder.matches(loginForm.password(), user.getPassword());
    }

    @Override
    public boolean deleteUser(String userid) {
        if (userRepository.findById(userid).isPresent()) {
            jamesWebAdmin.deleteUser(userid);
            return true;
        }
        return false;
    }

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

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
