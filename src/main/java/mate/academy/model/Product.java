package mate.academy.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    public Product(String name, double price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }
}
