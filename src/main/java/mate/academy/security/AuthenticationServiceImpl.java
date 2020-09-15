package mate.academy.security;

import mate.academy.exceptions.AuthenticationException;
import mate.academy.lb.Inject;
import mate.academy.lb.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password)
            throws AuthenticationException {
        User userFromDB = userService.findByLogin(login);

        if (userFromDB != null && userFromDB.getPassword().equals(password)) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
