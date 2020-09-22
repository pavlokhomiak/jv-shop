package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.ProductDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lb.Dao;
import mate.academy.model.Product;
import mate.academy.util.ConnectionUtil;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (product_name, product_price) VALUE (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice().doubleValue());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating product " + product
                    + " is failed", e);
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET product_name = ?, product_price = ? "
                + "WHERE product_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice().doubleValue());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Updating product " + product
                     + " is failed", e);
        }
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(retrieveProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Getting product with id="
                    + id + " is failed", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(retrieveProductFromResultSet(resultSet));
            }
            return productList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting products from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products SET deleted = true WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting product with id="
                    + id + " is failed", e);
        }
    }

    private Product retrieveProductFromResultSet(ResultSet resultSet)
            throws SQLException {
        String productName = resultSet.getString("product_name");
        double productPrice = resultSet.getDouble("product_price");
        Product product = new Product(productName, productPrice);
        product.setId(resultSet.getLong("product_id"));
        return product;
    }
}
