package constants;

public class TableQueries {
        public static final String[] CREATE_TABLES = {
                        // 1. Bank table
                        "CREATE TABLE IF NOT EXISTS Bank (" +
                                        "bank_id INT PRIMARY KEY AUTO_INCREMENT," +
                                        "bank_name VARCHAR(100) NOT NULL UNIQUE," +
                                        "min_balance DECIMAL(12,2) NOT NULL," +
                                        "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)",

                        // 2. Branch table
                        "CREATE TABLE IF NOT EXISTS Branch (" +
                                        "branch_id INT PRIMARY KEY AUTO_INCREMENT," +
                                        "bank_id INT," +
                                        "branch_name VARCHAR(100)," +
                                        "ifsc_code VARCHAR(20) UNIQUE," +
                                        "FOREIGN KEY (bank_id) REFERENCES Bank(bank_id))",

                        // 3. User table (merged Customer + Employee)
                        "CREATE TABLE IF NOT EXISTS User (" +
                                        "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                                        "first_name VARCHAR(50)," +
                                        "last_name VARCHAR(50)," +
                                        "username VARCHAR(50) UNIQUE NOT NULL," +
                                        "password VARCHAR(255) NOT NULL," +
                                        "email VARCHAR(100)," +
                                        "phone VARCHAR(15)," +
                                        "user_type ENUM('CUSTOMER', 'CASHIER', 'MANAGER', 'ADMIN') NOT NULL," +
                                        "aadhar_number VARCHAR(12)," +
                                        "pan_number VARCHAR(10)," +
                                        "branch_id INT," +
                                        "credit_score INT DEFAULT 700," +
                                        "is_active BOOLEAN DEFAULT TRUE," +
                                        "FOREIGN KEY (branch_id) REFERENCES Branch(branch_id))",

                        // 4. Account table
                        "CREATE TABLE IF NOT EXISTS Account (" +
                                        "account_id INT PRIMARY KEY AUTO_INCREMENT," +
                                        "account_number VARCHAR(20) UNIQUE NOT NULL," +
                                        "branch_id INT," +
                                        "balance DECIMAL(15,2) NOT NULL," +
                                        "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                        "status ENUM('ACTIVE', 'INACTIVE', 'CLOSED') DEFAULT 'ACTIVE'," +
                                        "FOREIGN KEY (branch_id) REFERENCES Branch(branch_id))",

                        // 5. Account_Owner table
                        "CREATE TABLE IF NOT EXISTS Account_Owner (" +
                                        "account_id INT," +
                                        "user_id INT," +
                                        "is_primary BOOLEAN DEFAULT FALSE," +
                                        "is_active BOOLEAN DEFAULT TRUE," +
                                        "PRIMARY KEY (account_id, user_id)," +
                                        "FOREIGN KEY (account_id) REFERENCES Account(account_id)," +
                                        "FOREIGN KEY (user_id) REFERENCES User(user_id))",

                        // 6. Transaction table
                        "CREATE TABLE IF NOT EXISTS Transaction (" +
                                        "transaction_id INT PRIMARY KEY AUTO_INCREMENT," +
                                        "account_id INT," +
                                        "performed_by INT," +
                                        "type ENUM('DEPOSIT', 'WITHDRAWAL') NOT NULL," +
                                        "amount DECIMAL(15,2) NOT NULL," +
                                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                        "is_undone BOOLEAN DEFAULT FALSE," +
                                        "FOREIGN KEY (account_id) REFERENCES Account(account_id)," +
                                        "FOREIGN KEY (performed_by) REFERENCES User(user_id))",

                        // 7. Loan table
                        "CREATE TABLE IF NOT EXISTS Loan (" +
                                        "loan_id INT PRIMARY KEY AUTO_INCREMENT," +
                                        "account_id INT," +
                                        "amount DECIMAL(15,2) NOT NULL," +
                                        "applied_on DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                        "approved_by INT," +
                                        "is_auto_approved BOOLEAN DEFAULT FALSE," +
                                        "status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING'," +
                                        "reason TEXT," +
                                        "FOREIGN KEY (account_id) REFERENCES Account(account_id)," +
                                        "FOREIGN KEY (approved_by) REFERENCES User(user_id))"
        };
}
