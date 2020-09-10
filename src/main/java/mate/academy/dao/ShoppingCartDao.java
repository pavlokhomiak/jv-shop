package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.ShoppingCart;

public interface ShoppingCartDao {

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
