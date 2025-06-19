package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

public class AadharValidator {
    private static final String Aadhar_PATTERN = "\\d{12}";
    private static final Pattern pattern = Pattern.compile(Aadhar_PATTERN);

    public static boolean isValid(final String aadhar) {
        return pattern.matcher(aadhar).matches();
    }

    public static String getErrorMessage(final String aadhar) {
        if (aadhar == null || aadhar.isEmpty()) {
            return ValidatorMessages.Aadhar.REQUIRED;
        }
        if (!isValid(aadhar)) {
            return ValidatorMessages.Aadhar.INVALID;
        }
        return null;
    }
}

