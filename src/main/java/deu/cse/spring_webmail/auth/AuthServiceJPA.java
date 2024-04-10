package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.user.Role;
import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceJPA implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isAvailableUserId(String userid) {
        return userRepository.findById(userid).isEmpty();
    }

    @Override
    public boolean addUser(LoginForm loginForm) {

        if (!isAvailableUserId(loginForm.username())) {
            return false;
        }

        User user = new User();
        user.setUserName(loginForm.username());
        user.setPassword(passwordEncoder.encode(loginForm.password()));
        user.setRole(Role.USER);
        user.setPasswordHashAlgorithm("NONE");
        user.setVersion(1);
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
            userRepository.deleteById(userid);
            return true;
        }
        return false;
    }

    @Override
    public User changePassword(String userid, String oldPassword, String newPassword) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Password not matched");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public User changePassword(String userid, String newPassword) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
