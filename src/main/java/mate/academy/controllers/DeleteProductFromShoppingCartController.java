package mate.academy.controllers;

import mate.academy.lb.Injector;
import mate.academy.model.Product;
import mate.academy.model.ShoppingCart;
import mate.academy.service.ProductService;
import mate.academy.service.ShoppingCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductFromShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("id");
        Long id = Long.parseLong(productId);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        Product product = productService.get(id);
        shoppingCartService.deleteProduct(shoppingCart, product);
        resp.sendRedirect(req.getContextPath() + "/shopping-cart/products/get");
    }
}
