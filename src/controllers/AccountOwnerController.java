package controllers;

import service.AccountOwnerService;

import java.util.List;

import repo.AccountOwnerRepo;

// *********************************************************************************************************
//  *  JAVA Class Name :   AccountOwnerController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class provides a centralized interface for managing account 
//  *                      ownership operations. It interacts with the AccountOwnerService to perform 
//  *                      actions like adding owners, checking if a user is already an owner, retrieving 
//  *                      all owners of an account, soft-deleting accounts, and verifying joint ownership.
//  *                      It follows the Singleton pattern to ensure consistent access across the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class AccountOwnerController {

    private static final AccountOwnerController instance = new AccountOwnerController();
    private final AccountOwnerService accountOwnerService;

    private AccountOwnerController() {
        this.accountOwnerService = AccountOwnerService.getInstance();
    }

    public static AccountOwnerController getInstance() {
        return instance;
    }

    public boolean addOwner(int accountId, int userId, boolean isPrimary) {
        return accountOwnerService.addOwner(accountId, userId, isPrimary);
    }

    public boolean isUserAlreadyOwner(int accountId, int userId) {
        return accountOwnerService.isUserAlreadyOwner(accountId, userId);
    }

    public List<Integer> getOwnerIdsByAccountId(int accountId) {
        return accountOwnerService.getOwnerIdsByAccountId(accountId);
    }

    public boolean hasAccountInBank(int userId, int bankId) {
        return accountOwnerService.hasAccountInBank(userId, bankId);
    }

    public boolean softDeleteAccount(int accountId, int userId) {
        return accountOwnerService.softDeleteAccount(accountId, userId);
    }

    public boolean hasJointHolder(int accountId) {
        return AccountOwnerRepo.getInstance().hasJointHolder(accountId);
    }

}
