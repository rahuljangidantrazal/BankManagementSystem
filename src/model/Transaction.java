package model;

import java.time.LocalDateTime;

// *********************************************************************************************************
//  *  JAVA Class Name :   Transaction.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class represents a financial transaction performed on a bank account. 
//  *                      It includes details such as the transaction ID, account ID, performer ID, 
//  *                      amount, transaction type (deposit/withdraw), timestamp, status (e.g., undone), 
//  *                      and metadata such as performer name, bank name, and branch name. 
//  *                      It is used to track and manage account activity in the banking system.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class Transaction {
    private int transactionId;
    private int accountId;
    private int performedBy;
    private double amount;
    private TransactionTypeEnum type;
    private LocalDateTime timestamp;
    private boolean isUndone;
    private String performedByName;

    private String bankName;
    private String branchName;

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Transaction(int accountId, int performedBy, double amount, TransactionTypeEnum type, LocalDateTime timestamp,
            boolean isUndone) {
        this.accountId = accountId;
        this.performedBy = performedBy;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.isUndone = isUndone;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(int performedBy) {
        this.performedBy = performedBy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    public void setType(TransactionTypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isUndone() {
        return isUndone;
    }

    public void setUndone(boolean isUndone) {
        this.isUndone = isUndone;
    }

    public String getPerformedByName() {
        return performedByName;
    }

    public void setPerformedByName(String performedByName) {
        this.performedByName = performedByName;
    }
}
