package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    Optional<ShoppingCart> get(Long id);

    List<ShoppingCart> getAll();

    boolean delete(Long id);

    Optional<ShoppingCart> getByUserId(Long userId);
}
