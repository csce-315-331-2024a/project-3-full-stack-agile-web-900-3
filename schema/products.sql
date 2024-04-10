CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    product_type VARCHAR(255) NOT NULL
);

-- Inserting data into products table
INSERT INTO products (product_id, ProductName, price, product_type)
VALUES
    (1, 'Revs Burger', 5.59, 'burger'),
    (2, 'Double Stack Cheeseburger' ,8.79, 'burger'),
    (3, 'Classic Burger', 5.49, 'burger'),
    (4, 'Bacon Cheeseburger', 6.99, 'burger'),
    (5, 'Three Tender Basket', 6.79, 'basket'),
    (6, 'Four Steak Finger Basket', 7.29, 'basket'),
    (7, 'Gig Em Patty Melt', 6.29, 'sandwich'),
    (8, 'Howdy Spicy Ranch Chicken Strip Sandwich', 6.99, 'sandwich'),
    (9, 'Classic Crispy or Grilled Chicken Tender Sandwich', 5.79, 'sandwich'),
    (10, 'Grilled Cheese', 3.49, 'sandwich'),
    (11, 'Chocolate Shake', 3.99, 'sweet'),
    (12, 'Vanilla Shake', 3.99, 'sweet'),
    (13, 'Strawberry Shake', 3.99, 'sweet'),
    (14, 'Cappuccino Shake', 3.99, 'sweet'),
    (15, 'Ice Cream', 1.50, 'sweet'),
    (16, 'Chocolate Chip Cookie', 1.30, 'sweet'),
    (17, 'Brownie', 1.50, 'sweet'),
    (18, 'Salad Bar', 8.99, 'salad'),
    (19, 'Small Drink', 2.25, 'beverage'),
    (20, 'Large Drink', 2.45, 'beverage'),
    (21, 'Coffee', 2.29, 'beverage'),
    (22, 'Cold Brew', 3.65, 'beverage'),
    (23, 'Fries', 1.50, 'side'),
    (24, 'Tater Tots', 1.50, 'side'),
    (25, 'Onion rings', 1.50, 'side'),
    (26, 'Chips', 1.50, 'side'),
    (27, 'Gig Em Sauce', .69, 'sauce'),
    (28, 'Buffalo', .69, 'sauce'),
    (29, 'BBQ Sauce', .69, 'sauce'),
    (30, 'Honey Mustard', .69, 'sauce'),
    (31, 'Spicy Ranch', .69, 'sauce'),
    (32, 'Ranch', .69, 'sauce');
