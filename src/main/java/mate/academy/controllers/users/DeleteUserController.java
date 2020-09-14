package mate.academy.controllers.users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.service.ShoppingCartService;
import mate.academy.service.UserService;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        shoppingCartService.delete(shoppingCartService.getByUserId(id));
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
