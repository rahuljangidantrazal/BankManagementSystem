package constants;

public class Messages {

    public static class BankMessages {
        public static final String ENTER_BANK_NAME = "Enter bank name: ";
        public static final String ENTER_MIN_BALANCE = "Enter minimum balance required: ";
        public static final String BANK_CREATED_SUCCESS = "Bank created successfully.";
        public static final String BANK_CREATION_FAILED = "Failed to create bank. It may already exist.";
        public static final String NO_BANKS_FOUND = "No banks found.";
        public static final String AVAILABLE_BANKS = "Available Banks:";
        public static final String BANK_NOT_FOUND = "Bank not found";
    }

    public static class BranchMessages {
        public static final String ENTER_BANK_ID = "Enter bank ID to add branch to: ";
        public static final String ENTER_BRANCH_NAME = "Enter branch name: ";
        public static final String ENTER_IFSC = "Enter IFSC code for branch: ";
        public static final String BRANCH_ADDED_SUCCESS = "Branch added successfully.";
        public static final String BRANCH_ADDITION_FAILED = "Failed to add branch.";
        public static final String NO_BRANCHES_FOUND = "No branches found for Bank ID: ";
        public static final String BRANCHES_FOR_BANK = "Branches for Bank ID ";
    }

    public static class RepoErrors {
        public static final String ERROR_INSERTING_BANK = "Error inserting bank: ";
        public static final String ERROR_FETCHING_BANKS = "Error fetching banks: ";
        public static final String ERROR_FETCHING_BANK = "Error occurred while fetching bank details: ";

        public static final String ERROR_INSERTING_BRANCH = "Error inserting branch: ";
        public static final String ERROR_FETCHING_BRANCHES = "Error fetching branches: ";
        public static final String ERROR_FETCHING_BRANCH = "Error fetching branch: ";
        public static final String ERROR_FETCHING_IFSC = "Error checking IFSC uniqueness: ";

        public static final String ERROER_FETCHING_BY_PAN = "Error fetching accounts by PAN: ";

        public static final String ERROR_CHECKING_BANK = "Error checking bank existence: ";
    }

    public static class RepoMessages {
        public static final String BANK_ALREADY_EXISTS = "Already Exists";
    }

    public static class UserMessages {
        public static final String USERNAME_EXISTS = "Username already exists.";
        public static final String PAN_EXISTS = "PAN already registered.";
    }

    public static class EmployeeMessages {
        public static final String NO_ACCOUNTS_FOUND_FOR_PAN = "No accounts found for PAN: ";
        public static final String NO_TRANSACTIONS_FOUND = "No transactions found.";
        public static final String NO_LOANS_FOUND_FOR_PAN = "No loans found for PAN: ";
    }

    public static class LoanMessages {
        public static final String LOAN_REQUEST_APPROVED = "Loan Approved.";
        public static final String LOAN_REQUEST_PENDING = "Loan request submitted for approval.";
        public static final String LOAN_REJECTED_SUCCESS = "Loan Rejected.";
        public static final String NO_LOANS_FOUND = "No loans found.";

        // Loan Model
        public static final String TO_STRING_PREFIX = "Loan{";
        public static final String TO_STRING_LOAN_ID = "loanId = ";
        public static final String TO_STRING_ACCOUNT_ID = "accountId = ";
        public static final String TO_STRING_AMOUNT = "amount = ";
        public static final String TO_STRING_APPLIED_ON = "appliedOn = ";
        public static final String TO_STRING_APPROVED_BY = "approvedBy = ";
        public static final String TO_STRING_AUTO_APPROVED = "isAutoApproved = ";
        public static final String TO_STRING_STATUS = "status = ";
        public static final String TO_STRING_REASON = "reason = '";
        public static final String TO_STRING_SUFFIX = "'}";
        public static final String COMMA = ", ";
    }

    public static class UserServiceMessages {

        public static final String ADMIN_NOT_FOUND = "Admin not found.";
        public static final String ADMIN_INACTIVE = "Admin account is inactive.";
        public static final String INVALID_PASSWORD = "Incorrect password.";
        public static final String NOT_AN_ADMIN = "User is not authorized as admin.";

        public static final String ERROR_INSERT_ADMIN = "Failed to insert admin.";
        public static final String ERROER_CHECKING_MANAGER = "Error checking existing manager: ";
        public static final String ERROR_FETCHING_ACCOUNT = "Error fetching accounts by PAN and bank: ";

        public static final String NO_BANKS_FOUND_FOR_REGISTRATION = "No banks found. Ask admin to create one first.";
        public static final String USER_REGISTRATION_FAILED = "Failed to register user.";
        public static final String JOINT_USER_REGISTRATION_FAILED = "Failed to register joint user.";
        public static final String ACCOUNT_CREATION_FAILED = "Failed to create bank account.";

        public static final String USER_NOT_FOUND_OR_INACTIVE = "User not found or inactive.";
        public static final String USER_UPDATE_SUCCESS = "User details updated successfully.";
        public static final String USER_UPDATE_FAILED = "Failed to update user.";

        public static final String ACCOUNT_NOT_FOUND = "Account not found.";
        public static final String JOINT_ACCOUNT_EXISTS = "This account already has a joint holder.";
        public static final String PRIMARY_USER_NOT_FOUND = "Primary user not found.";
        public static final String REGISTERING_JOINT_HOLDER = "Registering joint account holder...";
        public static final String JOINT_USER_ID_FETCH_FAILED = "Could not retrieve joint user ID.";
        public static final String JOINT_HOLDER_ADDED_SUCCESS = "Joint account holder added successfully.";
    }

}
