BEGIN TRANSACTION;

DROP TABLE IF EXISTS users_authorities, authority, users CASCADE;


-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE IF NOT EXISTS authority (
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(50),
    state_code CHAR(2),
    zip CHAR(5),
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS users_authorities (
    user_id BIGINT NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority_name),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (authority_name) REFERENCES authority (name)
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
-- h2 uses:
COMMIT;