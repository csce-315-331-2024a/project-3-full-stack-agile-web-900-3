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

    private Long product_id;

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
