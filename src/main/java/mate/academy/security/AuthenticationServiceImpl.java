package mate.academy.security;

import java.util.Optional;
import mate.academy.exceptions.AuthenticationException;
import mate.academy.lb.Inject;
import mate.academy.lb.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password)
            throws AuthenticationException {
        Optional<User> userFromDB = userService.findByLogin(login);
        if (userFromDB.isPresent()) {
            byte[] salt = userFromDB.get().getSalt();
            String hashedPassword = HashUtil.hashPassword(password, salt);

            if (userFromDB.get().getPassword().equals(hashedPassword)) {
                return userFromDB.get();
            }
        }

        throw new AuthenticationException("Incorrect username or password");
    }
}
