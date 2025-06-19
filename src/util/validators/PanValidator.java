package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

public class PanValidator {
    private static final String Pan_PATTERN = "[A-Z]{5}\\d{4}[A-Z]";
    private static final Pattern pattern = Pattern.compile(Pan_PATTERN);

    public static boolean isValid(final String pan) {
        return pattern.matcher(pan).matches();
    }

    public static String getErrorMessage(final String pan) {
        if (pan == null || pan.isEmpty()) {
            return ValidatorMessages.PAN.REQUIRED;
        }
        if (!isValid(pan)) {
            return ValidatorMessages.PAN.INVALID;
        }
        return null;
    }
}
