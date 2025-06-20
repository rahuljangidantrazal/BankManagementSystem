package controllers;

import model.Transaction;
import service.TransactionService;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   TransactionController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class acts as an interface between the view layer and the 
//  *                      TransactionService, facilitating operations related to financial transactions 
//  *                      such as deposit, withdrawal, undo actions, and retrieving transaction history. 
//  *                      It also supports searching transactions by PAN and bank name.
//  *                      This class follows the Singleton pattern to maintain a consistent access point 
//  *                      for transaction-related functionalities throughout the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class TransactionController {
    private static final TransactionController instance = new TransactionController();
    private static final TransactionService transactionService = TransactionService.getInstance();

    private TransactionController() {
    }

    public static TransactionController getInstance() {
        return instance;
    }

    public boolean deposit(int accountId, int performedBy, double amount) {
        return transactionService.deposit(accountId, performedBy, amount);
    }

    public boolean withdraw(int accountId, int performedBy, double amount) {
        return transactionService.withdraw(accountId, performedBy, amount);
    }

    public Transaction getUndoableTransaction(int accountId) {
        return transactionService.getUndoableTransaction(accountId);
    }

    public boolean undoTransaction(Transaction txn, int performedBy) {
        return transactionService.undoTransaction(txn, performedBy);
    }

    public List<Transaction> getTransactionHistory(int accountId) {
        return transactionService.getTransactionHistory(accountId);
    }

    public List<Transaction> getAccountsByPanAndBank(String pan, int bankId) {
        return transactionService.getAccountsByPanAndBank(pan, bankId);
    }

    public List<Transaction> getTransactionsByPan(String pan, String bankName) {
        return TransactionService.getInstance().getTransactionsByPan(pan, bankName);
    }

}
