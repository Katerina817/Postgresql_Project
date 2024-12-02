package org.example.postgresql_project.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
/*@Entity
@Table(name = "users")*/
public class User {
    /*@Id
    @Column(name = "user_id", nullable = false, length = 36)*/
    private String userId;
    //@Column(name = "login", nullable = false, length = 20)
    private String login;
    //@Column(name = "name", length = 30)
    private String name;
    //@Column(name = "surname", length = 30)
    private String surname;
    //@Column(name = "password", nullable = false, length = 34)
    private String password;
    //@Column(name = "email", length = 100)
    private String email;
}
