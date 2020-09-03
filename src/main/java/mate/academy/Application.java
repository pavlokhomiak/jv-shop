package mate.academy;

import java.math.BigDecimal;
import mate.academy.lb.Injector;
import mate.academy.model.Product;
import mate.academy.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

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

        productService.getAll().forEach(System.out::println);

        Product iphone9Update = productService.get(5L);

        System.out.println(iphone9Update);

        iphone9Update.setPrice(BigDecimal.valueOf(2000));

        System.out.println(iphone9Update);

        productService.update(iphone9Update);

        System.out.println(productService.get(5L));

        productService.delete(5L);

        productService.getAll().forEach(System.out::println);
    }
}
