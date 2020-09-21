package mate.academy.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.service.ProductService;
import mate.academy.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User admin = new User("admin", "admin", "admin");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
