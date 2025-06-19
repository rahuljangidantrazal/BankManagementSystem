package constants;

public class Constants {

    public static class InputMessages {
        public static final String REQUIRED_FIELD = " is required.";
        public static final String INVALID_INPUT = "Invalid input.";
        public static final String ENTER_0_TO_GO_BACK = "Enter 0 to go back: ";
        public static final String PLEASE_ENTER_VALID_NUMBER = "Please enter a valid number.";
        public static final String ERROR_PREFIX = "Error: ";
        public static final String YES_NO_PROMPT_SUFFIX = " (Y/N): ";
        public static final String INVALID_YES_NO = "Invalid input. Please enter Y or N.";
    }

    public static class GeneralMessages {
        public static final String ACTION_FAILED = "Action failed. Please try again.";
        public static final String ACTION_SUCCESS = "Action completed successfully.";
        public static final String INVALID_OPTION = "Invalid option. Please choose again.";
    }

    public static class ValidationMessages {
        public static final String INVALID_PHONE = "Phone must be 10 digits.";
        public static final String INVALID_AADHAR = "Aadhar must be 12 digits.";
        public static final String INVALID_PAN = "PAN format is invalid.";
        public static final String INVALID_EMAIL = "Email format is invalid.";
        public static final String INVALID_PASSWORD = "Password does not meet the criteria.";
    }

    public static class LoginMessages {
        public static final String ENTER_USERNAME = "Enter username: ";
        public static final String ENTER_PASSWORD = "Enter password: ";
        public static final String INVALID_CREDENTIALS = "Invalid username or password.";
        public static final String ACCOUNT_INACTIVE = "This account is inactive.";
    }

    public static class Format {
        public static final String DATE_FORMAT = "dd-MM-yyyy";
        public static final String TIME_FORMAT = "hh:mm:ss a";
    }

    public static class Texts {
        public static final String APPROVED = "Approved";
        public static final String OK = "OK";
        public static final String UNDONE = "Undone";
        public static final String NO_TRANSACTIONS = "No transactions found for this account.";
        public static final String PASSBOOK_TITLE = "\n                  === PASSBOOK ===                      ";
        public static final String PASSBOOK_END = "                  === ******** ===                      \n";
    }

    public static class Table {
        public static final String LOAN_BORDER = "-".repeat(76);
        public static final String LOAN_HEADER_FORMAT = "| %-15s | %-25s | %-12s | %-15s%n";
        public static final String LOAN_ROW_FORMAT = "| Rs.%-12.2f | %-25s | %-12s | %-15s%n";

        public static final String TRANSACTION_HEADER_FORMAT = "%-15s | %-10s | %-20s | %-12s | %-12s%n";
        public static final String TRANSACTION_BORDER = "--------------------------------------------------------------------------------------------";
        public static final String TRANSACTION_ROW_FORMAT = "Rs.%-12.2f | %-10s | %-20s | %-12s | %-12s%n";

        public static final String PASSBOOK_HEADER_FORMAT = "%-5s | %-10s | %-10s | %-12s | %-12s | %-8s%n";
        public static final String PASSBOOK_BORDER = "---------------------------------------------------------------";
        public static final String PASSBOOK_ROW_FORMAT = "%-5d | %-10s | Rs.%-7.2f | %-12s | %-12s | %-8s%n";
    }
}
