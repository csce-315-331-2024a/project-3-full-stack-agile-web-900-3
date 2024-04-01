CREATE TABLE employee (
    EmployeeID int,
    EmployeeName varchar(255),
    Gender varchar(255),
    BirthMonth varchar(255),
    EmployeeRole varchar(255)
);

INSERT INTO employee (EmployeeID, EmployeeName, Gender, BirthMonth, EmployeeRole)

VALUES

(1234, 'Findlay Brand', 'Male', 'January', 'Cashier'),
(5678, 'Zarifa Rake', 'Male', 'August', 'Cashier'),
(1479, 'Johannes Hood', 'Female', 'July', 'Cashier'),
(9741, 'Stan Jung', 'Male', 'December', 'Cashier'),
(159, 'Albina Roy', 'Female', 'March', 'Manager'),
(8431, 'Maria Baylor', 'Female', 'March', 'Cashier' ),
(3871, 'Kenna Fosse', 'Female', 'April', 'Cashier'),
(7731, 'Martin Foth', 'Male', 'February', 'Cashier'),
(198, 'Alfred David', 'Male', 'June', 'Manager'),
(9371, 'Torquil Draper', 'Male', 'August', 'Cashier');

SELECT * FROM employee;