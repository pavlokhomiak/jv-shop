package mate.academy.model;

import lombok.Data;

@Data
public class Role {
    private Long id;
    private RoleName roleName;

    private Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public enum RoleName {
        USER, ADMIN
    }
}
