package mate.academy.storage;

import java.util.ArrayList;
import java.util.List;
import mate.academy.model.Order;
import mate.academy.model.Product;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static long productId = 0;
    private static long orderId = 0;
    private static long shoppingCartId = 0;
    private static long userId = 0;

    public static void addProduct(Product product) {
        productId++;
        product.setId(productId);
        products.add(product);
    }

    public static void addOrder(Order order) {
        orderId++;
        order.setId(orderId);
        orders.add(order);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartId++;
        shoppingCart.setId(shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void addUser(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
    }
}
