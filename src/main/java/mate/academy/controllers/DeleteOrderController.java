package mate.academy.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.service.OrderService;

public class DeleteOrderController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        Long id = Long.parseLong(orderId);
        orderService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/order/all/admin");
    }
}
