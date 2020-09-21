package mate.academy;

import java.math.BigDecimal;
import mate.academy.lb.Injector;
import mate.academy.model.Product;
import mate.academy.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);
        Product product = new Product("Iphone 10", 3000);
        System.out.println(productService.create(product));
        System.out.println(productService.getAll());
        product.setPrice(BigDecimal.valueOf(4000));
        System.out.println(productService.update(product));
        System.out.println(productService.getAll());
        System.out.println(productService.get(2L));
        System.out.println(productService.delete(4L));
        System.out.println(productService.getAll());
    }
}
