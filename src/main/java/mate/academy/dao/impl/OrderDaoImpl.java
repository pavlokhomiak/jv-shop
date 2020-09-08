package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.dao.OrderDao;
import mate.academy.lb.Dao;
import mate.academy.model.Order;
import mate.academy.storage.Storage;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(i -> Storage.orders.get(i).equals(order.getId()))
                .forEach(i -> Storage.orders.set(i, order));
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders
                .removeIf(o -> o.getId().equals(id));
    }
}
