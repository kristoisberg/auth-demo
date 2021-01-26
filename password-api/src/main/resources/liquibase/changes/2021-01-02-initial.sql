CREATE TABLE password_authentication_method (
    account_id BIGINT NOT NULL UNIQUE,
    password CHAR(60) NOT NULL
);
