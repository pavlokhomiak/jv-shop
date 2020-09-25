package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lb.Dao;
import mate.academy.model.Product;
import mate.academy.model.ShoppingCart;
import mate.academy.util.ConnectionUtil;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String insertShoppingCarts = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertShoppingCartsStatement
                        = connection.prepareStatement(insertShoppingCarts,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertShoppingCartsStatement.setLong(1, shoppingCart.getUserId());
            insertShoppingCartsStatement.executeUpdate();
            ResultSet resultSet = insertShoppingCartsStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long shoppingCartId = resultSet.getLong(1);
                shoppingCart.setId(shoppingCartId);
                insertShoppingCartsStatement.close();
                insertShoppingCartsProducts(shoppingCart, connection);
                return shoppingCart;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating shopping cart " + shoppingCart
                    + " is failed", e);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        String updateShoppingCart = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE cart_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateShoppingCartStatement
                        = connection.prepareStatement(updateShoppingCart)) {
            updateShoppingCartStatement.setLong(1, shoppingCart.getUserId());
            updateShoppingCartStatement.setLong(2, shoppingCart.getId());
            if (updateShoppingCartStatement.executeUpdate() == 1) {
                updateShoppingCartStatement.close();
                deleteShoppingCartProduct(shoppingCart.getId(), connection);
                insertShoppingCartsProducts(shoppingCart, connection);
                return shoppingCart;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Updating shopping cart " + shoppingCart
                    + " is failed", e);
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String selectShoppingCart = "SELECT * FROM shopping_carts WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectShoppingCartsStatement
                        = connection.prepareStatement(selectShoppingCart)) {
            selectShoppingCartsStatement.setLong(1, id);
            ResultSet resultSet = selectShoppingCartsStatement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = new ShoppingCart(resultSet.getLong(2));
                shoppingCart.setId(id);
                selectShoppingCartsStatement.close();
                shoppingCart.setProducts(getProducts(id, connection));
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting shopping cart with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String selectShoppingCart = "SELECT * FROM shopping_carts WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectShoppingCartsStatement
                        = connection.prepareStatement(selectShoppingCart)) {
            selectShoppingCartsStatement.setLong(1, userId);
            ResultSet resultSet = selectShoppingCartsStatement.executeQuery();
            if (resultSet.next()) {
                Long cartId = resultSet.getLong(1);
                ShoppingCart shoppingCart = new ShoppingCart(userId);
                shoppingCart.setId(cartId);
                selectShoppingCartsStatement.close();
                shoppingCart.setProducts(getProducts(cartId, connection));
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting shopping cart by user id="
                    + userId + " is failed", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String selectShoppingCarts = "SELECT * FROM shopping_carts WHERE deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectShoppingCartsStatement
                        = connection.prepareStatement(selectShoppingCarts)) {
            ResultSet cartsResultSet = selectShoppingCartsStatement.executeQuery();
            List<ShoppingCart> shoppingCartList = new ArrayList<>();
            while (cartsResultSet.next()) {
                Long cartId = cartsResultSet.getLong(1);
                Long userId = cartsResultSet.getLong(2);
                ShoppingCart shoppingCart = new ShoppingCart(userId);
                shoppingCart.setId(cartId);
                shoppingCartList.add(shoppingCart);
            }
            selectShoppingCartsStatement.close();
            for (ShoppingCart cart : shoppingCartList) {
                cart.setProducts(getProducts(cart.getId(), connection));
            }
            return shoppingCartList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting shopping carts from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateShoppingCarts = "UPDATE shopping_carts SET deleted = true "
                + "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateShoppingCartsStatement
                        = connection.prepareStatement(updateShoppingCarts)) {
            updateShoppingCartsStatement.setLong(1, id);
            if (updateShoppingCartsStatement.executeUpdate() == 0) {
                return false;
            }
            updateShoppingCartsStatement.close();
            deleteShoppingCartProduct(id, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting shopping cart with id="
                    + id + " is failed", e);
        }
        return true;
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

    private List<Product> getProducts(Long cartId, Connection connection)
            throws SQLException {
        String selectProduct = "SELECT products.product_id, product_name, product_price "
                + "FROM shopping_carts_products "
                + "JOIN products "
                + "ON shopping_carts_products.product_id = products.product_id "
                + "WHERE shopping_carts_products.cart_id = ?;";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement selectProductStatement
                     = connection.prepareStatement(selectProduct)) {
            selectProductStatement.setLong(1, cartId);
            ResultSet resultSet = selectProductStatement.executeQuery();
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

    private void insertShoppingCartsProducts(ShoppingCart shoppingCart, Connection connection)
            throws SQLException {
        String insertShoppingCartsProducts
                = "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?);";
        try (PreparedStatement insertStatement
                     = connection.prepareStatement(insertShoppingCartsProducts)) {
            for (Product product : shoppingCart.getProducts()) {
                if (product.getId() == null) {
                    setProductId(product, connection);
                }
                insertStatement.setLong(1, shoppingCart.getId());
                insertStatement.setLong(2, product.getId());
                insertStatement.executeUpdate();
            }
        }
    }

    private void deleteShoppingCartProduct(Long cartId, Connection connection)
            throws SQLException {
        String deleteShoppingCartProduct = "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(deleteShoppingCartProduct)) {
            statement.setLong(1, cartId);
            statement.executeUpdate();
        }
    }
}


