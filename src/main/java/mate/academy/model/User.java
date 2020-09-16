package mate.academy.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String name;
    private String login;
    private String password;
    private List<Role> roles;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
