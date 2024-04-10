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
        <td><button onclick="addToOrder(${item.product_id}, ${item.price}, '${item.productname.replace(/'/g, "\\'")}')">Add to Order</button></td>
      `;
      menuItems.appendChild(row);
    });
  })
  .catch(error => console.error('Error fetching data:', error));

  let orderTotal = 0; // This will hold the running total

  // When clicking the addToOrder button, do this
  function addToOrder(productId, price, productName) {
    // Increment the order total by the price of the added item
    orderTotal += price * 1.08;
    
    // Update the order total display
    const totalPriceElement = document.getElementById('total-price');
    totalPriceElement.textContent = `$${orderTotal.toFixed(2)}`;
  
    // Add the item to the order summary list
    const orderSummary = document.getElementById('order-summary');
    const listItem = document.createElement('li');
    listItem.textContent = `${productName} - $${price.toFixed(2)}`;
    orderSummary.appendChild(listItem);

    // Save the current order to localStorage
    localStorage.setItem('orderTotal', orderTotal.toFixed(2));
    localStorage.setItem('orderSummary', orderSummary.innerHTML);
  
    console.log(`Added product with ID ${productId} to order. Total is now $${orderTotal.toFixed(2)}`);
  }

  // When clicking the cancel order button, do this
  document.getElementById('cancel-order').addEventListener('click', function() {
    // Clear the order summary list
    document.getElementById('order-summary').innerHTML = '';
    // Reset the total price display
    orderTotal = 0;
    document.getElementById('total-price').textContent = `$${orderTotal.toFixed(2)}`;

    // Clear the saved order from localStorage
    localStorage.removeItem('orderTotal');
    localStorage.removeItem('orderSummary');
  }); 
  
  // When clicking the confirm order button, do this
  document.getElementById('confirm-order').addEventListener('click', function() {
    // Confirm checkout process
    if (orderTotal > 0 && confirm('Do you want to proceed to checkout?')) {
      // If confirmed, redirect to the checkout page
      window.location.href = 'checkout.html';; // Update with the actual path to your checkout page
    } else {
      alert('Please add items to your order.');
    }
  });

  // As soon as the DOM is fully loaded, check for any stored orders
  document.addEventListener('DOMContentLoaded', (event) => {
    const savedTotal = localStorage.getItem('orderTotal');
    const savedSummary = localStorage.getItem('orderSummary');

    // If there's a saved order, restore it
    if (savedTotal && savedSummary) {
      const totalPriceElement = document.getElementById('total-price');
      const orderSummary = document.getElementById('order-summary');

      totalPriceElement.textContent = `$${savedTotal}`;
      orderSummary.innerHTML = savedSummary;
      orderTotal = parseFloat(savedTotal); // Make sure to parse it as a number
    }
  });

  /*
  // Assume that 'data' is an array that holds all products fetched from the server
  let allProducts = [];

  fetch('/customer')
    .then(response => response.json())
    .then(data => {
      allProducts = data;
      displayProducts('burger'); // Default display or based on some other logic
    })
    .catch(error => console.error('Error fetching data:', error));

  function displayProducts(type) {
    const menuItems = document.getElementById('menu-items');
    menuItems.innerHTML = ''; // Clear current menu items

    allProducts.forEach(item => {
      if(item.product_type.toLowerCase() === type.toLowerCase()) {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${item.product_id}</td>
          <td>${item.productname}</td>
          <td>$${item.price.toFixed(2)}</td>
          <td>${item.product_type}</td>
          <td><button onclick="addToOrder(${item.product_id}, ${item.price}, '${item.productname.replace(/'/g, "\\'")}')">Add to Order</button></td>
          `;
          menuItems.appendChild(row);
        }
      });
    }

    // Add event listeners to each nav-button
    document.querySelectorAll('.nav-button').forEach(button => {
      button.addEventListener('click', function() {
        const type = this.id; // The id of the button corresponds to the product_type
        displayProducts(type);
      });
    });
    */