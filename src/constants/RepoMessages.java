package constants;

public class RepoMessages {

    // --- AccountOwnerRepo ---
    public static final String ADD_OWNER = "INSERT INTO Account_Owner (account_id, user_id, is_primary) VALUES (?, ?, ?)";

    public static final String IS_USER_ALREADY_OWNER = "SELECT COUNT(*) FROM Account_Owner WHERE account_id = ? AND user_id = ?";

    public static final String GET_OWNER_IDS_BY_ACCOUNT = "SELECT user_id FROM Account_Owner WHERE account_id = ?";

    public static final String HAS_ACCOUNT_IN_BANK = """
                SELECT COUNT(*) FROM Account_Owner ao
                JOIN Account a ON ao.account_id = a.account_id
                JOIN Branch b ON a.branch_id = b.branch_id
                WHERE ao.user_id = ? AND b.bank_id = ? AND ao.is_active = TRUE AND a.status = 'ACTIVE'
            """;

    public static final String SOFT_DELETE_DEACTIVATE_OWNER = "UPDATE Account_Owner SET is_active = FALSE WHERE account_id = ? AND user_id = ?";

    public static final String SOFT_DELETE_CHECK_REMAINING_OWNERS = "SELECT COUNT(*) FROM Account_Owner WHERE account_id = ? AND is_active = TRUE";

    public static final String SOFT_DELETE_DEACTIVATE_ACCOUNT = "UPDATE Account SET status = 'INACTIVE' WHERE account_id = ?";

    public static final String HAS_JOINT_HOLDER = "SELECT COUNT(*) FROM Account_Owner WHERE account_id = ? AND is_active = TRUE";

    // --- AccountRepo ---
    public static final String INSERT_ACCOUNT = "INSERT INTO Account (account_number, branch_id, balance, status) VALUES (?, ?, ?, ?)";
    public static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM Account WHERE account_id = ?";
    public static final String DEPOSIT_TO_ACCOUNT = "UPDATE Account SET balance = balance + ? WHERE account_id = ?";
    public static final String WITHDRAW_FROM_ACCOUNT = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
    public static final String SELECT_BALANCE_BY_ACCOUNT_ID = "SELECT balance FROM Account WHERE account_id = ?";
    public static final String SELECT_ACCOUNTS_BY_USER_ID = """
                SELECT a.* FROM Account a
                JOIN Account_Owner ao ON a.account_id = ao.account_id
                WHERE ao.user_id = ? AND a.status = 'ACTIVE' AND ao.is_active = TRUE
            """;
    public static final String SELECT_ACCOUNT_BY_ACCOUNT_NUMBER = "SELECT * FROM Account WHERE account_number = ?";
    public static final String CLOSE_ACCOUNT = "UPDATE Account SET status = 'CLOSED' WHERE account_id = ?";
    public static final String SELECT_ACCOUNTS_BY_PAN = """
                SELECT DISTINCT a.* FROM Account a
                JOIN Account_Owner ao ON a.account_id = ao.account_id
                JOIN User u ON ao.user_id = u.user_id
                WHERE u.pan = ?
            """;

    // --- Bank Repo ---
    public static final String BANK_EXISTS_BY_NAME = "SELECT bank_id FROM Bank WHERE LOWER(bank_name) = LOWER(?)";

    public static final String INSERT_BANK = "INSERT INTO Bank (bank_name, min_balance) VALUES (?, ?)";

    public static final String ALL_FROM_BANK = "SELECT * FROM Bank";

    public static final String GET_BANK_ID_BY_NAME = "SELECT bank_id FROM Bank WHERE LOWER(bank_name) = LOWER(?)";

    public static final String GET_BANK_BY_ID = "SELECT * FROM Bank WHERE bank_id = ?";

    public static final String GET_BANK_NAMES_BY_PAN = """
                SELECT DISTINCT bk.bank_name
                FROM Bank bk
                JOIN Branch b ON bk.bank_id = b.bank_id
                JOIN Account a ON a.branch_id = b.branch_id
                JOIN Account_Owner ao ON ao.account_id = a.account_id
                JOIN User u ON ao.user_id = u.user_id
                WHERE u.pan_number = ?
            """;

    public static final String GET_BANK_BY_ACCOUNT_ID = """
                SELECT b.bank_id, b.bank_name, b.min_balance
                FROM Account a
                JOIN Branch br ON a.branch_id = br.branch_id
                JOIN Bank b ON br.bank_id = b.bank_id
                WHERE a.account_id = ?
            """;

    public static final String ERROR_BANK = "Error fetching bank by account ID: ";

    // --- Branch Repo ---
    public static final String INSERT_BRANCH = "INSERT INTO Branch (bank_id, branch_name, ifsc_code) VALUES (?, ?, ?)";

    public static final String GET_BRANCHES_BY_BANK_ID = "SELECT * FROM Branch WHERE bank_id = ?";

    public static final String GET_BRANCH_BY_ID = "SELECT * FROM Branch WHERE branch_id = ?";

    public static final String GET_BRANCH_ID_BY_NAME = "SELECT branch_id FROM Branch WHERE branch_name = ?";

