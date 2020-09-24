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
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String selectShoppingCartsProducts = "SELECT cart_id, user_id, " +
                "product_id, product_name, product_price " +
                "FROM shopping_carts " +
                "INNER JOIN shopping_carts_products " +
                "ON shopping_carts.cart_id = shopping_carts_products.cart_id " +
                "INNER JOIN products " +
                "ON shopping_carts_products.product_id = products.product_id " +
                "WHERE user_id = ? AND shopping_carts.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectShoppingCartsStatement = connection
                    .prepareStatement(selectShoppingCartsProducts);
            selectShoppingCartsStatement.setLong(1, userId);
            ResultSet resultSet = selectShoppingCartsStatement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = retrieveShoppingCartFromResultSet(resultSet);
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting shopping cart by user id="
                    + userId + " is failed", e);
        }
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String insertShoppingCarts = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        String insertShoppingCartProducts = "INSERT INTO shopping_carts_products " +
                "(cart_id, product_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement insertShoppingCartsStatement = connection
                .prepareStatement(insertShoppingCarts, Statement.RETURN_GENERATED_KEYS)) {
            insertShoppingCartsStatement.setLong(1, shoppingCart.getUserId());
            insertShoppingCartsStatement.executeUpdate();
            ResultSet resultSet = insertShoppingCartsStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long shoppingCartId = resultSet.getLong(1);
                shoppingCart.setId(shoppingCartId);
                for (Product product : shoppingCart.getProducts()) {
                    PreparedStatement insertShoppingCartProductsStatement = connection
                            .prepareStatement(insertShoppingCartProducts);
                    insertShoppingCartProductsStatement.setLong(1, shoppingCartId);
                    insertShoppingCartProductsStatement.setLong(2, product.getId());
                    insertShoppingCartProductsStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating shopping cart " + shoppingCart
                    + " is failed", e);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        String updateShoppingCart = "UPDATE shopping_carts SET user_id = ? " +
                "WHERE cart_id = ? AND deleted = false;";
        String deleteShoppingCartProduct = "DELETE FROM shopping_carts_products " +
                "WHERE cart_id = ?;";
        String insertShoppingCartProduct = "INSERT INTO shopping_carts_products " +
                "(cart_id, product_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateShoppingCartStatement = connection
                    .prepareStatement(updateShoppingCart);
            updateShoppingCartStatement.setLong(1, shoppingCart.getUserId());
            updateShoppingCartStatement.setLong(2, shoppingCart.getId());
            if (updateShoppingCartStatement.executeUpdate() == 1) {
                PreparedStatement deleteShoppingCartProductStatement = connection
                        .prepareStatement(deleteShoppingCartProduct);
                deleteShoppingCartProductStatement.setLong(1, shoppingCart.getId());
                deleteShoppingCartProductStatement.executeUpdate();
                for (Product product : shoppingCart.getProducts()) {
                    PreparedStatement insertShoppingCartProductStatement = connection
                            .prepareStatement(insertShoppingCartProduct);
                    insertShoppingCartProductStatement.setLong(1, shoppingCart.getId());
                    insertShoppingCartProductStatement.setLong(2, product.getId());
                    insertShoppingCartProductStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Updating shopping cart " + shoppingCart
                    + " is failed", e);
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String selectShoppingCartsProducts = "SELECT shopping_carts.cart_id, shopping_carts.user_id, " +
                "products.product_id, products.product_name, products.product_price " +
                "FROM shopping_carts " +
                "INNER JOIN shopping_carts_products " +
                "ON shopping_carts.cart_id = shopping_carts_products.cart_id " +
                "INNER JOIN products " +
                "ON shopping_carts_products.product_id = products.product_id " +
                "WHERE shopping_carts.cart_id = ? AND shopping_carts.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectShoppingCartsStatement = connection
                    .prepareStatement(selectShoppingCartsProducts);
            selectShoppingCartsStatement.setLong(1, id);
            ResultSet resultSet = selectShoppingCartsStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retrieveShoppingCartFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting shopping cart with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        String selectShoppingCarts = "SELECT * FROM shopping_carts WHERE deleted = false;";
        String selectShoppingCartsProducts = "SELECT shopping_carts.cart_id, shopping_carts.user_id, " +
                "products.product_id, products.product_name, products.product_price " +
                "FROM shopping_carts " +
                "INNER JOIN shopping_carts_products " +
                "ON shopping_carts.cart_id = shopping_carts_products.cart_id " +
                "INNER JOIN products " +
                "ON shopping_carts_products.product_id = products.product_id " +
                "WHERE shopping_carts.cart_id = ? AND shopping_carts.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectShoppingCartsStatement = connection
                    .prepareStatement(selectShoppingCarts);
            ResultSet cartsResultSet = selectShoppingCartsStatement.executeQuery();
            while (cartsResultSet.next()) {
                Long cartId = cartsResultSet.getLong("cart_id");
                PreparedStatement selectShoppingCartsProductsStatement = connection
                        .prepareStatement(selectShoppingCartsProducts);
                selectShoppingCartsProductsStatement.setLong(1, cartId);
                ResultSet resultSet = selectShoppingCartsProductsStatement.executeQuery();
                while (resultSet.next()) {
                    shoppingCartList.add(retrieveShoppingCartFromResultSet(resultSet));
                }
            }
            return shoppingCartList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting shopping carts from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateShoppingCarts = "UPDATE shopping_carts SET deleted = true " +
                "WHERE cart_id = ?;";
        String deleteShoppingCartsProducts = "DELETE FROM shopping_carts WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateShoppingCartsStatement = connection
                    .prepareStatement(updateShoppingCarts);
            updateShoppingCartsStatement.setLong(1, id);
            if (updateShoppingCartsStatement.executeUpdate() == 0) {
                return false;
            }
            PreparedStatement deleteShoppingCartsProductsStatement = connection
                    .prepareStatement(deleteShoppingCartsProducts);
            deleteShoppingCartsProductsStatement.setLong(1, id);
            deleteShoppingCartsProductsStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting shopping cart with id="
                    + id + " is failed", e);
        }
        return true;
    }

    private ShoppingCart retrieveShoppingCartFromResultSet(ResultSet resultSet)
            throws SQLException {
        Long cartId = resultSet.getLong("shopping_carts.cart.id");
        Long userId = resultSet.getLong("shopping_carts.user.id");
        List<Product> productList = new ArrayList<>();
        do {
            Long productId = resultSet.getLong("products.product_id");
            String productName = resultSet.getString("products.product_name");
            double productPrice = resultSet.getDouble("products.product_price");
            Product product = new Product(productName, productPrice);
            product.setId(productId);
            productList.add(product);
        } while (resultSet.next());
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(cartId);
        shoppingCart.setProducts(productList);
        return shoppingCart;
    }
}
