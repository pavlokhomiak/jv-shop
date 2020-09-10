package mate.academy.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.model.Product;
import mate.academy.service.ProductService;

public class AddProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productName = req.getParameter("name");
        String price = req.getParameter("price");
        double priceDouble;
        Product product;
        try {
            priceDouble = Double.parseDouble(price);
            product = new Product(productName, priceDouble);
            productService.create(product);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (NumberFormatException e) {
            req.setAttribute("message", "Uncorrected price format");
            req.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(req, resp);
        }
    }
}
