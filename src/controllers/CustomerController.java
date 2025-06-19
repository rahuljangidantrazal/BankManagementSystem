package controllers;

import model.Account;
import model.AccountDetails;
import model.Transaction;
import service.CustomerService;

import java.util.List;

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
