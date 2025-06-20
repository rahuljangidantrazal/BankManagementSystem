package util;

import constants.Constants.InputMessages;

import java.util.Scanner;
import java.util.function.Function;

// *********************************************************************************************************
//  *  JAVA Class Name :   InputUtil.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class provides common input handling methods for the console-based 
//  *                      user interface. It supports validated field entry, yes/no prompts, numeric parsing, 
//  *                      and "go back" handling to improve user experience and standardize input flows.
//  *
//  *                      Provided Methods:
//  *                      - print(String)
//  *                      - print(String...)
//  *                      - readNonEmpty(String)
//  *                      - readNonEmpty(String, String)
//  *                      - readValidatedField(String, Function<String, String>)
//  *                      - readValidatedField(String, Function<String, String>, String)
//  *                      - askYesOrNo(String)
//  *                      - goBack()
//  *                      - readValidatedInt(String)
//  *                      - readDouble(String)
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

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

    public static String readNonEmpty(String fieldName) {
        return readValidatedField(fieldName,
                input -> input.isEmpty() ? fieldName + InputMessages.REQUIRED_FIELD : null);
    }

    public static String readNonEmpty(String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = sc.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

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

    public static void goBack() {
        while (true) {
            System.out.print(InputMessages.ENTER_0_TO_GO_BACK);
            String input = sc.nextLine().trim();
            if (input.equals("0"))
                return;
            print(InputMessages.INVALID_INPUT);
        }
    }

    public static int readValidatedInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readNonEmpty(prompt));
            } catch (NumberFormatException e) {
                print(InputMessages.PLEASE_ENTER_VALID_NUMBER);
            }
        }
    }

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
