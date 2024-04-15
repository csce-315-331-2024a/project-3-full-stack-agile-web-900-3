document.addEventListener('DOMContentLoaded', function() {
  loadMenuItems('burgers'); // Initially loads burgers on page load
});

function loadMenuItems(category) {
  fetch(`/api/menu/${category}`)
    .then(response => response.json())
    .then(data => {
      const menuItems = document.getElementById('menu-items');
      menuItems.innerHTML = '';
      data.forEach(item => {
        const itemDiv = document.createElement('div');
        itemDiv.className = 'menu-item';
        const detailsDiv = document.createElement('div');
        detailsDiv.className = 'menu-item-details';
        const itemName = document.createElement('h3');
        itemName.textContent = item.productname;
        const itemPrice = document.createElement('p');
        itemPrice.className = 'menu-item-price';
        itemPrice.textContent = `$${item.price.toFixed(2)}`;
        const orderButton = document.createElement('button');
        orderButton.className = 'order-button';
        orderButton.textContent = 'Order Now';
        orderButton.onclick = function() { addToOrder(item.product_id, item.price, item.productname); };
        detailsDiv.appendChild(itemName);
        detailsDiv.appendChild(itemPrice);
        detailsDiv.appendChild(orderButton);
        itemDiv.appendChild(detailsDiv);
        menuItems.appendChild(itemDiv);
      });
    })
    .catch(error => console.error('Error loading items:', error));
}

let orderTotal = 0;

function addToOrder(productId, price, productName) {
  orderTotal += price * 1.08;
  const totalPriceElement = document.getElementById('total-price');
  totalPriceElement.textContent = `$${orderTotal.toFixed(2)}`;
  const orderSummary = document.getElementById('order-summary');
  const listItem = document.createElement('li');
  listItem.textContent = `${productName} - $${price.toFixed(2)}`;
  orderSummary.appendChild(listItem);
  localStorage.setItem('orderTotal', orderTotal.toFixed(2));
  localStorage.setItem('orderSummary', orderSummary.innerHTML);
}

document.getElementById('cancel-order').addEventListener('click', function() {
  document.getElementById('order-summary').innerHTML = '';
  orderTotal = 0;
  document.getElementById('total-price').textContent = `$0.00`;
  localStorage.removeItem('orderTotal');
  localStorage.removeItem('orderSummary');
});

document.getElementById('confirm-order').addEventListener('click', function() {
  if (orderTotal > 0 && confirm('Do you want to proceed to checkout?')) {
    window.location.href = 'checkout.html';
  } else {
    alert('Please add items to your order.');
  }
});

document.addEventListener('DOMContentLoaded', () => {
  const savedTotal = localStorage.getItem('orderTotal');
  const savedSummary = localStorage.getItem('orderSummary');
  if (savedTotal && savedSummary) {
    document.getElementById('total-price').textContent = `$${savedTotal}`;
    document.getElementById('order-summary').innerHTML = savedSummary;
    orderTotal = parseFloat(savedTotal);
  }
});

fetch('/customer')
  .then(response => response.json())
  .then(data => {
    const menuItems = document.getElementById('menu-items');
    data.forEach(item => {
      const itemDiv = document.createElement('div');
      itemDiv.className = 'menu-item';
      const img = document.createElement('img');
      img.src = 'path_to_image/' + item.image;
      const detailsDiv = document.createElement('div');
      detailsDiv.className = 'menu-item-details';
      const itemName = document.createElement('h3');
      itemName.textContent = item.productname;
      const itemPrice = document.createElement('p');
      itemPrice.className = 'menu-item-price';
      itemPrice.textContent = `$${item.price.toFixed(2)}`;
      const orderButton = document.createElement('button');
      orderButton.className = 'order-button';
      orderButton.textContent = 'Order Now';
      orderButton.onclick = () => addToOrder(item.product_id, item.price, item.productname);
      detailsDiv.appendChild(itemName);
      detailsDiv.appendChild(itemPrice);
      detailsDiv.appendChild(orderButton);
      itemDiv.appendChild(img);
      itemDiv.appendChild(detailsDiv);
      menuItems.appendChild(itemDiv);
    });
  })
  .catch(error => console.error('Error fetching data:', error));
