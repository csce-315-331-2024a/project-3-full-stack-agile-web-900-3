fetch('/manager')
  .then(response => response.json())
  .then(data => {
    const tableBody = document.getElementById('manager-data');

    data.forEach(item => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${item.product_id}</td>
        <td>${item.productname}</td>
        <td>${item.price}</td>
      `;
      tableBody.appendChild(row);
    });
  })
  .catch(error => console.error('Error fetching data:', error));
