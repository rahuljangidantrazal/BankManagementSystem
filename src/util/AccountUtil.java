package util;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

// *********************************************************************************************************
//  *  JAVA Class Name :   AccountUtil.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class generates unique account numbers for new bank 
//  *                      accounts. The generated number includes an abbreviation of the bank 
//  *                      name, the branch ID, and a random 5-digit suffix.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class AccountUtil {

    public static String generateAccountNumber(String bankName, int branchId) {
        String abbreviation = Arrays.stream(bankName.trim().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());

        int randomPart = new Random().nextInt(90000) + 10000;

        return abbreviation + branchId + randomPart;
    }
}
