package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.exception.CustomException;
import deu.cse.spring_webmail.james.JamesUsers;
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
 *
 * @deprecated Web Admin 사용을 중단하고 JPA를 이용한 사용자 인증 서비스를 사용합니다.
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceWebAdmin implements AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JamesUsers jamesWebAdmin;


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
        jamesWebAdmin.createUser(loginForm.username(), encodedPassword);

        // 사용자 역할 설정
        User user = userRepository.findById(loginForm.username()).orElseThrow(
                () -> new CustomException("해당 사용자를 찾을 수 없습니다.")
        );
        user.setRoles(List.of(Role.USER));
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
