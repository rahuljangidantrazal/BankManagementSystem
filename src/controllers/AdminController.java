package controllers;

import model.Bank;
import model.Branch;
import model.User;
import service.AdminService;

import java.util.List;

public class AdminController {

    private static final AdminController instance = new AdminController();
    private static final AdminService adminService = AdminService.getInstance();

    private AdminController() {}

    public static AdminController getInstance() {
        return instance;
    }

    public Integer getBankIdByName(String bankName) {
        return adminService.getBankIdByName(bankName);
    }

    public boolean createBank(String name, double minBal) {
        return adminService.createBank(name, minBal);
    }

    public Bank getBankById(int bankId) {
        return adminService.getBankById(bankId);
    }

    public boolean createBranch(int bankId, String branchName) {
        return adminService.createBranch(bankId, branchName);
    }

    public List<Bank> getAllBanks() {
        return adminService.getAllBanks();
    }

    public List<Branch> getBranchesByBankId(int bankId) {
        return adminService.getBranchesByBankId(bankId);
    }

    public boolean branchHasManager(int branchId) {
        return adminService.branchHasManager(branchId);
    }

    public boolean createEmployee(User user) {
        return adminService.createEmployee(user);
    }

    public void viewBanksAndBranches() {
        adminService.viewBanksAndBranches();
    }
}
