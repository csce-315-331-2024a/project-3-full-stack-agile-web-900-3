package projectone.demo.model;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * @author Quinn Bromley
 */
/**
 * Entity representing the order_products table which serves as a junction table
 * between orders and products in the database.
 * This entity links orders with the products they contain, including quantities of each product.
 */
@Entity(name = "OrderProducts")
@Table(name = "order_products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProducts {
     /**
     * Unique identifier for an entry in the order_products table.
     * This ID is unique across the table and serves as the primary key.
     */
    @Id
    private Long order_product_id;
        /**
     * The identifier for the order. This ID links to the Orders table.
     */
    private Long order_id;
     /**
     * The identifier for the product. This ID links to the Products table.
     */
    private Long product_id;
    /**
     * The quantity of the product included in the order.
     */
    private Long quantity;
}

// <div class = top >
// <span th:text = "'Order ID: ' +${order.order_id}"></span>
// <span th:text = "'Order Date: ' + ${order.orderDatetime}"></span>
// <span>
//   <span>Order Price: </span>
// <input name = "price" id = styledInput th:value = "${order.price}">
// </span>
// <span id  = statusUpdate>
// <div class = modify-items   >
//   <input  name = Orderid th:value = ${order.order_id} display = hidden >
//   <select name = status th:fragment = "Status-update">
//     <option value = "" th:text = "${order.status}" disabled selected hidden></option>
    
//     <option value = "processing">Processing</option>
//     <option value = "completed">Completed</option>
//     <option value = "canceled">Canceled</option>
//   </select>
// <button>Modify Status</button>
// </div>
// </span>
// </div>
// <div class = bottom>
// <div class = items> Purchased:
// <span th:each = "junc : ${junction}" th:if ="${junc.order_id} == ${order.order_id}">
  
//     <span class = "products-name"   th:each ="product:${products}" th:if="${product.product_id == junc.product_id}" th:text = "${product.productname}"></span>
  
// </span>
// </div>
// <div class = modify>
// <div class = modify-items >
//   <span>Add:</span>
//   <select name = add>
//     <option value = "">don't add</option>
//     <div th:each = "product :${products}" >
//       <option th:value = ${product.product_id} th:text = "${product.productname}" ></option>
//     </div>
//   </select>
 
// </div>

// <div calss = modify-items >
//   <span>Remove: </span>
//   <select name = remove>
//     <option value = "">don't remove</option>
//     <div th:each = "junc : ${junction}" th:if ="${junc.order_id} == ${order.order_id}">
//         <option th:each ="product:${products}" th:if="${product.product_id == junc.product_id}" th:text = "${product.productname}" th:value = "${junc.order_product_id}">

//         </option>
//     </div>
//   </select>
 
// </div>
// </div>

// </div>
