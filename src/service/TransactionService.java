package service;

import model.Account;
import model.Bank;
import model.Transaction;
import model.TransactionTypeEnum;
import model.User;
import repo.AccountRepo;
import repo.BankRepo;
import repo.BranchRepo;
import repo.TransactionRepo;
import repo.UserRepo;

import static util.InputUtil.print;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   TransactionService.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This service class manages all business logic related to financial transactions
//  *                      within the banking system. It includes functionalities for deposits, withdrawals,
//  *                      undoing transactions within a time limit, and credit score adjustments based on 
//  *                      minimum balance rules. It communicates with various repositories to ensure account 
//  *                      balance consistency and maintains transactional records.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class TransactionService {
    private static final TransactionService instance = new TransactionService();
    private final TransactionRepo transactionRepo = TransactionRepo.getInstance();
    private final BranchRepo branchRepo = BranchRepo.getInstance();
    private final AccountRepo accountRepo = AccountRepo.getInstance();
    private final UserRepo userRepo = UserRepo.getInstance();
    private final BankRepo bankRepo = BankRepo.getInstance();
    private static final int UNDO_LIMIT_MINUTES = 10;

    TransactionService() {
    }

    public static TransactionService getInstance() {
        return instance;
    }

    public boolean deposit(int accountId, int performedBy, double amount) {
        if (amount <= 0)
            return false;
        if (accountRepo.deposit(accountId, amount)) {
            Transaction txn = new Transaction(accountId, performedBy, amount, TransactionTypeEnum.DEPOSIT,
                    LocalDateTime.now(), false);
            return transactionRepo.insertTransaction(txn);
        }
        return false;
    }

    public boolean withdraw(int accountId, int performedBy, double amount) {
        if (amount <= 0)
            return false;

        try {
            Account acc = accountRepo.getAccountById(accountId);
            if (acc == null || acc.getBalance() < amount)
                return false;

            if (!accountRepo.withdraw(accountId, amount))
                return false;

            Transaction txn = new Transaction(accountId, performedBy, amount, TransactionTypeEnum.WITHDRAWAL,
                    LocalDateTime.now(), false);

            boolean txnSuccess = transactionRepo.insertTransaction(txn);
            if (!txnSuccess)
                return false;

            Bank bank = bankRepo.getBankByAccountId(accountId);
            User user = userRepo.findById(performedBy);

            if (bank != null && user != null) {
                double newBalance = accountRepo.getBalance(accountId);
                int currentScore = user.getCreditScore();

                if (newBalance < bank.getMinBalance()) {
                    if (currentScore > 600) {
                        user.setCreditScore(Math.max(600, currentScore - 5));
                        userRepo.updateUser(user);
                    }
                } else {
                    if (currentScore < 700) {
                        user.setCreditScore(700);
                        userRepo.updateUser(user);
                    }
                }
            }

            return true;

        } catch (Exception e) {
            print(e.getMessage());
            return false;
        }
    }

    public Transaction getUndoableTransaction(int accountId) {
        Transaction txn = transactionRepo.getLatestTransaction(accountId);
        if (txn == null || txn.isUndone())
            return null;

        Duration duration = Duration.between(txn.getTimestamp(), LocalDateTime.now());
        return (duration.toMinutes() <= UNDO_LIMIT_MINUTES) ? txn : null;
    }

    public boolean undoTransaction(Transaction txn, int performedBy) {
        if (txn == null)
            return false;

        boolean success = switch (txn.getType()) {
            case DEPOSIT -> accountRepo.withdraw(txn.getAccountId(), txn.getAmount());
            case WITHDRAWAL -> accountRepo.deposit(txn.getAccountId(), txn.getAmount());
        };

        if (!success)
            return false;

        if (!transactionRepo.markTransactionAsUndone(txn.getTransactionId()))
            return false;

        Transaction reverseTxn = new Transaction(
                txn.getAccountId(), performedBy, txn.getAmount(),
                txn.getType() == TransactionTypeEnum.DEPOSIT ? TransactionTypeEnum.WITHDRAWAL
                        : TransactionTypeEnum.DEPOSIT,
                LocalDateTime.now(), false);

        return transactionRepo.insertTransaction(reverseTxn);
    }

    public List<Transaction> getTransactionHistory(int accountId) {
        return transactionRepo.getTransactionsByAccountId(accountId);
    }

    public List<Transaction> getAccountsByPanAndBank(String pan, int bankId) {
        List<Account> accounts = accountRepo.getAccountsByPan(pan);
        if (accounts == null || accounts.isEmpty()) {
            return List.of();
        }

        return accounts.stream()
                .filter(account -> {
                    int branchId = account.getBranchId();
                    int bId = branchRepo.getBankIdByBranchId(branchId);
                    return bId == bankId;
                })
                .flatMap(account -> transactionRepo.getTransactionsByAccountId(account.getAccountId()).stream())
                .toList();
    }

    public List<Transaction> getTransactionsByPan(String pan, String bankName) {
        return transactionRepo.getTransactionsByPan(pan, bankName);
    }

}
