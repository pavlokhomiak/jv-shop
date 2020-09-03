package mate.academy.model;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCart {
    private Long id;
    private Long userId;
    private List<Product> products;
}
