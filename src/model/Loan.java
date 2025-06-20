package model;

import java.time.LocalDateTime;

import constants.Messages.LoanMessages;

// *********************************************************************************************************
//  *  JAVA Class Name :   Loan.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class represents a loan request made by a customer in the banking system. 
//  *                      It includes details such as the associated account ID, loan amount, application 
//  *                      date, approval status, approving manager (if any), and reason for the loan. 
//  *                      It also provides helper methods for checking loan approval status and updating 
//  *                      the loan status (approve/reject). Used as a domain model and for tracking loan 
//  *                      lifecycle in the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class Loan {
    private int loanId;
    private int accountId;
    private double amount;
    private LocalDateTime appliedOn;
    private Integer approvedBy;
    private boolean isAutoApproved;
    private LoanStatusEnum status;
    private String reason;

    public Loan() {
    }

    public Loan(int accountId, double amount, boolean isAutoApproved, LoanStatusEnum status, String reason) {
        this.accountId = accountId;
        this.amount = amount;
        this.appliedOn = LocalDateTime.now();
        this.isAutoApproved = isAutoApproved;
        this.status = status;
        this.reason = reason;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(LocalDateTime appliedOn) {
        this.appliedOn = appliedOn;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public boolean isAutoApproved() {
        return isAutoApproved;
    }

    public void setAutoApproved(boolean autoApproved) {
        isAutoApproved = autoApproved;
    }

    public LoanStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LoanStatusEnum status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isPendingApproval() {
        return !isAutoApproved && status == LoanStatusEnum.PENDING;
    }

    public boolean isApproved() {
        return status == LoanStatusEnum.APPROVED;
    }

    public boolean isRejected() {
        return status == LoanStatusEnum.REJECTED;
    }

    public void approve(int approverId) {
        this.status = LoanStatusEnum.APPROVED;
        this.approvedBy = approverId;
    }

    public void reject(int approverId) {
        this.status = LoanStatusEnum.REJECTED;
        this.approvedBy = approverId;
    }

    @Override
    public String toString() {
        return LoanMessages.TO_STRING_PREFIX +
                LoanMessages.TO_STRING_LOAN_ID + loanId + LoanMessages.COMMA +
                LoanMessages.TO_STRING_ACCOUNT_ID + accountId + LoanMessages.COMMA +
                LoanMessages.TO_STRING_AMOUNT + amount + LoanMessages.COMMA +
                LoanMessages.TO_STRING_APPLIED_ON + appliedOn + LoanMessages.COMMA +
                LoanMessages.TO_STRING_APPROVED_BY + approvedBy + LoanMessages.COMMA +
                LoanMessages.TO_STRING_AUTO_APPROVED + isAutoApproved + LoanMessages.COMMA +
                LoanMessages.TO_STRING_STATUS + status + LoanMessages.COMMA +
                LoanMessages.TO_STRING_REASON + reason +
                LoanMessages.TO_STRING_SUFFIX;
    }

}
