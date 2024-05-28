package deu.cse.spring_webmail.contacts;

import deu.cse.spring_webmail.user.User;
import deu.cse.spring_webmail.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param partner  친구 아이디
     */
    public void addContact(String username, String partner) {
        User user = userRepository.findById(username).get();
        User friend = userRepository.findById(partner).get();

        if (checkContact(user, friend)) {
            log.info("이미 친구입니다.");
            return;
        }

        Contact contact = Contact.builder()
                .owner(user)
                .friend(friend)
                .nickname(friend.getUserName())
                .build();

        contactRepository.save(contact);
    }

    /**
     * 친구 목록 조회 메소드
     *
     * @param user 사용자
     * @return 친구 목록
     */
    List<Contact> getContacts(User user) {
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
