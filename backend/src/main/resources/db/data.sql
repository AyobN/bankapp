-- Customers (all use password "password123")
-- BCrypt hash generated for "password123"
INSERT INTO customers (customer_number, password_hash) VALUES
                                                           (100000001, '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjPeuPRtcFSZ7UTBR9FuLMnWFm3fe.'),
                                                           (100000002, '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjPeuPRtcFSZ7UTBR9FuLMnWFm3fe.'),
                                                           (100000003, '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjPeuPRtcFSZ7UTBR9FuLMnWFm3fe.'),
                                                           (100000004, '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjPeuPRtcFSZ7UTBR9FuLMnWFm3fe.'),
                                                           (100000005, '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjPeuPRtcFSZ7UTBR9FuLMnWFm3fe.');

-- Accounts (varied per customer)
INSERT INTO accounts (account_number, customer_number, balance, nickname) VALUES
                                                                              -- Customer 1: 3 accounts
                                                                              ('100000001', 100000001, 1500.50, 'Main Checking'),
                                                                              ('100000002', 100000001, 12000.00, 'Savings'),
                                                                              ('100000003', 100000001, 250.75, NULL),

                                                                              -- Customer 2: 1 account
                                                                              ('200000001', 100000002, 750.25, 'Vacation Fund'),

                                                                              -- Customer 3: 2 accounts
                                                                              ('300000001', 100000003, 5000.00, 'Emergency Fund'),
                                                                              ('300000002', 100000003, 100.00, NULL),

                                                                              -- Customer 4: 4 accounts
                                                                              ('400000001', 100000004, 25000.00, 'House Down Payment'),
                                                                              ('400000002', 100000004, 800.00, 'Daily Spending'),
                                                                              ('400000003', 100000004, 15000.00, NULL),
                                                                              ('400000004', 100000004, 3200.50, 'Tax Savings'),

                                                                              -- Customer 5: 2 accounts
                                                                              ('500000001', 100000005, 450.00, NULL),
                                                                              ('500000002', 100000005, 8900.25, 'Investment');