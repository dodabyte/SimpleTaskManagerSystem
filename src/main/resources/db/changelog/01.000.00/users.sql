--liquibase formatted sql
--changeset Vadim_Miller:v1.0 localFilePath:01.000.00/users.sql

CREATE TABLE users
(
    user_id uuid PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    role VARCHAR(20) NOT NULL CHECK ( role IN ('ROLE_USER', 'ROLE_ADMIN') )
);

--rollback drop table users;