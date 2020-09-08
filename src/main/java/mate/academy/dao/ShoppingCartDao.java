package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {

    Optional<ShoppingCart> getByUserId(Long userId);
}