    public static final String EXISTS_BY_IFSC = "SELECT 1 FROM Branch WHERE ifsc_code = ?";

    public static final String GET_BANK_ID_BY_BRANCH_ID = "SELECT bank_id FROM Branch WHERE branch_id = ?";

    // --- User Repo ---
    public static final String INSERT_ADMIN = "SELECT COUNT(*) FROM User WHERE username = 'admin' AND user_type = 'ADMIN'";

    public static final String INSERT_USER = "INSERT INTO User (first_name, last_name, phone, email, aadhar_number, pan_number, "
            + "username, password, user_type, branch_id, credit_score, is_active) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM User WHERE username = ? AND password = ? AND is_active = true";

    public static final String GET_USER_ID_BY_USERNAME = "SELECT user_id FROM User WHERE username = ?";

    public static final String GET_USER_BY_USERNAME = "SELECT * FROM User WHERE username = ?";

    public static final String EXISTS_BY_USERNAME = "SELECT 1 FROM User WHERE username = ?";

    public static final String EXISTS_BY_PAN = "SELECT 1 FROM User WHERE pan_number = ?";

    public static final String EXISTS_BY_PHONE = "SELECT 1 FROM User WHERE phone = ?";

    public static final String EXISTS_BY_EMAIL = "SELECT 1 FROM User WHERE email = ?";

    public static final String EXISTS_BY_AADHAR = "SELECT 1 FROM User WHERE aadhar_number = ?";

    public static final String BRANCH_HAS_MANAGER = "SELECT COUNT(*) FROM User WHERE branch_id = ? AND user_type = 'MANAGER' AND is_active = TRUE";

    public static final String FIND_BY_ID = "SELECT * FROM User WHERE user_id = ?";

    public static final String UPDATE_USER = "UPDATE User SET first_name = ?, last_name = ?, phone = ?, email = ?, "
            + "pan_number = ?, username = ?, password = ?, branch_id = ?, credit_score = ? "
            + "WHERE user_id = ? AND is_active = TRUE";

    public static final String GET_ACCOUNTS_BY_PAN_AND_BANK = """
                SELECT DISTINCT a.* FROM Account a
                JOIN Account_Owner ao ON a.account_id = ao.account_id
                JOIN User u ON ao.user_id = u.user_id
                JOIN Branch b ON a.branch_id = b.branch_id
                WHERE u.pan = ? AND b.bank_id = ?
            """;

    public static final String GET_ALL_PANS = "SELECT DISTINCT pan_number FROM User WHERE pan_number IS NOT NULL AND pan_number != ''";

    // --- Transaction Repo ---
    public static final String INSERT_TRANSACTION = "INSERT INTO Transaction (account_id, performed_by, type, amount, timestamp, is_undone) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String GET_TRANSACTION_BY_ACCOUNT_ID = """
                SELECT t.*, u.first_name, u.last_name
                FROM Transaction t
                LEFT JOIN User u ON t.performed_by = u.user_id
                WHERE t.account_id = ?
                ORDER BY t.timestamp DESC
            """;

    public static final String MARK_TRANSACTION_UNDONE = "UPDATE Transaction SET is_undone = true WHERE transaction_id = ?";

    public static final String GET_LATEST_TRANSACTION = """
                SELECT * FROM Transaction
                WHERE account_id = ? AND is_undone = false
                ORDER BY timestamp DESC LIMIT 1
            """;

    public static final String GET_TRANSACTION_BY_PAN = """
                SELECT t.*,
                       pu.first_name AS performer_first_name,
                       pu.last_name AS performer_last_name,
                       bk.bank_name,
                       b.branch_name
                FROM Transaction t
                JOIN Account a ON t.account_id = a.account_id
                JOIN Branch b ON a.branch_id = b.branch_id
                JOIN Bank bk ON b.bank_id = bk.bank_id
                JOIN Account_Owner ao ON a.account_id = ao.account_id
                JOIN User u ON ao.user_id = u.user_id
                LEFT JOIN User pu ON t.performed_by = pu.user_id
                WHERE u.pan_number = ? AND bk.bank_name = ?
            """;

    // --- Loan Repo ---
    public static final String APPLY_LOAN = "INSERT INTO Loan (account_id, amount, reason, approved_by, is_auto_approved, status) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String GET_PENDING_LOANS = "SELECT * FROM Loan WHERE status = ?";

    public static final String UPDATE_LOAN_STATUS = "UPDATE Loan SET status = ?, approved_by = ? WHERE loan_id = ?";

    public static final String APPROVE_LOAN_MANUALLY = "UPDATE Loan SET status = ?, approved_by = ?, is_auto_approved = false WHERE loan_id = ?";

    public static final String GET_LOANS_BY_ACCOUNT_ID = "SELECT * FROM Loan WHERE account_id = ?";

    public static final String GET_LOAN_HISTORY_BY_PAN = """
            SELECT l.* FROM Loan l
            JOIN Account a ON l.account_id = a.account_id
            JOIN Account_Owner ao ON ao.account_id = a.account_id
            JOIN User u ON u.user_id = ao.user_id
            WHERE u.pan = ?
            """;
}
