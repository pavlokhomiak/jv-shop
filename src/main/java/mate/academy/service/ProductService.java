package mate.academy.service;

import java.util.List;
import mate.academy.model.Product;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
