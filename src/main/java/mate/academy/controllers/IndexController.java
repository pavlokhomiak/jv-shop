package mate.academy.controllers;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lb.Injector;
import mate.academy.model.Product;
import mate.academy.service.ProductService;

public class IndexController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    public void init() throws ServletException {
        super.init();
        Product iphone5 = new Product("Iphone 5", 1500);
        Product iphone6 = new Product("Iphone 6", 1600);
        Product iphone7 = new Product("Iphone 7", 1700);
        Product iphone8 = new Product("Iphone 8", 1800);
        Product iphone9 = new Product("Iphone 9", 1900);
        productService.create(iphone5);
        productService.create(iphone6);
        productService.create(iphone7);
        productService.create(iphone8);
        productService.create(iphone9);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = LocalTime.now().format(dateTimeFormatter);
        req.setAttribute("time", time);
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
