package mate.academy.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.OrderDao;
import mate.academy.lb.Inject;
import mate.academy.lb.Service;
import mate.academy.model.Order;
import mate.academy.model.ShoppingCart;
import mate.academy.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderDao orderDao;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = orderDao.completeOrder(shoppingCart);
        order.setProducts(new ArrayList<>(shoppingCart.getProducts()));
        shoppingCart.getProducts().removeAll(shoppingCart.getProducts());
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
