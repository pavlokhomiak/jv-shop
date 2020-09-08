package mate.academy.service;

import java.util.List;
import mate.academy.model.User;

public interface UserService extends GenericService<User, Long> {

    User create(User user);

    User get(Long id);

    List<User> getAll();

    User update(User user);

    boolean delete(Long id);
}
