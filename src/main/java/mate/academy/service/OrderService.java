package mate.academy.service;

import java.util.List;
import mate.academy.model.Order;
import mate.academy.model.ShoppingCart;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);

    Order get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
