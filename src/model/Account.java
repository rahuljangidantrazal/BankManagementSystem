package model;

// *********************************************************************************************************
//  *  JAVA Class Name :   Account.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class represents a bank account within the system. It holds account-related
//  *                      data such as account number, associated branch ID, current balance, and status. 
//  *                      It also includes helper methods for checking account 
//  *                      state and standard getter/setter methods for encapsulation.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class Account {

    private int accountId;
    private String accountNumber;
    private int branchId;
    private double balance;
    private String status;

    public Account() {
    }

    public Account(String accountNumber, int branchId, double balance, String status) {
        this.accountNumber = accountNumber;
        this.branchId = branchId;
        this.balance = balance;
        this.status = status;
    }

    public Account(int accountId, String accountNumber, int branchId, double balance, String status) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.branchId = branchId;
        this.balance = balance;
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status);
    }

}
