package mate.academy;

import java.math.BigDecimal;
import mate.academy.lb.Injector;
import mate.academy.model.Product;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.service.OrderService;
import mate.academy.service.ProductService;
import mate.academy.service.ShoppingCartService;
import mate.academy.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);
        final UserService userService = (UserService) injector.getInstance(UserService.class);
        final ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        final OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        System.out.println("\nPRODUCT TESTS");
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
        System.out.println("\nAll products in store ");
        productService.getAll().forEach(System.out::println);
        Product iphone9Update = new Product(productService.get(5L).getName(),
                productService.get(5L).getPrice().doubleValue());
        System.out.println("iphone9Update " + iphone9Update);
        System.out.println("=== SHOULD SET CORRECT id ===");
        iphone9Update.setId(iphone9.getId());
        System.out.println("iphone9Update " + iphone9Update);
        iphone9Update.setPrice(BigDecimal.valueOf(2000.0));
        System.out.println("iphone9Update get new price " + iphone9Update);
        productService.update(iphone9Update);
        System.out.println("Get iphone9 after update price " + productService.get(5L));
        productService.delete(4L);
        System.out.println("\nProducts after delete iphone8 ");
        productService.getAll().forEach(System.out::println);

        System.out.println("\n\n\nUSER TESTS");
        User userPaulo = new User("Paulo", "folder", "02041993");
        User userRik = new User("Rik", "morti", "123");
        User userMorti = new User("Morti", "rik", "321");
        User userAristarh = new User("Aristarh", "aric", "qwerty");
        userService.create(userPaulo);
        userService.create(userRik);
        userService.create(userMorti);
        userService.create(userAristarh);
        System.out.println("All users before delete ");
        userService.getAll().forEach(System.out::println);
        userService.delete(userAristarh.getId());
        System.out.println("All users after delete Aristarh");
        userService.getAll().forEach(System.out::println);
        System.out.println("=== For updating we must have access to target login, password.. ===");
        User updateUser = new User("Pavlo", userPaulo.getLogin(), userPaulo.getPassword());
        System.out.println("=== SHOULD SET CORRECT id ===");
        userPaulo.setId(userPaulo.getId());
        userService.update(updateUser);
        System.out.println("Update Pavlos name " + userPaulo);
        User getUserPaulo = userService.get(userPaulo.getId());

        System.out.println("\n\n\nSHOPPING CART TESTS");
        ShoppingCart pavlosShoppingCart = new ShoppingCart(getUserPaulo.getId());
        ShoppingCart riksShoppingCart = new ShoppingCart(userRik.getId());
        ShoppingCart mortisShoppingCart = new ShoppingCart(userMorti.getId());
        shoppingCartService.create(pavlosShoppingCart);
        shoppingCartService.create(riksShoppingCart);
        shoppingCartService.create(mortisShoppingCart);
        System.out.println("Get shoppingCart by Rik user Id "
                + shoppingCartService.getByUserId(riksShoppingCart.getUserId()));
        System.out.println("true if Riks shoppingCart was deleted: "
                + shoppingCartService.delete(riksShoppingCart));
        shoppingCartService.addProduct(pavlosShoppingCart, iphone5);
        shoppingCartService.addProduct(pavlosShoppingCart, iphone7);
        shoppingCartService.addProduct(pavlosShoppingCart, iphone9);
        shoppingCartService.addProduct(mortisShoppingCart, iphone5);
        shoppingCartService.addProduct(mortisShoppingCart, iphone5);
        shoppingCartService.addProduct(mortisShoppingCart, iphone5);
        System.out.println("Pavlos shoppingCart before removing single product "
                + shoppingCartService.getByUserId(getUserPaulo.getId()).getProducts());
        shoppingCartService.deleteProduct(pavlosShoppingCart, iphone5);
        System.out.println("Pavlos shoppingCart after removing single product "
                + shoppingCartService.getByUserId(getUserPaulo.getId()).getProducts());
        shoppingCartService.clear(pavlosShoppingCart);
        System.out.println("Pavlos shoppingCart after removing all products "
                + shoppingCartService.getByUserId(getUserPaulo.getId()).getProducts());

        System.out.println("\n\n\nORDER TESTS");
        shoppingCartService.addProduct(pavlosShoppingCart, iphone5);
        shoppingCartService.addProduct(pavlosShoppingCart, iphone7);
        shoppingCartService.addProduct(pavlosShoppingCart, iphone9);
        System.out.println("Pavlos shoppingCart "
                + shoppingCartService.getByUserId(getUserPaulo.getId()).getProducts());
        orderService.completeOrder(pavlosShoppingCart);
        orderService.completeOrder(mortisShoppingCart);
        System.out.println("Pavlos shoppingCart after completed order "
                + shoppingCartService.getByUserId(getUserPaulo.getId()).getProducts());
        System.out.println("Order after complete order " + orderService.get(1L).getProducts());
        shoppingCartService.addProduct(pavlosShoppingCart, iphone6);
        shoppingCartService.addProduct(pavlosShoppingCart, iphone6);
        shoppingCartService.addProduct(pavlosShoppingCart, iphone6);
        orderService.completeOrder(pavlosShoppingCart);
        System.out.println("\nShow all userPavlo orders ");
        orderService.getUserOrders(pavlosShoppingCart.getUserId()).forEach(System.out::println);
        System.out.println("\nShow all users orders ");
        orderService.getAll().forEach(System.out::println);
        orderService.delete(1L);
        System.out.println("\nShow all userPavlo orders after delete ");
        orderService.getUserOrders(pavlosShoppingCart.getUserId()).forEach(System.out::println);
    }
}
