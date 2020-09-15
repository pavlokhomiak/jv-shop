package mate.academy.dao;

import mate.academy.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);
}
