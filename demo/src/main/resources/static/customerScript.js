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
          addToOrder(item.product_id, item.price, item.productname); 
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
function addToOrder(productId, price, productName) {
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
    editButton.onclick = function() {
        window.location.href = 'editItem.html';  // Redirects to the edit page
    };

    contentDiv.appendChild(itemDetails);
    contentDiv.appendChild(editButton);

    listItem.appendChild(contentDiv);
    orderSummary.appendChild(listItem);

    minusButton.addEventListener('click', () => adjustQuantity(productId, price, -1));
    plusButton.addEventListener('click', () => adjustQuantity(productId, price, 1));
  } else {
    adjustQuantity(productId, price, 1);
  }
  updateTotal();
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
  updateTotal();
}

function updateTotal() {
  const orderSummary = document.getElementById('order-summary');
  const listItems = orderSummary.querySelectorAll('li');
  orderTotal = 0;
  listItems.forEach(item => {
    const price = parseFloat(item.textContent.match(/\$([\d\.]+)/)[1]);
    const quantity = parseInt(item.querySelector('.quantity-display').textContent);
    orderTotal += price * quantity;
  });
  const totalPriceElement = document.getElementById('total-price');
  totalPriceElement.textContent = `$${orderTotal.toFixed(2)}`;
}

document.getElementById('cancel-order').addEventListener('click', function () {
  document.getElementById('order-summary').innerHTML = '';
  updateTotal();
});

document.getElementById('confirm-order').addEventListener('click', function () {
  if (orderTotal > 0 && confirm('Do you want to proceed to checkout?')) {
    localStorage.setItem('orderTotal', orderTotal.toFixed(2));
    localStorage.setItem('orderSummary', document.getElementById('order-summary').innerHTML);
    localStorage.setItem('returningUser', true);
    window.location.href = 'checkout.html';
  } else {
    alert('Please add items to your order.');
  }
});

window.addEventListener('beforeunload', function (event) {
  if (!localStorage.getItem('returningUser')) {
    localStorage.clear();
  }
});

document.addEventListener('DOMContentLoaded', () => {
  const returningUser = localStorage.getItem('returningUser');
  if (returningUser) {
    const savedTotal = localStorage.getItem('orderTotal');
    const savedSummary = localStorage.getItem('orderSummary');
    if (savedTotal && savedSummary) {
      document.getElementById('total-price').textContent = `$${savedTotal}`;
      document.getElementById('order-summary').innerHTML = savedSummary;
      orderTotal = parseFloat(savedTotal);
    }
    localStorage.setItem('returningUser', false);
    localStorage.clear();
  } else {
    loadMenuItems('burger');
    localStorage.clear();
  }
});

function toggleEditSection(show) {
  document.getElementById('edit-section').style.display = show ? 'block' : 'none';
  document.getElementById('menu-items').style.display = show ? 'none' : 'block';
  document.getElementById('order-tracker').style.display = show ? 'none' : 'block'; // Assuming this is your order summary
}

document.querySelectorAll('.edit-button').forEach(button => {
  button.onclick = () => toggleEditSection(true);
});
