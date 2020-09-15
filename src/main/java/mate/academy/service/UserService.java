package mate.academy.service;

import java.util.Optional;
import mate.academy.model.User;

public interface UserService extends GenericService<User, Long> {
    Optional<User> findByLogin(String login);
}
