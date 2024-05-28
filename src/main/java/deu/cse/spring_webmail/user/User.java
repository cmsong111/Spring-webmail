package deu.cse.spring_webmail.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 사용자 엔티티 클래스
 */
@Entity
@Table(name = "JAMES_USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    /**
     * 사용자 이름
     */
    @Id
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    /**
     * 사용자 비밀번호 해시 알고리즘
     */
    @Column(name = "PASSWORD_HASH_ALGORITHM", nullable = false)
    private String passwordHashAlgorithm;

    /**
     * 사용자 비밀번호
     */
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    /**
     * 사용자 버전
     */
    @Column(name = "version")
    private Integer version;

    /**
     * 사용자 권한 목록
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
