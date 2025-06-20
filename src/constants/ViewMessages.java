package constants;

public class ViewMessages {
        public static class AllViewMessages {

                // Login
                public static final String ENTER_ADMIN_USERNAME = "Enter admin username: ";
                public static final String ENTER_ADMIN_PASSWORD = "Enter admin password: ";
                public static final String LOGIN_FAILED = "Login failed.";

                // Admin Menu
                public static final String ADMIN_MENU_HEADER = """

                                ==============================================
                                |               ADMIN DASHBOARD              |
                                ==============================================
                                | 1. Create Bank                             |
                                | 2. Create Branch                           |
                                | 3. Create Employee (Cashier/Manager)       |
                                | 4. View Banks & Branches                   |
                                | 5. Get Transaction History by PAN          |
                                | 0. Logout                                  |
                                ==============================================
                                """;
                public static final String ENTER_OPTION = "Choose option: ";
                public static final String INVALID_OPTION = "Invalid option.";
                public static final String ENTER_VALID_NUMBER = "Please enter a valid number.";
                public static final String LOGGED_OUT = "Logged out.";

                public static final String DASH = " - ";
                public static final String NO_PAN_RECORD = "No PAN records found.";
                public static final String PAN_LIST = "Available PANs:";
                // Create Bank
                public static final String ENTER_BANK_NAME = "Enter Bank Name: ";
                public static final String ENTER_MIN_BALANCE = "Enter Minimum Balance";
                public static final String BANK_CREATED_SUCCESS = "Bank created successfully.";
                public static final String BANK_CREATION_FAILED = "Failed to create bank.";
                public static final String ASK_CREATE_BRANCH = "Do you want to create a branch for this bank now?";
                public static final String ASK_CREATE_EMPLOYEE = "Do you want to add an employee to this branch now?";

                // Create Branch
                public static final String NO_BANKS_FOUND = "No banks found.";
                public static final String INVALID_BANK_NAME = "Invalid bank name.";
                public static final String ENTER_BRANCH_NAME = "Enter Branch Name: ";
                public static final String BRANCH_CREATED_SUCCESS = "Branch created successfully.";
                public static final String BRANCH_CREATION_FAILED = "Failed to create branch.";
                public static final String NO_BRANCHES_FOUND = "No branches found for this bank.";
                public static final String AVAILABLE_BRANCHES = "Available Branches:";
                public static final String DISPLAY_AVAILABLE_BRANCHES = " - Branch ID: %d, Name: %s, IFSC: %s%n";
                public static final String ENTER_BRANCH_ID_OR_NAME = "Enter Branch ID or Branch Name";
                public static final String BRANCH_NOT_FOUND = "Branch not found.";

                // Create Employee
                public static final String ENTER_FIRST_NAME = "First Name: ";
                public static final String ENTER_LAST_NAME = "Last Name: ";
                public static final String ENTER_PHONE = "Phone (10 digits): ";
                public static final String PHONE_REGEX = "\\d{10}";
                public static final String PHONE_INVALID = "Phone must be 10 digits.";
                public static final String ENTER_EMAIL = "Enter Email: ";
                public static final String ENTER_AADHAR = "Enter Aadhar (12 Digits): ";
                public static final String ENTER_PAN = "Enter PAN (eg. ABCDE1234F): ";
                public static final String ENTER_USERNAME = "Username: ";
                public static final String ENTER_PASSWORD = "Password: ";
                public static final String EMPLOYEE_CREATED = "Employee created.";
                public static final String EMPLOYEE_CREATION_FAILED = "Failed to create employee.";
                public static final String EMPLOYEE_CREATION_ERROR_PREFIX = "Error: ";

                // Employee Type
                public static final String EMPLOYEE_TYPE_MENU = """

                                Choose Employee Type:
                                1. Cashier
                                2. Manager
                                """;
                public static final String ENTER_EMPLOYEE_TYPE_CHOICE = "Enter choice: ";
                public static final String MANAGER_EXISTS = "This branch already has a manager.";
                public static final String ENTER_INVALID_EMPLOYEE_TYPE_CHOICE = "Invalid option.";

                // View Bank and Branches
                public static final String BANKS_LIST = "---> Bank: ";
                public static final String BRANCH_LIST_FORMAT = "    └─>Branch: %s, IFSC: %s";

