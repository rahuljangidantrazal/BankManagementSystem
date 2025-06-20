package util;

import repo.BranchRepo;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

// *********************************************************************************************************
//  *  JAVA Class Name :   IFSCUtil.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class generates a unique IFSC code for new branches 
//  *                      using an abbreviation of the bank name and a random 5-digit number. 
//  *                      The abbreviation is formed from the initials of each word in the 
//  *                      bank name.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class IFSCUtil {

    public static String generateUniqueIFSC(String bankName, BranchRepo branchRepo) {
        String abbreviation = Arrays.stream(bankName.trim().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());

        String ifsc;
        int random = new Random().nextInt(90000) + 10000;
        ifsc = abbreviation + random;

        return ifsc;
    }
}
