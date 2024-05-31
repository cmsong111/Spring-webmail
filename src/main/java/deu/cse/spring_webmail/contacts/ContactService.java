package deu.cse.spring_webmail.contacts;

import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 연락처 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    /**
     * 친구 추가 메소드
     *
     * @param username 사용자 아이디
     * @param friendEmail  친구 아이디
     */
    public String addContact(String username, String friendEmail, String nickname) {
        log.info("addContact");
        log.info("username: " + username);
        log.info("friendEmail: " + friendEmail);
        log.info("nickname: " + nickname);
        User user = userRepository.findById(username).get();
        Optional<User> friend = userRepository.findById(friendEmail);

        if (user.getUserName().equals(friendEmail)) {
            return "자기 자신을 친구로 추가할 수 없습니다.";
        }

        if (friend.isEmpty()) {
            return "존재하지 않는 사용자입니다.";
        }

        if (checkContact(user, friend.get())) {
            return "이미 친구입니다.";
        }

        Contact contact = Contact.builder()
                .owner(user)
                .friend(friend.get())
                .nickname(nickname)
                .build();

        contactRepository.save(contact);
        return "친구 추가 성공";
    }

    /**
     * 친구 수정 메소드
     *
     * @param id       연락처 고유번호
     * @param nickname 친구 별명
     * @param email    친구 이메일
     * @return 친구 수정 결과
     */
    String editContact(Long id, String email, String nickname) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isEmpty()) {
            return "연락처가 존재하지 않습니다.";
        }

        Optional<User> friend = userRepository.findById(email);
        if (friend.isEmpty()) {
            return "존재하지 않는 사용자입니다.";
        }
        Contact contact = contactOptional.get();
        contact.setFriend(friend.get());
        contact.setNickname(nickname);
        contactRepository.save(contact);
        return "친구 수정 성공";
    }


    Contact getContact(Long id) {
        return contactRepository.findById(id).get();
    }

    /**
     * 친구 목록 조회 메소드
     *
     * @param username 사용자 아이디
     * @return 친구 목록
     */
    List<Contact> getContacts(String username) {
        User user = userRepository.findById(username).get();
        return contactRepository.findByOwner(user);
    }

    /**
     * 친구 삭제 메소드
     *
     * @param id 연락처 고유번호
     */
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    /**
     * 친구 여부 확인 메소드
     *
     * @param user   본인
     * @param friend 친구
     * @return 친구 여부 (true: 친구, false: 친구 아님)
     */
    public boolean checkContact(User user, User friend) {
        return contactRepository.existsByOwnerAndFriend(user, friend);
    }
}
