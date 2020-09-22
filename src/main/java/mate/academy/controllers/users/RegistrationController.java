package mate.academy.controllers.users;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.model.Role;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;
import mate.academy.service.UserService;

public class RegistrationController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("log");
        String password = req.getParameter("pas");
        String passwordRepeat = req.getParameter("pas-rep");

        if (password.equals(passwordRepeat)) {
            User user = new User(name, login, password);
            user.setRoles(Set.of(Role.of("USER")));
            userService.create(user);
            ShoppingCart shoppingCart = new ShoppingCart(user.getId());
            shoppingCartService.create(shoppingCart);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Your password are not same");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
