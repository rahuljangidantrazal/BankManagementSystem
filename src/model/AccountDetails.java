package model;

// *********************************************************************************************************
//  *  JAVA Class Name :   AccountDetails.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class serves as a data transfer object (DTO) that encapsulates the complete 
//  *                      details of a bank account. It includes the account information, associated branch, 
//  *                      primary account holder, and a list of joint account holders (if any). 
//  *                      It is used to present a comprehensive view of an account and its ownership.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

import java.util.List;

public class AccountDetails {
    private Account account;
    private Branch branch;
    private User primaryHolder;
    private List<User> jointHolders;

    public AccountDetails(Account account, Branch branch, User primaryHolder, List<User> jointHolders) {
        this.account = account;
        this.branch = branch;
        this.primaryHolder = primaryHolder;
        this.jointHolders = jointHolders;
    }

    public Account getAccount() {
        return account;
    }

    public Branch getBranch() {
        return branch;
    }

    public User getPrimaryHolder() {
        return primaryHolder;
    }

    public List<User> getJointHolders() {
        return jointHolders;
    }
}
