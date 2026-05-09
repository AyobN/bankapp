DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS customers;


CREATE TABLE customers (
    customer_number  INT          NOT NULL,
    password_hash    VARCHAR(255) NOT NULL,

    PRIMARY KEY (customer_number),
    CONSTRAINT chk_customer_number_range
        CHECK (customer_number BETWEEN 100000000 AND 999999999)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE accounts (
    account_number   CHAR(9)      NOT NULL,
    customer_number  INT          NOT NULL,
    balance          FLOAT        NOT NULL DEFAULT 0,
    nickname         VARCHAR(255) NULL,

    PRIMARY KEY (account_number),
    KEY idx_accounts_customer_number (customer_number),
    CONSTRAINT fk_accounts_customer
        FOREIGN KEY (customer_number) REFERENCES customers(customer_number)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;