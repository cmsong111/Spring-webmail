package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User addUser(String userid, String password) {
        // 이미 사용중인 아이디인 경우 null 반환
        if (!isAvailableUserId(userid)) {
            return null;
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        jamesWebAdmin.addUser(userid, encodedPassword);

        // 사용자 추가 성공시 사용자 정보 반환
        return userRepository.findById(userid).orElse(null);
    }

    @Override
    public User authenticate(String userid, String password) {
        User user = userRepository.findById(userid).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
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
