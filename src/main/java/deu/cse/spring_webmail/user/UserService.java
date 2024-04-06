package deu.cse.spring_webmail.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }
}
