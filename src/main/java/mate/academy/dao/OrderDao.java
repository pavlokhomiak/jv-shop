package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Order;

public interface OrderDao extends GenericDao<Order, Long> {

    Order create(Order order);

    Order update(Order order);

    List<Order> getUserOrders(Long userId);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
