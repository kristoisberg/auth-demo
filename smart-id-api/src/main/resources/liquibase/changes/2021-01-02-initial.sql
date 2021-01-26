CREATE TABLE smartid_authentication_method (
    account_id BIGINT NOT NULL UNIQUE,
    country_code CHAR(2) NOT NULL,
    identity_code VARCHAR(32) NOT NULL,
    UNIQUE (country_code, identity_code),
    CHECK (country_code IN ('EE', 'LT'))
);
