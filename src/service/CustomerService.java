package service;

import model.Account;
import model.AccountDetails;
import model.Branch;
import model.Transaction;
import model.User;
import repo.AccountOwnerRepo;
import repo.AccountRepo;
import repo.BranchRepo;
import repo.UserRepo;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   CustomerService.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This service class handles all business operations available to customers.
//  *                      It provides functionalities such as viewing accounts, depositing, withdrawing, 
//  *                      checking balances, undoing recent transactions, and viewing account details 
//  *                      (including joint holders and branch info). It interacts with repositories and 
//  *                      the TransactionService to encapsulate core customer logic.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class CustomerService {
    private static final CustomerService instance = new CustomerService();
    private final TransactionService txnService = new TransactionService();
    private final AccountRepo accountRepo = AccountRepo.getInstance();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        return instance;
    }

    public List<Account> getAccountsForUser(int userId) {
        return accountRepo.getAccountsByUserId(userId);
    }

    public boolean deposit(int accountId, int performedBy, double amount) {
        return txnService.deposit(accountId, performedBy, amount);
    }

    public boolean withdraw(int accountId, int performedBy, double amount) {
        return txnService.withdraw(accountId, performedBy, amount);
    }

    public double getBalance(int accountId) {
        return accountRepo.getBalance(accountId);
    }

    public Transaction getUndoableTransaction(int accountId) {
        return txnService.getUndoableTransaction(accountId);
    }

    public boolean undoTransaction(Transaction txn, int performedBy) {
        return txnService.undoTransaction(txn, performedBy);
    }

    public AccountDetails getAccountDetails(int accountId, int primaryUserId) {
        Account account = accountRepo.getAccountById(accountId);
        if (account == null || !account.isActive())
            return null;

        Branch branch = BranchRepo.getInstance().getBranchById(account.getBranchId());
        User primaryUser = UserRepo.getInstance().findById(primaryUserId);

        List<Integer> ownerIds = AccountOwnerRepo.getInstance().getOwnerIdsByAccountId(accountId);
        List<User> jointHolders = ownerIds.stream()
                .filter(id -> id != primaryUserId)
                .map(id -> UserRepo.getInstance().findById(id))
                .toList();

        return new AccountDetails(account, branch, primaryUser, jointHolders);
    }

    public List<Integer> getAccountIds(int userId) {
        return getAccountsForUser(userId).stream()
                .map(Account::getAccountId)
                .toList();
    }

}
