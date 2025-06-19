package constants;

public class ValidatorMessages {

    public static class Aadhar {
        public static final String REQUIRED = "Aadhar Number is required.";
        public static final String INVALID = "Invalid Aadhar Number.";
    }

    public static class Email {
        public static final String REQUIRED = "Email is required.";
        public static final String INVALID = "Invalid email format.";
    }

    public static class PAN {
        public static final String REQUIRED = "PAN Number is required.";
        public static final String INVALID = "Invalid PAN Number.";
    }

    public static class Password {
        public static final String REQUIRED = "Password is required.";
        public static final String INVALID =
                "Password must be at least 8 characters long, contain uppercase, lowercase, digit, special character, and no spaces.";
    }
}