                // Cashier Dashboard
                public static final String CASHIER_MENU = """

                                ============================================================
                                |                   Cashier Dashboard                      |
                                ============================================================
                                | 1. View Customer Transactions                            |
                                | 0. Logout                                                |
                                ============================================================
                                """;
                public static final String CASHIER_ENTER_OPTION = "Choose option: ";
                public static final String CASHIER_LOGOUT_MESSAGE = "Logging out...";
                public static final String CASHIER_INVALID_OPTION = "Invalid choice.";
                public static final String ENTER_CUSTOMER_PAN = "Enter customer's PAN: ";
                public static final String NO_TRANSACTIONS_FOUND = "No transactions found.";

                // === CustomerView ===
                public static final String NO_ACTIVE_ACCOUNT_FOUND = "No active account found.";
                public static final String CUSTOMER_ACCOUNT_LIST_HEADER = "Your Accounts:";
                public static final String ACCOUNT_TABLE_DIVIDER = "-".repeat(78);
                public static final String ACCOUNT_TABLE_ROW_FORMAT = "%d. Acc: %-15s | Rs.%-10.2f | Branch: %-15s | IFSC: %s%n";
                public static final String SELECT_AN_ACCOUNT = "Select an Account: ";
                public static final String INVALID_ACCOUNT_CHOICE = "Invalid choice.";
                public static final String CUSTOMER_WELCOME = "\nWelcome %s %s!\n";
                public static final String UNKNOWN = "Unknown";
                public static final String APPROVED = "APPROVED";
                public static final String PENDING = "PENDING";

                public static final String CUSTOMER_MENU = """

                                =================================================================
                                |                         Customer Dashboard                    |
                                =================================================================
                                | 1. Deposit                  | 2. Withdraw                     |
                                | 3. View Transaction History | 4. Undo Last Transaction        |
                                | 5. View Balance             | 6. Add Joint Account Holder     |
                                | 7. Edit Profile             | 8. Account Details              |
                                | 9. Apply for Loan           | 10. Loan History                |
                                | 11. Delete Account          | 12. Print Passbook              |
                                |                        0. Logout                              |
                                =================================================================
                                """;
                public static final String ENTER_CHOICE = "Choose option: ";

                public static final String DEPOSIT_PROMPT = "Amount to deposit: ";
                public static final String DEPOSIT_AMOUNT_INVALID = "Amount must be greater than 0.";
                public static final String DEPOSIT_SUCCESS = "Deposit successful.";
                public static final String DEPOSIT_FAILED = "Deposit failed.";

                public static final String WITHDRAW_PROMPT = "Amount to withdraw: ";
                public static final String WITHDRAW_AMOUNT_INVALID = "Amount must be greater than 0.";
                public static final String WITHDRAW_SUCCESS = "Withdraw successful.";
                public static final String WITHDRAW_FAILED = "Insufficient balance or withdrawal failed.";

                public static final String LAST_TRANSACTION_INFO = "Last transaction: %s Rs.%.2f\n";
                public static final String UNDO_CONFIRMATION = "Confirm undo";
                public static final String TRANSACTION_UNDO_SUCCESS = "Transaction undone.";
                public static final String TRANSACTION_UNDO_FAILED = "Undo failed.";
                public static final String NO_ELIGIBLE_UNDO = "No eligible transaction to undo.";
                public static final String UNDO_CANCELLED = "Undo cancelled.";

                public static final String BALANCE_DISPLAY = "Current Balance: Rs.%.2f\n";

                public static final String ACCOUNT_DETAILS_FAILED = "Could not retrieve account details.";
                public static final String ACCOUNT_NUMBER_LABEL = "Account Number : ";
                public static final String BRANCH_NAME_LABEL = "Branch Name    : ";
                public static final String IFSC_CODE_LABEL = "IFSC Code      : ";
                public static final String PRIMARY_USER_LABEL = "\nPrimary User:";
                public static final String JOINT_HOLDERS_LABEL = "\nJoint Account Holder(s):";
                public static final String JOINT_DIVIDER = "-------------------------";

                public static final String LOAN_AMOUNT_PROMPT = "Enter loan amount: ";
                public static final String LOAN_REASON_PROMPT = "Purpose of loan: ";
                public static final String LOAN_AUTO_APPROVED = "Loan auto-approved.\nRs.%s credited to your account.";
                public static final String LOAN_REQUEST_FAILED = "Loan request failed.";
                public static final String LOAN_REQUEST_PENDING = "Loan request submitted for approval.";
                public static final String LOW_CREDIT_SCORE_LOAN_DENIED = "Loan request denied. Credit score must be at least 650.";

                public static final String NO_LOANS_FOUND = "No loans found.";

                public static final String DELETE_CONFIRMATION = "Are you sure you want to delete this account?";
                public static final String ACCOUNT_DELETED = "Account deleted successfully.";
                public static final String ACCOUNT_DELETE_FAILED = "Account deletion failed.";
                public static final String DELETE_CANCELLED = "Deletion cancelled.";

