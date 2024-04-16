BEGIN TRANSACTION;

-- Authorities
INSERT INTO authority (name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

-- Users
INSERT INTO users (username,password_hash, name, address, city, state_code, zip) VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'Jack O''Lantern', null, 'Cleveland', 'OH', '44123'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'Jill O''Lantern', null, 'Beverly Hills', 'CA', '90210');

-- User-Authority Associations
INSERT INTO users_authorities (user_id, authority_name) VALUES
((SELECT user_id FROM users WHERE username = 'user'), 'ROLE_USER'),
((SELECT user_id FROM users WHERE username = 'admin'), 'ROLE_ADMIN');

-- This script inserts 10 transactions into the transactions table.

INSERT INTO transactions (user_id, type, amount, category, the_date) VALUES
(1, 'income', 1200.00, 'Salary', '2023-10-01'),
(2, 'expense', 150.00, 'Groceries', '2023-10-02'),
(1, 'expense', 200.00, 'Transportation', '2023-10-01'),
(2, 'income', 950.00, 'Freelance', '2023-10-04'),
(1, 'expense', 100.00, 'Utilities', '2023-10-05'),
(2, 'income', 500.00, 'Dividends', '2023-10-06'),
(1, 'income', 1100.00, 'Salary', '2023-10-07'),
(2, 'expense', 130.00, 'Rent', '2023-10-08'),
(1, 'income', 250.00, 'Bonus', '2023-10-09'),
(2, 'income', 800.00, 'Freelance', '2023-10-10');


COMMIT;