package deu.cse.spring_webmail.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Spring Security에서 사용할 UserDetailsService 구현체
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * 사용자 정보를 관리하는 Repository
     */
    UserRepository userRepository;

    /**
     * 사용자 이름을 기반으로 사용자 정보를 조회합니다.
     *
     * @param username 사용자 이름
     * @return 사용자 정보 (User Details)
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
