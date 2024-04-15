fetch('/manager')
  .then(response => response.json())
  .then(data => {
    const select = document.getElementById('manager-data');

    data.forEach(item => {
      const row = document.createElement('option');
      row.innerHTML = `
       
        <p>${item.productname}</p>
        
      `;
      select.appendChild(row);
    });
  })
  .catch(error => console.error('Error fetching data:', error));
