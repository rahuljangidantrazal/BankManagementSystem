package service;

import model.Bank;
import model.Branch;
import model.User;
import repo.BankRepo;
import repo.BranchRepo;
import repo.UserRepo;
import util.IFSCUtil;
import constants.Messages;

import static constants.ViewMessages.AllViewMessages.BANKS_LIST;
import static util.InputUtil.*;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   AdminService.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This service class handles business operations available to Admin users,
//  *                      including creating banks and branches, managing employee (manager/cashier)
//  *                      creation, verifying branch-manager mapping, and displaying bank-branch
//  *                      relationships. It interacts with multiple repositories and utilities to 
//  *                      coordinate data flow and business rules enforcement.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class AdminService {
    private static AdminService instance;
    private final BankRepo bankRepo = BankRepo.getInstance();
    private final BranchRepo branchRepo = BranchRepo.getInstance();
    private final UserRepo userRepo = UserRepo.getInstance();
    private final BranchService branchService = BranchService.getInstance();

    private AdminService() {
    }

    public static AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }

    public boolean createBank(String name, double minBal) {
        Bank bank = new Bank(name, minBal);
        return bankRepo.insertBank(bank);
    }

    public Integer getBankIdByName(String name) {
        return bankRepo.getBankIdByName(name);
    }

    public Bank getBankById(int bankId) {
        return bankRepo.getBankById(bankId);
    }

    public boolean createBranch(int bankId, String branchName) {
        Bank bank = getBankById(bankId);
        if (bank == null)
            return false;

        String ifsc = IFSCUtil.generateUniqueIFSC(bank.getBankName(), branchRepo);
        Branch branch = new Branch(bankId, branchName, ifsc);
        return branchRepo.insertBranch(branch);
    }

    public List<Bank> getAllBanks() {
        return bankRepo.getAllBanks();
    }

    public List<Branch> getBranchesByBankId(int bankId) {
        return branchRepo.getBranchesByBankId(bankId);
    }

    public boolean branchHasManager(int branchId) {
        return userRepo.branchHasManager(branchId);
    }

    public boolean createEmployee(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException(Messages.UserMessages.USERNAME_EXISTS);
        }
        if (userRepo.existsByPan(user.getPan())) {
            throw new IllegalArgumentException(Messages.UserMessages.PAN_EXISTS);

        }
        return userRepo.insertUser(user);
    }

    public void viewBanksAndBranches() {
        List<Bank> banks = getAllBanks();
        if (banks.isEmpty()) {
            print(Messages.BankMessages.NO_BANKS_FOUND);
        } else {
            for (Bank bank : banks) {
                print(BANKS_LIST + bank.getBankName());
                branchService.listBranchesByBankId(bank.getBankId());
            }
        }
        goBack();
    }
}
