/* =====================================================
   DATABASE CREATION
===================================================== */
CREATE DATABASE bank;
USE bank;


/* =====================================================
   PROFILE TABLE (Customer Account Details)
===================================================== */
CREATE TABLE profile (
    acc_no BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30),
    last_name VARCHAR(40) NOT NULL,
    address VARCHAR(50) NOT NULL,
    email VARCHAR(60) UNIQUE,
    mobile VARCHAR(15) UNIQUE NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    acc_status ENUM('ACTIVE','CLOSED','SUSPENDED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(acc_no)
);

SELECT * FROM profile;


/* =====================================================
   ACCOUNT ACTIVITY TABLE (Deposit / Withdraw)
===================================================== */
CREATE TABLE account_activity (
    act_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    acc_no BIGINT NOT NULL,
    act_type ENUM('WITHDRAW','DEPOSIT') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    balance_after DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    act_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (acc_no) REFERENCES profile(acc_no)
);

SELECT * FROM account_activity;


/* =====================================================
   TRANSACTION TABLE (Money Transfer)
===================================================== */
CREATE TABLE txn (
    t_id BIGINT AUTO_INCREMENT,
    amount DECIMAL(15,2),
    from_accno BIGINT NOT NULL,
    to_accno BIGINT NOT NULL,
    from_balance_after DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    to_balance_after DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    t_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(t_id),
    FOREIGN KEY(from_accno) REFERENCES profile(acc_no)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY(to_accno) REFERENCES profile(acc_no)
        ON DELETE RESTRICT ON UPDATE RESTRICT
);

SELECT * FROM txn;


/* =====================================================
   ALTER TABLES (If modifying existing structure)
   Use only if tables already created earlier
===================================================== */

-- Modify profile table columns
ALTER TABLE profile MODIFY acc_no BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE profile MODIFY email VARCHAR(60) UNIQUE;
ALTER TABLE profile MODIFY mobile VARCHAR(15) UNIQUE;

-- Add balance tracking columns
ALTER TABLE account_activity 
ADD COLUMN balance_after DECIMAL(15,2) NOT NULL DEFAULT 0.00;

ALTER TABLE txn 
ADD COLUMN from_balance_after DECIMAL(15,2) NOT NULL DEFAULT 0.00,
ADD COLUMN to_balance_after DECIMAL(15,2) NOT NULL DEFAULT 0.00;
