package controllers;

import model.Transaction;
import service.TransactionService;

import java.util.List;

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
