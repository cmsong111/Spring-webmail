package deu.cse.spring_webmail.contacts;

import deu.cse.spring_webmail.user.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 연락처 엔티티
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Contact {
    /**
     * 연락처 고유번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 소유자
     */
    @ManyToOne
    private User owner;
    /**
     * 친구
     */
    @ManyToOne
    private User friend;
    /**
     * 친구 별명
     */
    private String nickname;
}
