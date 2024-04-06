package deu.cse.spring_webmail.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        log.info("loadUserByUsername: {}", username);
        return userRepository.findById(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
    }
}
