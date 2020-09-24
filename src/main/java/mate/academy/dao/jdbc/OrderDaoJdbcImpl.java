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
    public Order create(Order order) {
        String insertOrder = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertOrderQueryStatement
                        = connection.prepareStatement(insertOrder,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertOrderQueryStatement.setLong(1, order.getUserId());
            insertOrderQueryStatement.executeUpdate();
            ResultSet resultSet = insertOrderQueryStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long orderId = resultSet.getLong(1);
                order.setId(orderId);
                insertOrdersProducts(order, connection);
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
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateOrderStatement
                        = connection.prepareStatement(updateOrder)) {
            updateOrderStatement.setLong(1, order.getUserId());
            updateOrderStatement.setLong(2, order.getId());
            if (updateOrderStatement.executeUpdate() == 1) {
                updateOrdersProducts(order, connection);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Updating order " + order
                    + " is failed", e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String selectOrders = "SELECT user_id FROM orders "
                + "WHERE order_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectOrderProductsStatement
                        = connection.prepareStatement(selectOrders)) {
            selectOrderProductsStatement.setLong(1, id);
            ResultSet resultSet = selectOrderProductsStatement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong(1);
                Order order = new Order(userId);
                order.setId(id);
                order.setProducts(getProducts(id, connection));
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting order with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        String selectUserOrders = "SELECT order_id FROM orders "
                + "WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectUserOrdersStatement
                        = connection.prepareStatement(selectUserOrders)) {
            selectUserOrdersStatement.setLong(1, userId);
            ResultSet resultSet = selectUserOrdersStatement.executeQuery();
            List<Order> ordersList = new ArrayList<>();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong(1);
                Order order = new Order(userId);
                order.setId(orderId);
                order.setProducts(getProducts(orderId, connection));
                ordersList.add(order);
            }
            return ordersList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting orders by user id="
                    + userId + " is failed", e);
        }
    }

    @Override
    public List<Order> getAll() {
        String selectOrders = "SELECT * FROM orders WHERE deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectOrdersStatement
                         = connection.prepareStatement(selectOrders)) {
            ResultSet resultSet = selectOrdersStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong(1);
                Long userId = resultSet.getLong(2);
                Order order = new Order(userId);
                order.setId(orderId);
                order.setProducts(getProducts(orderId, connection));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting orders from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateOrder = "UPDATE orders SET deleted = true WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateOrderStatement
                        = connection.prepareStatement(updateOrder);) {

            updateOrderStatement.setLong(1, id);
            if (updateOrderStatement.executeUpdate() == 0) {
                return false;
            }
            deleteOrdersProducts(id, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting order with id="
                    + id + " is failed", e);
        }
        return true;
    }

    private List<Product> getProducts(Long orderId, Connection connection)
            throws SQLException {
        String selectProducts = "SELECT products.product_id, product_name, product_price "
                + "FROM orders_products "
                + "JOIN products "
                + "ON orders_products.product_id = products.product_id "
                + "WHERE orders_products.order_id = ?;";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement selectProductsStatement
                     = connection.prepareStatement(selectProducts)) {
            selectProductsStatement.setLong(1, orderId);
            ResultSet resultSet = selectProductsStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong(1);
                String productName = resultSet.getString(2);
                double productPrice = resultSet.getDouble(3);
                Product product = new Product(productName, productPrice);
                product.setId(productId);
                products.add(product);
            }
        }
        return products;
    }

    private void updateOrdersProducts(Order order, Connection connection)
            throws SQLException {
        String deleteOrdersProducts = "DELETE FROM orders_products WHERE order_id = ?;";
        try (PreparedStatement deleteOrdersProductsStatement
                     = connection.prepareStatement(deleteOrdersProducts)) {
            deleteOrdersProductsStatement.setLong(1, order.getId());
            deleteOrdersProductsStatement.executeUpdate();
            insertOrdersProducts(order, connection);
        }
    }

    private void deleteOrdersProducts(Long orderId, Connection connection)
            throws SQLException {
        String deleteOrdersProducts = "DELETE FROM orders_products WHERE order_id = ?;";
        try (PreparedStatement deleteOrdersProductsStatement
                     = connection.prepareStatement(deleteOrdersProducts)) {
            deleteOrdersProductsStatement.setLong(1, orderId);
            deleteOrdersProductsStatement.executeUpdate();
        }
    }

    private void setProductId(Product product, Connection connection)
            throws SQLException {
        String selectProducts = "SELECT product_id FROM products "
                + "WHERE product_name = ? AND deleted = false;";
        try (PreparedStatement selectProductsStatement
                     = connection.prepareStatement(selectProducts)) {
            selectProductsStatement.setString(1, product.getName());
            ResultSet resultSet = selectProductsStatement.executeQuery();
            resultSet.next();
            product.setId(resultSet.getLong(1));
        }
    }

    private void insertOrdersProducts(Order order, Connection connection)
            throws SQLException {
        String insertOrdersProducts
                = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        try (PreparedStatement insertOrdersProductsStatement
                     = connection.prepareStatement(insertOrdersProducts)) {
            for (Product product : order.getProducts()) {
                setProductId(product, connection);
                insertOrdersProductsStatement.setLong(1, order.getId());
                insertOrdersProductsStatement.setLong(2, product.getId());
                insertOrdersProductsStatement.executeUpdate();
            }
        }
    }
}
