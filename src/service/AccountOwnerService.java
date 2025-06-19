package service;

import repo.AccountOwnerRepo;

import java.util.List;

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
