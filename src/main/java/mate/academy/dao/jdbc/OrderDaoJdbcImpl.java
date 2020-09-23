package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.OrderDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lb.Dao;
import mate.academy.model.Order;
import mate.academy.model.Product;
import mate.academy.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        List<Order> ordersList = new ArrayList<>();
        String selectUserOrders = "SELECT order_id FROM orders WHERE user_id = ? AND deleted = false;";
        String selectOrdersProducts = "SELECT orders.order_id, orders.user_id, products.product_id, " +
                "products.product_name, products.product_price " +
                "FROM orders " +
                "INNER JOIN orders_products " +
                "ON orders.order_id = orders_products.order_id " +
                "INNER JOIN products " +
                "ON products.product_id = orders_products.product.id " +
                "WHERE orders.order_id = ? AND products.deleted = false;";
        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectUserOrdersStatement = connection.prepareStatement(selectUserOrders);
            selectUserOrdersStatement.setLong(1, userId);
            ResultSet userResultSet = selectUserOrdersStatement.executeQuery();
            while (userResultSet.next()) {
                Long orderId = userResultSet.getLong("order_id");
                PreparedStatement selectOrderProductsStatement = connection.prepareStatement(selectOrdersProducts);
                selectOrderProductsStatement.setLong(1, orderId);
                ResultSet resultSet = selectOrderProductsStatement.executeQuery();
                ordersList.add(retrieveOrderFromResultSet(resultSet));
            }
            return ordersList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting orders by user id="
                    + userId + " is failed", e);
        }
    }

    @Override
    public Order create(Order order) {
        String insertOrder = "INSERT INTO orders (user_id) VALUES (?);";
        String insertOrdersProducts = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insertOrderQueryStatement = connection.prepareStatement(insertOrder,
                    Statement.RETURN_GENERATED_KEYS);
            insertOrderQueryStatement.setLong(1, order.getUserId());
            insertOrderQueryStatement.executeUpdate();
            ResultSet resultSet = insertOrderQueryStatement.getGeneratedKeys();
            Long orderId = resultSet.getLong("order_id");
            order.setId(orderId);
            for (Product product : order.getProducts()) {
                PreparedStatement insertOrderProductsStatement = connection.prepareStatement(insertOrdersProducts);
                insertOrderProductsStatement.setLong(1, orderId);
                insertOrderProductsStatement.setLong(2, product.getId());
                insertOrderProductsStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating order " + order
                    + " is failed", e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        String updateOrder = "UPDATE orders SET user_id = ? WHERE order_id = ? AND deleted = false";
        String deleteOrdersProducts = "DELETE FROM orders_products WHERE order_id = ?;";
        String insertOrdersProducts = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateOrderStatement = connection.prepareStatement(updateOrder);
            updateOrderStatement.setLong(1, order.getUserId());
            updateOrderStatement.setLong(2, order.getId());
            if (updateOrderStatement.executeUpdate() == 1) {
                PreparedStatement deleteOrdersProductsStatement =
                        connection.prepareStatement(deleteOrdersProducts);
                deleteOrdersProductsStatement.setLong(1, order.getId());
                deleteOrdersProductsStatement.executeUpdate();
                for (Product product : order.getProducts()) {
                    PreparedStatement insertOrdersProductsStatement = connection
                            .prepareStatement(insertOrdersProducts);
                    insertOrdersProductsStatement.setLong(1, order.getId());
                    insertOrdersProductsStatement.setLong(2, product.getId());
                    insertOrdersProductsStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Updating order " + order
                    + " is failed", e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String selectOrderProducts = "SELECT orders.order_id, orders.user_id, products.product_id, " +
                "products.product_name, products.product_price " +
                "FROM orders " +
                "INNER JOIN orders_products " +
                "ON orders.order_id = orders_products.order_id " +
                "INNER JOIN products " +
                "ON products.product_id = orders_products.product.id " +
                "WHERE orders.order_id = ? AND orders.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectOrderProductsStatement = connection.prepareStatement(selectOrderProducts);
            selectOrderProductsStatement.setLong(1, id);
            ResultSet resultSet = selectOrderProductsStatement.executeQuery();
            return Optional.of(retrieveOrderFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Getting order with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        String selectOrders = "SELECT * FROM orders WHERE deleted = false;";
        String selectOrderProducts = "SELECT orders.order_id, orders.user_id, products.product_id, " +
                "products.product_name, products.product_price " +
                "FROM orders " +
                "INNER JOIN orders_products " +
                "ON orders.order_id = orders_products.order_id " +
                "INNER JOIN products " +
                "ON products.product_id = orders_products.product.id " +
                "WHERE orders.order_id = ? AND orders.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectOrdersStatement = connection.prepareStatement(selectOrders);
            ResultSet ordersResultSet = selectOrdersStatement.executeQuery();
            while (ordersResultSet.next()) {
                Long orderId = ordersResultSet.getLong("order_id");
                PreparedStatement selectOrderProductsStatement = connection.prepareStatement(selectOrderProducts);
                selectOrderProductsStatement.setLong(1, orderId);
                ResultSet resultSet = selectOrderProductsStatement.executeQuery();
                while (resultSet.next()) {
                    orderList.add(retrieveOrderFromResultSet(resultSet));
                }
            }
            return orderList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting orders from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateOrder = "UPDATE orders SET deleted = true WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateOrderStatement = connection.prepareStatement(updateOrder);
            updateOrderStatement.setLong(1, id);
            if (updateOrderStatement.executeUpdate() == 0) {
                return false;
            }
            String deleteProducts = "DELETE FROM orders_products WHERE order_id = ?;";
            PreparedStatement deleteProductStatement = connection.prepareStatement(deleteProducts);
            deleteProductStatement.setLong(1, id);
            deleteProductStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting order with id="
                    + id + " is failed", e);
        }
        return true;
    }

    private Order retrieveOrderFromResultSet(ResultSet resultSet)
            throws SQLException {
        Long orderId = resultSet.getLong("orders.order_id");
        Long userId = resultSet.getLong("orders.user_id");
        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            Long productId = resultSet.getLong("products.product_id");
            String productName = resultSet.getString("products.name");
            double productPrice = resultSet.getDouble("products.price");
            Product product = new Product(productName, productPrice);
            product.setId(productId);
            productList.add(product);
        }
        Order order = new Order(userId);
        order.setId(orderId);
        return order;
    }
}
