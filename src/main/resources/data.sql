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

COMMIT;