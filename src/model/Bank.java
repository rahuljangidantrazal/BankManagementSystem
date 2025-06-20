package model;

// *********************************************************************************************************
//  *  JAVA Class Name :   Bank.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class represents a bank entity in the system. It holds essential details 
//  *                      such as the bank's unique ID, name, and the minimum balance requirement for 
//  *                      opening or maintaining an account. It serves as the core reference for branches 
//  *                      and account-related validations.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class Bank {
    private int bankId;
    private String bankName;
    private double minBalance;

    public Bank() {
    }

    public Bank(String bankName, double minBalance) {
        this.bankName = bankName;
        this.minBalance = minBalance;
    }

    public String getBankName() {
        return bankName;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
