package mate.academy.controllers;

import mate.academy.lb.Injector;
import mate.academy.model.Order;
import mate.academy.model.Product;
import mate.academy.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetOrderDetails extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        Long id = Long.parseLong(orderId);
        Order order = orderService.get(id);
        List<Product> productList = order.getProducts();
        req.setAttribute("products", productList);
        req.getRequestDispatcher("/WEB-INF/views/orderDetail.jsp").forward(req, resp);
    }
}
