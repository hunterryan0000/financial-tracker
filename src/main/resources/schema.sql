BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users CASCADE;


-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	address varchar(100) NULL,
	city varchar(50) NULL,
	state_code char(2) NULL,
	zip varchar(5) NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);
-- cart item
--CREATE TABLE cart_item (
--	cart_item_id SERIAL,
--	user_id int NOT NULL,
--	product_id int NOT NULL,
--	quantity int NOT NULL DEFAULT(1),
--	CONSTRAINT PK_cart_item PRIMARY KEY (cart_item_id),
--	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
--	CONSTRAINT FK_cart_item_product FOREIGN KEY (product_id) REFERENCES product(product_id)
--);
--CREATE UNIQUE INDEX IX_cart_item_user_product ON cart_item(user_id, product_id);



--COMMIT TRANSACTION;
-- h2 is:
COMMIT;