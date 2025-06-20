package util.validators;

import constants.ValidatorMessages;

import java.util.regex.Pattern;

// *********************************************************************************************************
//  *  JAVA Class Name :   AadharValidator.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class provides validation logic for Indian Aadhar numbers. 
//  *                      It ensures that the input is a 12-digit numeric string. Error messages are 
//  *                      returned based on predefined validation rules and messages.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

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
