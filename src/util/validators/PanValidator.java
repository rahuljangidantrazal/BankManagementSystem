package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

// *********************************************************************************************************
//  *  JAVA Class Name :   PanValidator.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class provides validation logic for Indian PAN (Permanent 
//  *                      Account Number) using a regex pattern. A valid PAN consists of 5 uppercase 
//  *                      letters, followed by 4 digits, and ending with an uppercase letter.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

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
