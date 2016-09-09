DROP TABLE products IF EXISTS;

CREATE TABLE products  (
    product_id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(100),
    price FLOAT
);