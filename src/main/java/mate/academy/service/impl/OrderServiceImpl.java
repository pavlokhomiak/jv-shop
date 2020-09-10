package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.OrderDao;
import mate.academy.lb.Inject;
import mate.academy.lb.Service;
import mate.academy.model.Order;
import mate.academy.model.ShoppingCart;
import mate.academy.service.OrderService;
import mate.academy.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getUserId());
        order.setProducts(List.copyOf(shoppingCart.getProducts()));
        shoppingCartService.clear(shoppingCart);
        return orderDao.create(order);
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
