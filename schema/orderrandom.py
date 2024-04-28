# Import Statements
import random
import math
import datetime

# Function to truncate our double into a 2 decimal number
def truncate_decimal(number):
    decimals=2
    factor = 10 ** decimals
    return math.trunc(number * factor) / factor

# Function to create a random datetime variable for our SQL Query
def random_time():
    hour = random.randint(0, 23)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    return datetime.time(hour, minute, second)

def main():
    prices = [5.59, 8.79, 5.49, 6.99, 6.79, 7.29, 6.29, 6.99, 5.79, 3.49, 3.99, 3.99, 3.99, 3.99,
               1.50, 1.30, 1.50, 8.99, 2.25, 2.45, 2.29, 3.65, 1.50, 1.50, 1.50, 1.50, .69, .69, .69, .69, .69, .69]
    totalIncome = 0
    with open('orders.sql', 'w') as file, open('orderProducts.sql','w') as file2:
        
        # Write the CREATE TABLE statement to the file
        file.write("CREATE TABLE orders (\n")
        file.write("    order_id INT PRIMARY KEY,\n")
        file.write("    price DECIMAL,\n")
        file.write("    order_datetime TIMESTAMP,\n")  # new TIMESTAMP variable
        file.write("    order_status VARCHAR(10)\n")  # new order status variable
        file.write(");\n\n")

        # Now write the INSERT INTO lines
        file.write("INSERT INTO orders (order_id, price, order_datetime, order_status) \n")
        file.write("VALUES \n")

        # Write the CREATE TABLE statement for order_products to the file2
        file2.write("CREATE TABLE order_products (\n")
        file2.write("    order_product_id INT PRIMARY KEY,\n")
        file2.write("    order_id INT,\n")
        file2.write("    product_id INT,\n")
        file2.write("    quantity INT,\n")
        file2.write("    FOREIGN KEY (order_id) REFERENCES orders(order_id),\n")
        file2.write("    FOREIGN KEY (product_id) REFERENCES products(product_id)\n")
        file2.write(");\n\n")

        #Now write the INSERT INTO lines
        file2.write("INSERT INTO order_products (order_product_id, order_id, product_id, quantity) \n")
        file2.write("VALUES \n")

        orderNUM = 1
        order_product_id = 1
        for year in [2023, 2024]:
            for month in range(1, 5):
                for day in range(1, 32): #Assume 31 days in each month for simplicity
                    # Check our datetime if it is a valid day
                    try:
                        date = datetime.date(year, month, day)
                    except ValueError:
                        continue # Skip to the next iteration if we get an error for our datetime we made

                    ordersPerDay = random.randint(75, 175) 
                    # Special dates, 2 times a year
                    if date == datetime.date(year, 7, 1) or date == datetime.date(year, 12, 1):  
                        ordersPerDay = random.randint(400,500)
                    
                    for k in range(ordersPerDay):
                        # make a random time using the function we made
                        time = random_time()

                        
                        numItems = random.randint(1,10)
                        orderPrice = 0

                        datetime_string = datetime.datetime.combine(date, time).strftime('%Y-%m-%d %H:%M:%S')

                        # generate the random order status
                        status = "completed" if random.random() > 0.2 else "canceled"  # 20% chance to be canceled

                        for j in range(numItems):
                            productId = random.randint(0,22)
                            orderPrice += prices[productId]
                            quantity = 1

                            file2.write("("+ str(order_product_id) + ", " + str(orderNUM) + ", " + str(productId+1)+ ", " + str(quantity) +"),\n")
                            order_product_id +=1

                        # Write to our orders.sql file for order number, order price, and the datetime
                        file.write(f"({orderNUM},  {truncate_decimal(orderPrice)}, '{datetime_string}', '{status}'),\n")        

                        totalIncome += orderPrice
                        orderNUM = orderNUM+1

        # Remove the trailing comma before the semicolon for file
        file.seek(file.tell() - 2, 0)
        file.truncate()

        # Remove the trailing comma before the semicolon for file2
        file2.seek(file2.tell() - 2, 0)
        file2.truncate()
        
        file.write(";")
        file2.write(";")
        print(totalIncome)
        




main()
