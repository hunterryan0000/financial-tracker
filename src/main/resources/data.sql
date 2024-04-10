BEGIN TRANSACTION;

-- Users
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user1','user1','ROLE_USER', 'User1 Name', 'User1 address', 'Cleveland', 'OH', '44123');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user2','user2','ROLE_USER', 'User2 Name', null, 'Beverly Hills', 'CA', '90210');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user3','user3','ROLE_USER', 'User3 Name', 'User3 address', 'Chicago', 'IL', '60609');


COMMIT;