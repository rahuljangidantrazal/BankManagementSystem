package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        return pattern.matcher(password).matches();
    }

    public static String getErrorMessage(final String password) {
        if (password == null || password.isEmpty()) {
            return ValidatorMessages.Password.REQUIRED;
        }
        if (!isValid(password)) {
            return ValidatorMessages.Password.INVALID;
        }
        return null;
    }
}
