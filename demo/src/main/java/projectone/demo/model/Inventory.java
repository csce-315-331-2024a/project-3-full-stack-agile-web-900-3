package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="Inventory")
@Table(name="inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long quantity;

    private String unit;

    private Long low_threshold;

    private String order_date;
}
