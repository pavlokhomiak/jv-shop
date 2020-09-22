package mate.academy.model;

import java.util.Set;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String login;
    private String password;
    private Set<Role> roles;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
