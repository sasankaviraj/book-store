-- Create the roles table
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- Insert available roles
INSERT INTO roles (name) VALUES
                             ('ROLE_USER'),
                             ('ROLE_AUTHOR'),
                             ('ROLE_ADMIN');