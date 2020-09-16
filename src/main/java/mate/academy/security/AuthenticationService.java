package mate.academy.security;

import mate.academy.exceptions.AuthenticationException;
import mate.academy.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
