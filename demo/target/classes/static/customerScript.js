function loadMenuItems(category) {
  fetch(`/api/menu/${category}`)
    .then(response => response.json())
    .then(data => {
      const menuItems = document.getElementById('menu-items');
      menuItems.innerHTML = '';
      data.forEach(item => {
        const itemDiv = document.createElement('div');
        itemDiv.className = 'menu-item';
        const img = document.createElement('img');
        img.src = `/images/${category}/${item.productname}.png`;
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
        orderButton.onclick = function () { addToOrder(item.product_id, item.price, item.productname); };
        detailsDiv.appendChild(itemName);
        detailsDiv.appendChild(itemPrice);
        detailsDiv.appendChild(orderButton);
        itemDiv.appendChild(img);
        itemDiv.appendChild(detailsDiv);
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
    listItem.innerHTML = `${productName} - $${price.toFixed(2)} <div class="quantity-controls"><button class="minus-button">-</button><span class="quantity-display">1</span><button class="plus-button">+</button></div>`;
    orderSummary.appendChild(listItem);
    listItem.querySelector('.minus-button').addEventListener('click', () => adjustQuantity(productId, price, -1));
    listItem.querySelector('.plus-button').addEventListener('click', () => adjustQuantity(productId, price, 1));
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
  loadMenuItems('burger');
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
