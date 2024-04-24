package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity(name="Orders")
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders implements Serializable{
    @Id
    @Column(name = "order_id")
    private Long id;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "order_datetime")
    Date date;

    @Column(name = "order_status")
    String status;
    
    
}
