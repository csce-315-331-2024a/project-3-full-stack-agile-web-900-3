function loadMenuItems(category) {
  fetch(`/api/menu/${category}`)
    .then(response => response.json())
    .then(data => {
      const menuItems = document.getElementById('menu-items');
      menuItems.innerHTML = '';  // Clear existing menu items
      data.forEach(item => {
        const itemDiv = document.createElement('div');
        itemDiv.className = 'menu-item';
        
        const img = document.createElement('img');
        img.src = `/images/${category}/${item.productname}.png`;
        img.alt = item.productname; 

        const detailsDiv = document.createElement('div');
        detailsDiv.className = 'menu-item-details';
        
        const itemName = document.createElement('h3');
        itemName.textContent = item.productname;
        
        const itemPrice = document.createElement('p');
        itemPrice.className = 'menu-item-price';
        itemPrice.textContent = `$${item.price.toFixed(2)}`;

        const orderButton = document.createElement('button');
        orderButton.className = 'order-button';
        orderButton.textContent = 'Add to Order';
        orderButton.onclick = function() { 
          addToOrder(item.product_id, item.price, item.productname, item.quantity); 
        };

        // Append elements to detailsDiv
        detailsDiv.appendChild(itemName);
        detailsDiv.appendChild(itemPrice);
        detailsDiv.appendChild(orderButton);
        
        // Append image and detailsDiv to itemDiv
        itemDiv.appendChild(img);
        itemDiv.appendChild(detailsDiv);

        // Append itemDiv to menuItems container
        menuItems.appendChild(itemDiv);
      });
    })
    .catch(error => console.error('Error loading items:', error));
}



let orderTotal = 0;
function addToOrder(productId, price, productName, quantity = 1) {
  const orderSummary = document.getElementById('order-summary');
  let listItem = orderSummary.querySelector(`li[data-product-id="${productId}"]`);

  if (!listItem) {
    listItem = document.createElement('li');
    listItem.setAttribute('data-product-id', productId);
    listItem.className = 'order-item';

    const contentDiv = document.createElement('div');
    contentDiv.className = 'order-item-content';

    const itemDetails = document.createElement('div');
    itemDetails.className = 'item-details';

    const title = document.createElement('span');
    title.textContent = `${productName} - $${price.toFixed(2)}`;
    title.className = 'order-item-title';

    const quantityControls = document.createElement('div');
    quantityControls.className = 'quantity-controls';
    const minusButton = document.createElement('button');
    minusButton.className = 'minus-button';
    minusButton.textContent = '-';
    const quantityDisplay = document.createElement('span');
    quantityDisplay.className = 'quantity-display';
    quantityDisplay.textContent = '1';
    const plusButton = document.createElement('button');
    plusButton.className = 'plus-button';
    plusButton.textContent = '+';

    quantityControls.appendChild(minusButton);
    quantityControls.appendChild(quantityDisplay);
    quantityControls.appendChild(plusButton);

    itemDetails.appendChild(title);
    itemDetails.appendChild(quantityControls);

    const editButton = document.createElement('button');
    editButton.className = 'edit-icon-button';
    editButton.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="24" height="24"><!-- Path omitted for brevity --><path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z" fill="#007BFF"/></svg>`;

    contentDiv.appendChild(itemDetails);
    contentDiv.appendChild(editButton);

    listItem.appendChild(contentDiv);
    orderSummary.appendChild(listItem);

    minusButton.addEventListener('click', () => adjustQuantity(productId, price, -1));
    plusButton.addEventListener('click', () => adjustQuantity(productId, price, 1));

    // Set the initial quantity display
    quantityDisplay.textContent = quantity.toString();

    saveOrderDetails();
  } else {
    adjustQuantity(productId, price, 1);
  }
  updateTotal();
}

// Save the current state of the order to localStorage
function saveOrderDetails() {
  const orderSummary = document.getElementById('order-summary');
  const listItems = orderSummary.querySelectorAll('li');
  let orderDetails = [];

  listItems.forEach(item => {
    // Extract the productId from the data attribute
    const productId = item.getAttribute('data-product-id');

    // Extract the price and product name from the order-item-title span
    const itemTitle = item.querySelector('.order-item-title').textContent;
    const titleMatch = itemTitle.match(/^(.*) - \$([\d\.]+)$/); // Assuming the format is always "Name - $Price"
    const productName = titleMatch ? titleMatch[1] : 'Unknown Product';
    const price = titleMatch ? parseFloat(titleMatch[2]) : 0;

    // Extract the quantity from the quantity-display span
    const quantityText = item.querySelector('.quantity-display').textContent;
    const quantity = quantityText ? parseInt(quantityText) : 0;

    if (productId && productName && !isNaN(price) && !isNaN(quantity)) {
      orderDetails.push({ productId, price, productName, quantity });
    }
  });

  localStorage.setItem('orderDetails', JSON.stringify(orderDetails));
  console.log('Saved order details:', orderDetails); // Debugging log
}

