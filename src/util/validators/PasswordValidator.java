package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

// *********************************************************************************************************
//  *  JAVA Class Name :   PasswordValidator.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class provides validation logic for user passwords. 
//  *                      It ensures the password is at least 8 characters long, contains at 
//  *                      least one digit, one lowercase letter, one uppercase letter, one 
//  *                      special character, and has no whitespace.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class PasswordValidator {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
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
