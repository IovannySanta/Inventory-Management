-- Código SQL para creación de base de datos

-- Creamos la base de datos
CREATE DATABASE inventory_management;

-- Usamos la base de datos
USE inventory_management;

-- Creamos la tabla productos que almacenara toda la información que pediremos posteriormente
CREATE TABLE products (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    product_code   VARCHAR(50),
    product_name   VARCHAR(150),
    category       VARCHAR(100),
    unit_price     DECIMAL(10,2),
    stock_quantity INT,
    minimum_stock  INT,
    supplier       VARCHAR(150),
    entry_date     DATE
);

-- Insertamos "productos" dentro de esta misma tabla
INSERT INTO products (product_code, product_name, category, unit_price, stock_quantity, minimum_stock, supplier, entry_date) VALUES
('PRD-001', 'Logitech MX Master 3 Mouse',        'Peripherals',      99.99,  45, 10, 'Logitech',             '2025-01-15'),
('PRD-002', 'Samsung 970 EVO Plus 1TB SSD',      'Storage',         109.99,  30,  8, 'Samsung Electronics',  '2025-02-03'),
('PRD-003', 'HP 207A Black Toner Cartridge',     'Consumables',      54.50,  60, 20, 'HP Inc.',              '2025-03-10'),
('PRD-004', 'Fellowes Powershred 79Ci Shredder', 'Office Equipment', 189.00,  12,  3, 'Fellowes Inc.',        '2025-04-22'),
('PRD-005', 'Targus 15.6" Laptop Backpack',      'Accessories',      49.95,  75, 15, 'Targus International', '2025-05-01');