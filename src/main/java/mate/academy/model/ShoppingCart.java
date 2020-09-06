package mate.academy.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCart {
    private Long id;
    private Long userId;
    private List<Product> products;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
    }
}
