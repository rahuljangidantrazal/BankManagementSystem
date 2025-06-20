package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

// *********************************************************************************************************
//  *  JAVA Class Name :   EmailValidator.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class provides validation logic for email addresses using 
//  *                      a regular expression pattern. It ensures the email format is valid and 
//  *                      returns appropriate error messages if the input is missing or malformed.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class EmailValidator {

    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
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
