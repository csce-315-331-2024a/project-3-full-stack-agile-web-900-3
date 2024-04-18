var categoryPerCol = [[${category_per_col}]]; // Retrieve category_per_col value from Thymeleaf
var categories = [[${categories}]]; // Retrieve categories list from Thymeleaf

// JavaScript code to dynamically render categories into columns
window.addEventListener('DOMContentLoaded', function () {
    // Get all the columns
    var columns = document.querySelectorAll('.col');

    // Loop through each column

    columns.forEach(function (column, index) {
        // Calculate start and end index for categories
        var startIndex = index * categoryPerCol;
        if(index === 2){
            endIndex = categories.length;
        }
        else{
            endIndex = Math.min((index + 1) * categoryPerCol, categories.length);
        }

        // Get categories for current column
        var categoriesInColumn = categories.slice(startIndex, endIndex);

        // Render categories in current column
        categoriesInColumn.forEach(function (category) {
            // Create HTML element for category and append it to column
            var div = document.createElement('div');
            div.setAttribute('th:insert', '~{fragments/categories :: ' category);
            column.appendChild(div);
        });
    });
});