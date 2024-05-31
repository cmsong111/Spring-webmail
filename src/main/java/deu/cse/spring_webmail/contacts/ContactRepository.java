package deu.cse.spring_webmail.contacts;

import deu.cse.spring_webmail.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 연락처 레포지토리 (Spring Data JPA)
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * 사용자의 친구 목록 조회
     *
     * @param user 사용자
     * @return 친구 목록
     */
    List<Contact> findByOwner(User user);

    /**
     * 사용자와 친구가 이미 연락처인지 확인
     *
     * @param owner  사용자
     * @param friend 친구
     * @return 연락처 여부(true: 이미 연락처, false: 연락처가 아님)
     */
    boolean existsByOwnerAndFriend(User owner, User friend);

}
