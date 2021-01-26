CREATE TABLE account (
    account_id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(32) NOT NULL,
    email VARCHAR(128) NOT NULL
);

CREATE UNIQUE INDEX account_username_unique_idx ON account (LOWER(username));
CREATE UNIQUE INDEX account_email_unique_idx ON account (LOWER(email));
