package mate.academy.dao;

import java.util.List;
import mate.academy.model.Order;

public interface OrderDao extends GenericDao<Order, Long> {

    List<Order> getUserOrders(Long userId);
}
