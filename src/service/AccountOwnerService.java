package service;

import repo.AccountOwnerRepo;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   AccountOwnerService.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This service class acts as a bridge between the business logic and the 
//  *                      AccountOwnerRepo layer. It encapsulates operations related to ownership of 
//  *                      accounts, including adding owners (primary/joint), verifying ownership, 
//  *                      retrieving all owner IDs for a given account, checking if a user holds 
//  *                      an account in a specific bank, and performing soft deletion of user ownership.
//  *                      It uses the Singleton pattern for centralized service access.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class AccountOwnerService {

    private static final AccountOwnerService instance = new AccountOwnerService();
    private final AccountOwnerRepo accountOwnerRepo;

    private AccountOwnerService() {
        this.accountOwnerRepo = AccountOwnerRepo.getInstance();
    }

    public static AccountOwnerService getInstance() {
        return instance;
    }

    public boolean addOwner(int accountId, int userId, boolean isPrimary) {
        return accountOwnerRepo.addOwner(accountId, userId, isPrimary);
    }

    public boolean isUserAlreadyOwner(int accountId, int userId) {
        return accountOwnerRepo.isUserAlreadyOwner(accountId, userId);
    }

    public List<Integer> getOwnerIdsByAccountId(int accountId) {
        return accountOwnerRepo.getOwnerIdsByAccountId(accountId);
    }

    public boolean hasAccountInBank(int userId, int bankId) {
        return accountOwnerRepo.hasAccountInBank(userId, bankId);
    }

    public boolean softDeleteAccount(int accountId, int userId) {
        return accountOwnerRepo.softDeleteAccount(accountId, userId);
    }
}