                public static final String NO_TXN_FOR_PASSBOOK = "No transactions found for this account.";

                public static final String LOGGING_OUT = "Logging out...";

                public static final String PRINT_NAME = "Name        : ";
                public static final String PRINT_USERNAME = "Username    : ";
                public static final String PRINT_EMAIL = "Email       : ";
                public static final String PRINT_PHONE = "Phone       : ";
                public static final String PRINT_AADHAR = "Aadhar      : ";
                public static final String PRINT_PAN = "PAN         : ";

                public static final String EDITING_DETAILS_FOR = "Editing details for: ";
                public static final String SPACE = " ";
                public static final String ENTER_PHONE_EDIT = "Enter Phone [ Old - ";
                public static final String ENTER_EMAIL_EDIT = "Enter Email [ Old - ";
                public static final String END_BRACKET = "]";
                public static final String ENTER_NEW_PASS = "Enter New Password ";

                // ===Main View===
                public static final String MAIN_MENU = """

                                ==============================================
                                |           Welcome to Bank System           |
                                ==============================================
                                | 1. Admin Login                             |
                                | 2. User Login                              |
                                | 3. Register New Customer                   |
                                | 0. Exit                                    |
                                ==============================================
                                """;
                public static final String THANK_YOU_END = "Thank you for using banking services.";

                // ===Manager VIew ===
                public static String MANAGER_MENU = """

                                ============================================================
                                |                     Manager Dashboard                    |
                                ============================================================
                                | 1. Approve/Reject Pending Loans                          |
                                | 2. View Customer Loan History                            |
                                | 3. View Customer Transactions                            |
                                | 0. Logout                                                |
                                ============================================================
                                """;
                public static final String APPROVE_REJECT_SKIP_PROMPT = "Approve (A) / Reject (R) / Skip (S): ";
                public static final String LOAN_INFO_FORMAT = "Loan ID: %d | Amount: Rs.%.2f | Account ID: %d | Reason: %s";

                // ===Register User VIew ===
                public static final String ACCOUNT_CREATED_HEADER = """

                                ==============================================
                                |     Bank Account Created Successfully      |
                                ==============================================
                                """;
                public static final String ACCOUNT_NUMBER = "Account Number : ";
                public static final String BRANCH_NAME = "Branch Name    : ";
                public static final String IFSC_CODE = "IFSC Code      : ";
                public static final String PRIMARY_USER = "Primary User:";
                public static final String JOINT_ACCOUNT_HOLDER = "\nJoint Account Holder:";
                public static final String SECTION_SEPARATOR = "----------------------------------------------";
                public static final String SECTION_END = "==============================================\n";

                public static final String USER_NAME = "Name        : ";
                public static final String USER_USERNAME = "Username    : ";
                public static final String USER_EMAIL = "Email       : ";
                public static final String USER_PHONE = "Phone       : ";
                public static final String USER_AADHAR = "Aadhar      : ";
                public static final String USER_PAN = "PAN         : ";

                public static final String ACCOUNT_EXISTS = "You already have an account in this bank.";
                public static final String OPENING_ACC = "Proceeding to open account...";
                public static final String ALREADY_EXISTS = "Already Exists. Try Another";
                public static final String INITIAL_DEPOSIT_PROMPT = "Enter Initial Deposit (>= Min Balance %.2f): ";
                public static final String DISPLAY_BRANCH_INFO = "Name: %s, IFSC Code: %s%n";
                public static final String DISPLAY_BANK_INFO = "Name: %s, Min Balance: %.2f%n";
                public static final String MIN_BALANCE_ERROR = "Amount must be >= %.2f";
                public static final String INVALID_AMOUNT_ERROR = "Invalid amount.";
                public static final String IS_JOINT_ACCOUNT_PROMPT = "Is this a joint account?";
                public static final String JOINT_ACCOUNT_PREFIX = "Joint Account ";
                public static final String JOINT_ACCOUNT_EXIST = "This account already has a joint holder.";

                // === User View ===
                public static final String USER_LOGIN_MENU_BLOCK = """
                                ==============================================
                                |                 User Login                 |
                                ==============================================
                                | 1. Customer Login                          |
                                | 2. Cashier Login                           |
                                | 3. Manager Login                           |
                                | 0. Go Back                                 |
                                ==============================================
                                """;
                public static final String ENTER_USERNAME_GOBACK = "Enter Username (or 0 to go back): ";
                public static final String INVALID_CREDENTIALS = "Invalid credentials or user type. Please try again.";
        }

}
