BEGIN TRANSACTION;

DROP TABLE IF EXISTS users_authorities, authority, users, transactions CASCADE;


-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************


CREATE TABLE IF NOT EXISTS authority (
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (name)
);

--users (name is pluralized because 'user' is a SQL keyword)
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

-- join/junction table
CREATE TABLE IF NOT EXISTS users_authorities (
    user_id BIGINT NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority_name),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (authority_name) REFERENCES authority (name)
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount DECIMAL(6,2) NOT NULL,
    category VARCHAR(50),
    the_date DATE,
    CONSTRAINT PK_transaction_id PRIMARY KEY (transaction_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT type_check CHECK (type IN ('income', 'expense'))
);



--COMMIT TRANSACTION;
-- h2 uses:
COMMIT;