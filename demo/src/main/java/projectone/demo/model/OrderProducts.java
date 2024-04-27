package projectone.demo.model;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "OrderProducts")
@Table(name = "order_products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProducts {
    @Id
    private Long order_product_id;

    private Long order_id;

    private long product_id;

    private long quantity;
}
