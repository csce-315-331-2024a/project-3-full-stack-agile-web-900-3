package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name="Orders")
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders implements Serializable{
    @Id
    private Long order_id;

    private BigDecimal price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_datetime")
    private LocalDateTime orderDatetime;
    @Column(name = "order_status")
    private String status;

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", price=" + price +
                ", orderDatetime=" + orderDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}
