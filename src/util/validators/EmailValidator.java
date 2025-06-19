package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(final String email) {
        return pattern.matcher(email).matches();
    }

    public static String getErrorMessage(final String email) {
        if (email == null || email.isEmpty()) {
            return ValidatorMessages.Email.REQUIRED;
        }
        if (!isValid(email)) {
            return ValidatorMessages.Email.INVALID;
        }
        return null;
    }
}
