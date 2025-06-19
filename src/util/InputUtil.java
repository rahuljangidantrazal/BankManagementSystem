package util;

import constants.Constants.InputMessages;

import java.util.Scanner;
import java.util.function.Function;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    // Print utility
    public static void print(String message) {
        if (message != null && !message.isBlank()) {
            System.out.println(message);
        }
    }

    public static void print(String... messages) {
        for (String msg : messages) {
            print(msg);
        }
    }

    // Read non-empty input using validator
    public static String readNonEmpty(String fieldName) {
        return readValidatedField(fieldName,
                input -> input.isEmpty() ? fieldName + InputMessages.REQUIRED_FIELD : null);
    }

    // Read non-empty input with default fallback
    public static String readNonEmpty(String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = sc.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    // Read validated input (mandatory)
    public static String readValidatedField(String label, Function<String, String> validator) {
        while (true) {
            System.out.print(label + ": ");
            String input = sc.nextLine().trim();
            String error = validator.apply(input);
            if (error == null) {
                return input;
            }
            print(InputMessages.ERROR_PREFIX + error);
        }
    }

    // Read validated input with default fallback
    public static String readValidatedField(String prompt, Function<String, String> validator, String defaultValue) {
        while (true) {
            System.out.print(prompt + " [" + defaultValue + "]: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                return defaultValue;

            String error = validator.apply(input);
            if (error == null) {
                return input;
            }
            print(InputMessages.ERROR_PREFIX + error);
        }
    }

    // Ask Y/N
    public static boolean askYesOrNo(String message) {
        while (true) {
            System.out.print(message + InputMessages.YES_NO_PROMPT_SUFFIX);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes"))
                return true;
            if (input.equals("n") || input.equals("no"))
                return false;
            print(InputMessages.INVALID_YES_NO);
        }
    }

    // Wait for "0" to go back
    public static void goBack() {
        while (true) {
            System.out.print(InputMessages.ENTER_0_TO_GO_BACK);
            String input = sc.nextLine().trim();
            if (input.equals("0"))
                return;
            print(InputMessages.INVALID_INPUT);
        }
    }

    // Read integer with validation
    public static int readValidatedInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readNonEmpty(prompt));
            } catch (NumberFormatException e) {
                print(InputMessages.PLEASE_ENTER_VALID_NUMBER);
            }
        }
    }

    // Read double with validation
    public static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readNonEmpty(prompt));
            } catch (NumberFormatException e) {
                print(InputMessages.PLEASE_ENTER_VALID_NUMBER);
            }
        }
    }
}
