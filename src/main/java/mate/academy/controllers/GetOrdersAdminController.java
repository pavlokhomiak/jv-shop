package mate.academy.controllers;

import mate.academy.lb.Injector;
import mate.academy.model.Order;
import mate.academy.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetOrdersAdminController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orderList = orderService.getAll();
        req.setAttribute("orders", orderList);
        req.getRequestDispatcher("/WEB-INF/views/allOrdersAdmin.jsp").forward(req, resp);
    }
}
