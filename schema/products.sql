
CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    product_type VARCHAR(255) NOT NULL
);

-- Inserting data into products table
INSERT INTO products (product_id, ProductName, price, product_type)
VALUES
    (1, 'Revs', 5.59, 'buger'),
    (2, 'DBL CHZ' ,8.79, 'buger'),
    (3, 'classic', 5.49, 'buger'),
    (4, 'Bacon', 6.99, 'buger'),
    (5, '3 Tender', 6.79, 'basket'),
    (6, 'Steak Finger', 7.29, 'basket'),
    (7, 'Patty Melt', 6.29, 'sandwich'),
    (8, 'Spicy Chkn Sndwch', 6.99, 'sandwich'),
    (9, 'Chkn tndr sndwch', 5.79, 'sandwich'),
    (10, 'Grilled Chz', 3.49, 'sandwich'),
    (11, 'Chocolate Shake', 3.99, 'sweet'),
    (12, 'Vanilla Shake', 3.99, 'sweet'),
    (13, 'Strawberry Shake', 3.99, 'sweet'),
    (14, 'Cappuccino Shake', 3.99, 'sweet'),
    (15, 'Ice Cream', 1.50, 'sweet'),
    (16, 'Choc chip cookie', 1.30, 'sweet'),
    (17, 'Brownie', 1.50, 'sweet'),
    (18, 'Salad Bar', 8.99, 'salad'),
    (19, 'sm Drink', 2.25, 'beverage'),
    (20, 'lg Drink', 2.45, 'beverage'),
    (21, 'Coffee', 2.29, 'beverage'),
    (22, 'Cold brew', 3.65, 'beverage'),
    (23, 'Fries', 1.50, 'side'),
    (24, 'Tater Tots', 1.50, 'side'),
    (25, 'Onion rings', 1.50, 'side'),
    (26, 'Chips', 1.50, 'side'),
    (27, 'Gig em', .69, 'sauce'),
    (28, 'Buffalo', .69, 'sauce'),
    (29, 'BBQ', .69, 'sauce'),
    (30, 'Honey must', .69, 'sauce'),
    (31, 'Spy Ranch', .69, 'sauce'),
    (32, 'Ranch', .69, 'sauce');
