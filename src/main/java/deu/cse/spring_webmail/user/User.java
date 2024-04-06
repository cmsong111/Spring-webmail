package deu.cse.spring_webmail.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "PASSWORD_HASH_ALGORITHM")
    private String passwordHashAlgorithm;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "VERSION")
    private Integer version;
}
