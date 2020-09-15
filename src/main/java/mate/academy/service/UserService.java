package mate.academy.service;

import mate.academy.model.User;

import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    User findByLogin(String login);
}