// Load and reconstruct the order from localStorage
function loadOrderDetails() {
  const savedOrderDetails = localStorage.getItem('orderDetails');
  if (savedOrderDetails) {
    const orderDetails = JSON.parse(savedOrderDetails);
    console.log('Loaded order details (parsed):', orderDetails); // Debugging log
  
    orderDetails.forEach(detail => {
      // Make sure the keys match what you expect. If they don't, it will be undefined.
      addToOrder(detail.productId, detail.price, detail.productName, detail.quantity);
    });

    updateTotal();
  } else {
    console.log('No order details found in localStorage.');
  }
}


function adjustQuantity(productId, price, change) {
  const listItem = document.getElementById('order-summary').querySelector(`li[data-product-id="${productId}"]`);
  const quantitySpan = listItem.querySelector('.quantity-display');
  let quantity = parseInt(quantitySpan.textContent);
  quantity = Math.max(0, quantity + change);
  if (quantity > 0) {
    quantitySpan.textContent = quantity;
  } else {
    listItem.remove();
  }

  console.log('Adjusted quantity for product ID:', productId, 'New quantity:', quantity); // Debugging log
  updateTotal();
  saveOrderDetails();
}

function updateTotal() {
  const orderSummary = document.getElementById('order-summary');
  const listItems = orderSummary.querySelectorAll('li');
  orderTotal = 0;

  listItems.forEach(item => {
    // Extract the price and product name from the order-item-title span
    const itemTitle = item.querySelector('.order-item-title').textContent;
    const titleMatch = itemTitle.match(/^(.*) - \$([\d\.]+)$/); // Assuming the format is always "Name - $Price"
    const price = titleMatch ? parseFloat(titleMatch[2]) : 0;

    // Extract the quantity from the quantity-display span
    const quantityText = item.querySelector('.quantity-display').textContent;
    const quantity = quantityText ? parseInt(quantityText) : 0;
    orderTotal += price * quantity;
  });

  const totalPriceElement = document.getElementById('total-price');
  totalPriceElement.textContent = `$${orderTotal.toFixed(2)}`;
  console.log('Updated order total:', orderTotal.toFixed(2)); // Debugging log
}


function editItem(productId) {
  // Fetch ingredients for the specific product
  fetch(`/api/products/${productId}/ingredients`)
    .then(response => response.json())
    .then(ingredients => {
      const editSection = document.getElementById('edit-section');
      const ingredientList = document.getElementById('ingredient-list');

      // Clear previous list items if any
      ingredientList.innerHTML = '';

      ingredients.forEach(ingredient => {
        const ingredientItem = document.createElement('li');
        ingredientItem.textContent = ingredient;
        ingredientList.appendChild(ingredientItem);
      });

      // Show the edit-section since we have successfully fetched ingredients
      editSection.style.display = 'block';
    })
    .catch(error => {
      console.error('Error fetching ingredients:', error);
      // Optionally hide the edit-section if there is an error
      editSection.style.display = 'none';
    });
}



document.getElementById('cancel-order').addEventListener('click', function () {
  document.getElementById('order-summary').innerHTML = '';
  saveOrderDetails();
  updateTotal();
});

document.getElementById('confirm-order').addEventListener('click', function () {
  if (orderTotal > 0 && confirm('Do you want to proceed to checkout?')) {
    // Set the flag that the user is navigating to the confirmation screen
    localStorage.setItem('returningUser', 'true');
    
    // Save the current order to local storage
    saveOrderDetails();
    
    // Navigate to confirmation screen
    window.location.href = 'checkout.html';
  } else {
    alert('Please add items to your order.');
  }
});


// Call this function when the page loads
// Call this function when the page loads
document.addEventListener('DOMContentLoaded', () => {
  const isReturningUser = localStorage.getItem('returningUser');
  
  if (isReturningUser) {
    loadOrderDetails();
    loadMenuItems('burger');
  } else {
    localStorage.clear();
    loadMenuItems('burger');
  }
  
  // Always remove the flag on a fresh page load, if the user navigates away it will be set again
  localStorage.removeItem('returningUser');
});



function toggleEditSection(show) {
  document.getElementById('edit-section').style.display = show ? 'block' : 'none';
  document.getElementById('menu-items').style.display = show ? 'none' : 'block';
  document.getElementById('order-tracker').style.display = show ? 'none' : 'block'; // Assuming this is your order summary
}

document.addEventListener('click', function(event) {
  let targetElement = event.target; // Clicked element
  while (targetElement != null) {
    if (targetElement.classList.contains('edit-icon-button')) {
      // Set the flag that the user is navigating to the edit item screen
      localStorage.setItem('returningUser', 'true');
    
      // Save the current order to local storage
      saveOrderDetails();

      const productId = targetElement.closest('li').getAttribute('data-product-id');
      window.location.href = `editItem.html?product_id=${productId}`;
      break; // Break the loop after finding your button
    }
    targetElement = targetElement.parentElement; // Move up the DOM
  }
});

