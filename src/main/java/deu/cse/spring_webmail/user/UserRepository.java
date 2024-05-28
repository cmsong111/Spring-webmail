package deu.cse.spring_webmail.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자 정보를 관리하는 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
