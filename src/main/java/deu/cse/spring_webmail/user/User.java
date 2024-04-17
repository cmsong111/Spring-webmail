package deu.cse.spring_webmail.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "JAMES_USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    @Column(name = "PASSWORD_HASH_ALGORITHM", nullable = false)
    private String passwordHashAlgorithm;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "version")
    private Integer version;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
