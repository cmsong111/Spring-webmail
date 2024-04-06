package deu.cse.spring_webmail.user;

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
public class User {
    @Id
    private String userName;
    private String passwordHashAlgorithm;
    private String password;
    private Integer version;
}
