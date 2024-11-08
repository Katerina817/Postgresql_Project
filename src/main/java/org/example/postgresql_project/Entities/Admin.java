package org.example.postgresql_project.Entities;
import lombok.Data;

@Data
/*@Entity
@Table(name = "admin")*/
public class Admin {
    /*@Id
    @Column(name = "admin_id", nullable = false, length = 36)*/
    private String adminId;

    //@Column(name = "login", nullable = false, length = 20, unique = true)
    private String login;

    //@Column(name = "name", length = 30)
    private String name;

    //@Column(name = "surname", length = 30)
    private String surname;

    //@Column(name = "password", nullable = false, length = 34)
    private String password;

    //@Column(name = "email", length = 100)
    private String email;

    //@Column(name = "age")
    private Integer age;

    //@Column(name = "birth_year")
    private Integer birthYear;
}

