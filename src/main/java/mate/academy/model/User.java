package mate.academy.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String login;
    private String password;
}
