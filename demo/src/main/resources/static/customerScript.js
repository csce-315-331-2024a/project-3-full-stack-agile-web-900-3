// Main function for loading the different menu items depending on the category being displayed
var totalPrice = document.getElementById('total-price');

function loadMenuItems(category) {
  fetch(`/customer/api/menu/${category}`)
    .then(response => response.json())
    .then(data => {
      const menuItems = document.getElementById('menu-items');
      menuItems.innerHTML = '';
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

        // Add combo checkbox for specific categories
        if (['burger', 'sandwich'].includes(category)) {
          const comboCheckbox = document.createElement('input');
          comboCheckbox.type = 'checkbox';
          comboCheckbox.className = 'combo-checkbox';
          comboCheckbox.id = `combo-${item.product_id}`;

          const comboLabel = document.createElement('label');
          comboLabel.htmlFor = `combo-${item.product_id}`;
          comboLabel.className = 'combo-label';
          comboLabel.textContent = ' Make it a Combo (+$1.90)';

          detailsDiv.appendChild(comboCheckbox);
          detailsDiv.appendChild(comboLabel);
        }
        else if (['basket'].includes(category)) {
          const comboCheckbox = document.createElement('input');
          comboCheckbox.type = 'checkbox';
          comboCheckbox.className = 'combo-checkbox';
          comboCheckbox.id = `combo-${item.product_id}`;

          const comboLabel = document.createElement('label');
          comboLabel.htmlFor = `combo-${item.product_id}`;
          comboLabel.className = 'combo-label';
          comboLabel.textContent = ' Make it a Combo (+$1.10)';

          detailsDiv.appendChild(comboCheckbox);
          detailsDiv.appendChild(comboLabel);
        }

        const orderButton = document.createElement('button');
        orderButton.className = 'order-button';
        orderButton.textContent = 'Add to Order';
        orderButton.onclick = function() { 
          addToOrder(item.product_id, item.price, item.productname, item.quantity, category); 
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
let uniqueId = 0;  // Global counter for unique items in the order summary

function addToOrder(productId, price, productName, quantity = 1, category) {
  const orderSummary = document.getElementById('order-summary');
  const comboCheckbox = document.getElementById(`combo-${productId}`);
  let isCombo = comboCheckbox && comboCheckbox.checked;
  let comboPrice = isCombo ? ((category === 'burger' || category === 'sandwich') ? 1.90 : 1.10) : 0;
  let totalItemPrice = price + comboPrice;  // Adjusted price including combo

  // Modify the product name if it is a combo
  let displayName = isCombo ? `${productName} Combo` : productName;

  // Find an existing list item that matches the product ID and combo state
  let listItem = Array.from(orderSummary.children).find(
    item => item.getAttribute('data-product-id') === productId && 
            item.getAttribute('data-combo') === String(isCombo)
  );

  console.log("Adding to order", { productId, price, productName, quantity, category });

  // Inside addToOrder, log to check if elements are being found and added correctly
  if (!listItem) {
      console.log("Creating new list item for", productName);
  } else {
      console.log("Item already exists, updating quantity for", productName);
  }

  if (!listItem) {
    ++uniqueId;
    listItem = document.createElement('li');
    listItem.setAttribute('data-product-id', productId);
    listItem.setAttribute('data-product-name', productName); // This should be the initial product name without 'Combo'
    listItem.setAttribute('data-price', price.toString()); // This should be the initial price without combo price
    listItem.setAttribute('data-category', category);
    listItem.setAttribute('data-is-combo', isCombo); // This will be either 'true' or 'false'
    listItem.setAttribute('data-unique-id', uniqueId.toString());
    listItem.className = 'order-item';

    const contentDiv = document.createElement('div');
    contentDiv.className = 'order-item-content';

    const itemDetails = document.createElement('div');
    itemDetails.className = 'item-details';

    const title = document.createElement('span');
    title.textContent = `${displayName} - $${totalItemPrice.toFixed(2)}`;
    title.className = 'order-item-title';

    const quantityControls = document.createElement('div');
    quantityControls.className = 'quantity-controls';

    const quantityDisplay = document.createElement('span');
    quantityDisplay.className = 'quantity-display';
    quantityDisplay.textContent = quantity.toString();

    const editButton = document.createElement('button');
    editButton.className = 'edit-icon-button';
    editButton.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="24" height="24"><!-- Path omitted for brevity --><path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z" fill="#007BFF"/></svg>`;

    // When creating buttons, bind the click event with the current uniqueId
    const minusButton = document.createElement('button');
    minusButton.className = 'plus-button';
    minusButton.textContent = '+';
    minusButton.addEventListener('click', (function(uniqueIdCopy) {
      return function() {
        adjustQuantity(uniqueIdCopy, -1);
      };
    })(uniqueId));


    const plusButton = document.createElement('button');
    plusButton.className = 'plus-button';
    plusButton.textContent = '+';
    plusButton.addEventListener('click', (function(uniqueIdCopy) {
      return function() {
        adjustQuantity(uniqueIdCopy, 1);
      };
    })(uniqueId));


    quantityControls.appendChild(minusButton);
    quantityControls.appendChild(quantityDisplay);
    quantityControls.appendChild(plusButton);

    itemDetails.appendChild(title);
    itemDetails.appendChild(quantityControls);

    contentDiv.appendChild(itemDetails);
    contentDiv.appendChild(editButton);

    listItem.appendChild(contentDiv);
    orderSummary.appendChild(listItem);
  } else {
    // If the item exists, find its uniqueId and use it to adjust the quantity
    const existingUniqueId = listItem.getAttribute('data-unique-id');
    adjustQuantity(existingUniqueId, quantity);
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
    const uniqueId = item.getAttribute('data-unique-id');
    const productId = item.getAttribute('data-product-id');

    // Extract the price and product name from the order-item-title span
    const itemTitle = item.querySelector('.order-item-title').textContent;
    const titleMatch = itemTitle.match(/^(.*) - \$([\d\.]+)$/); // Assuming the format is always "Name - $Price"
    const productName = titleMatch ? titleMatch[1] : 'Unknown Product';
    const price = titleMatch ? parseFloat(titleMatch[2]) : 0;

    // Extract the quantity from the quantity-display span
    const quantityText = item.querySelector('.quantity-display').textContent;
    const quantity = quantityText ? parseInt(quantityText) : 0;

    // Use the stored attribute instead of checking the checkbox state
    const isCombo = item.getAttribute('data-is-combo') === 'true';

    orderDetails.push({ uniqueId, productId, price, productName, quantity, isCombo });
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


function adjustQuantity(uniqueId, change) {
  console.log("Adjusting Quantity", { uniqueId, change });

  const orderSummary = document.getElementById('order-summary');
  const listItem = orderSummary.querySelector(`[data-unique-id="${uniqueId}"]`);

  if (!listItem) {
    console.error("No list item found for this uniqueId:", uniqueId);
    return;
  }

  // Retrieve stored product data
  const productPrice = parseFloat(listItem.getAttribute('data-price'));
  const productName = listItem.getAttribute('data-product-name');
  const category = listItem.getAttribute('data-category'); // Ensure this is being set when creating the list item
  const isCombo = listItem.getAttribute('data-is-combo') === 'true';

  const quantityDisplay = listItem.querySelector('.quantity-display');
  let currentQuantity = parseInt(quantityDisplay.textContent);
  let newQuantity = Math.max(0, currentQuantity + change);

  console.log("Found list item", listItem);

  // Update the list item display
  if (newQuantity > 0) {
    quantityDisplay.textContent = newQuantity;
    let displayName = isCombo ? `${productName} Combo` : productName;
    let comboPrice = isCombo ? ((category === 'burger' || category === 'sandwich') ? 1.90 : 1.10) : 0;
    let totalNewPrice = productPrice + comboPrice;
    listItem.querySelector('.order-item-title').textContent = `${displayName} - $${totalNewPrice.toFixed(2)}`;
    console.log("Updated display name", displayName);

  } else {
    orderSummary.removeChild(listItem);
    console.log("Removed item due to zero quantity", { uniqueId, currentQuantity, newQuantity });
  }

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

  // Apply state tax
  const stateTaxRate = 1.08; // 8% state tax
  orderTotal *= stateTaxRate;

  const totalPriceElement = document.getElementById('total-price');
  totalPriceElement.value = orderTotal.toFixed(2);
  document.getElementById('display-total').textContent = `$${orderTotal.toFixed(2)}`;
  console.log('Updated order total:', orderTotal.toFixed(2));
}


function customerEditItem(productId) {
  fetch(`/api/products/${productId}/ingredients`)
    .then(response => response.json())
    .then(ingredients => {
      const editSection = document.getElementById('edit-section');
      const ingredientList = document.getElementById('ingredient-list');
      ingredientList.innerHTML = '';

      ingredients.forEach(ingredient => {
        const ingredientItem = document.createElement('li');
        ingredientItem.textContent = ingredient;
        ingredientList.appendChild(ingredientItem);
      });

      editSection.style.display = 'block';
    })
    .catch(error => {
      console.error('Error fetching ingredients:', error);
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
    localStorage.setItem('returningUser', 'true');
    saveOrderDetails();
  } else {
    alert('Please add items to your order.');
  }
});


// Call this function when the page loads
document.addEventListener('DOMContentLoaded', () => {
  console.log("DOM fully loaded and parsed");
  const isReturningUser = localStorage.getItem('returningUser');
  
  console.log("Is Returning User:", isReturningUser);
  
  if (isReturningUser) {
    console.log("Loading Order Details from Local Storage");
    loadOrderDetails();
    loadMenuItems('burger');
  } else {
    console.log("Clearing Local Storage");
    localStorage.clear();
    loadMenuItems('burger');
  }
  
  console.log("Removing 'returningUser' from Local Storage");
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
      window.location.href = `customer/edit?product_id=${productId}`;
      break; // Break the loop after finding your button
    }
    targetElement = targetElement.parentElement; // Move up the DOM
  }
});

