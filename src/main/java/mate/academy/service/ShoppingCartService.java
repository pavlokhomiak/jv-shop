package mate.academy.service;

import mate.academy.model.Product;
import mate.academy.model.ShoppingCart;

public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
