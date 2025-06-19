package controllers;

import service.AccountOwnerService;

import java.util.List;

import repo.AccountOwnerRepo;

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
