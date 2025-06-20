package controllers;

import model.Account;
import model.AccountDetails;
import model.Transaction;
import service.CustomerService;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   CustomerController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class provides a high-level interface for customer-specific 
//  *                      operations in the banking system. It delegates business logic to the 
//  *                      CustomerService and exposes functionality such as retrieving user accounts, 
//  *                      performing deposits and withdrawals, checking balances, handling undoable 
//  *                      transactions, and viewing detailed account information. 
//  *                      The class follows the Singleton pattern for consistent access throughout the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class CustomerController {
    private static final CustomerController instance = new CustomerController();
    private static final CustomerService customerService = CustomerService.getInstance();

    private CustomerController() {
    }

    public static CustomerController getInstance() {
        return instance;
    }

    public List<Account> getAccountsForUser(int userId) {
        return customerService.getAccountsForUser(userId);
    }

    public boolean deposit(int accountId, int performedBy, double amount) {
        return customerService.deposit(accountId, performedBy, amount);
    }

    public boolean withdraw(int accountId, int performedBy, double amount) {
        return customerService.withdraw(accountId, performedBy, amount);
    }

    public double getBalance(int accountId) {
        return customerService.getBalance(accountId);
    }

    public Transaction getUndoableTransaction(int accountId) {
        return customerService.getUndoableTransaction(accountId);
    }

    public boolean undoTransaction(Transaction txn, int performedBy) {
        return customerService.undoTransaction(txn, performedBy);
    }

    public AccountDetails getAccountDetails(int accountId, int primaryUserId) {
        return customerService.getAccountDetails(accountId, primaryUserId);
    }

    public List<Integer> getAccountIds(int userId) {
        return customerService.getAccountIds(userId);
    }
}
