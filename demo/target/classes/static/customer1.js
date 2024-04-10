fetch('/customer')
  .then(response => response.json())
  .then(data => {
    const menuItems = document.getElementById('menu-items'); // Updated to target the correct element

    data.forEach(item => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${item.product_id}</td>
        <td>${item.productname}</td>
        <td>$${item.price.toFixed(2)}</td>
        <td>$${item.product_type}</td>
        <td><button onclick="addToOrder(${item.product_id}, ${item.price})">Add to Order</button></td>
      `;
      menuItems.appendChild(row);
    });
  })
  .catch(error => console.error('Error fetching data:', error));

  let orderTotal = 0; // This will hold the running total

  function addToOrder(productId, price) {
    // Increment the order total by the price of the added item
    orderTotal += price;
  
    // Update the order total display
    const totalPriceElement = document.getElementById('total-price');
    totalPriceElement.textContent = `$${orderTotal.toFixed(2)}`;
  
    // You can add additional logic to display the item in the order summary, if desired
    console.log(`Added product with ID ${productId} to order. Total is now $${orderTotal.toFixed(2)}`);
  }