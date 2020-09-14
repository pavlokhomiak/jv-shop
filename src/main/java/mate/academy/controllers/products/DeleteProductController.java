package mate.academy.controllers.products;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.service.ProductService;

public class DeleteProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("id");
        Long id = Long.parseLong(productId);
        productService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/products/all/admin");
    }
}
