-- Insert data into CUSTOMER table
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('Jane Smith', 'jane.smith@example.com');
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('Mike Johnson', 'mike.johnson@example.com');
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('Emily Brown', 'emily.brown@example.com');
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('David Lee', 'david.lee@example.com');
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('Sarah Wilson', 'sarah.wilson@example.com');
INSERT INTO CUSTOMER (NAME, EMAIL) VALUES ('Chris Taylor', 'chris.taylor@example.com');

-- Insert data into ECOM_SALES_DATA table, referencing the CUSTOMER table
INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD001', '2024-10-01', 'Shipped', 'Warehouse', 'Online', 'Electronics', 'Medium', 199.99, 'New York', 'NY', 1);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD002', '2024-10-02', 'Pending', 'Drop-Shipping', 'Retail', 'Apparel', 'Large', 89.99, 'Los Angeles', 'CA', 2);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD003', '2024-10-03', 'Delivered', 'Warehouse', 'Online', 'Home & Garden', 'Small', 49.99, 'Chicago', 'IL', 3);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD004', '2024-10-04', 'Shipped', 'Warehouse', 'Mobile', 'Beauty', 'Small', 29.99, 'Houston', 'TX', 4);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD005', '2024-10-05', 'Processing', 'Drop-Shipping', 'Online', 'Electronics', 'Large', 599.99, 'Phoenix', 'AZ', 5);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD006', '2024-10-06', 'Shipped', 'Warehouse', 'Retail', 'Sports & Outdoors', 'Medium', 149.99, 'Philadelphia', 'PA', 6);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD007', '2024-10-07', 'Pending', 'Warehouse', 'Online', 'Books', 'Small', 24.99, 'San Antonio', 'TX', 7);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD008', '2024-10-08', 'Delivered', 'Drop-Shipping', 'Mobile', 'Toys & Games', 'Medium', 39.99, 'San Diego', 'CA', 1);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD009', '2024-10-09', 'Processing', 'Warehouse', 'Online', 'Apparel', 'Small', 79.99, 'Dallas', 'TX', 2);

INSERT INTO ECOM_SALES_DATA (ORDER_ID, ORDER_DATE, STATUS, FULFILMENT, CHANNEL, CATEGORY, SIZE, AMOUNT, SHIP_CITY, SHIP_STATE, CUSTOMER_ID)
VALUES ('ORD010', '2024-10-10', 'Shipped', 'Warehouse', 'Retail', 'Electronics', 'Large', 399.99, 'San Jose', 'CA', 3);