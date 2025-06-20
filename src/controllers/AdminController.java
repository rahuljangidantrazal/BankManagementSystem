package controllers;

import model.Bank;
import model.Branch;
import model.User;
import service.AdminService;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   AdminController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class provides a high-level interface for administrative operations 
//  *                      such as managing banks, branches, and employees. It delegates business logic to 
//  *                      the AdminService and exposes methods for creating banks/branches, assigning employees, 
//  *                      retrieving bank/branch details, and ensuring branch-level constraints (like one manager).
//  *                      It follows the Singleton pattern for consistent access throughout the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class AdminController {

    private static final AdminController instance = new AdminController();
    private static final AdminService adminService = AdminService.getInstance();

    private AdminController() {
    }

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
