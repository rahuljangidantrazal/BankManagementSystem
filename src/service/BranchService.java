package service;

import model.Bank;
import model.Branch;
import repo.BankRepo;
import repo.BranchRepo;
import util.IFSCUtil;
import constants.Messages;
import static constants.ViewMessages.AllViewMessages.*;

import static util.InputUtil.*;

import java.util.List;
import java.util.Scanner;

// *********************************************************************************************************
//  *  JAVA Class Name :   BranchService.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This service class handles operations related to branch creation and 
//  *                      listing. It facilitates user input for branch creation, generates unique 
//  *                      IFSC codes, and interacts with BranchRepo and BankRepo for persistence 
//  *                      and validation. It supports retrieving branches by bank ID or branch ID, 
//  *                      and provides display utilities for admin-related views.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class BranchService {

    private static BranchService instance;
    private final BranchRepo branchrepo;
    private final BankRepo bankRepo;

    private BranchService() {
        this.branchrepo = BranchRepo.getInstance();
        this.bankRepo = BankRepo.getInstance();
    }

    public static BranchService getInstance() {
        if (instance == null) {
            instance = new BranchService();
        }
        return instance;
    }

    public void addBranch(Scanner scanner) {
        System.out.print(Messages.BranchMessages.ENTER_BANK_ID);
        int bankId = Integer.parseInt(scanner.nextLine());

        Bank bank = bankRepo.getBankById(bankId);
        if (bank == null) {
            print(Messages.BankMessages.BANK_NOT_FOUND);
            return;
        }

        String bankName = bank.getBankName();

        System.out.print(Messages.BranchMessages.ENTER_BRANCH_NAME);
        String branchName = scanner.nextLine();

        String ifsc = IFSCUtil.generateUniqueIFSC(bankName, branchrepo);

        Branch branch = new Branch();
        branch.setIfscCode(ifsc);
        branch.setBankId(bankId);
        branch.setBranchName(branchName);

        boolean success = branchrepo.insertBranch(branch);
        if (success) {
            print(Messages.BranchMessages.BRANCH_ADDED_SUCCESS);
        } else {
            print(Messages.BranchMessages.BRANCH_ADDITION_FAILED);
        }
    }

    public void listBranchesByBankId(int bankId) {
        List<Branch> branches = branchrepo.getBranchesByBankId(bankId);
        if (branches.isEmpty()) {
            print(Messages.BranchMessages.NO_BRANCHES_FOUND + bankId);
            return;
        }

        for (Branch b : branches) {
            print(String.format(BRANCH_LIST_FORMAT, b.getBranchName(), b.getIfscCode()));
        }
    }

    public Branch getBranchById(int branchId) {
        return branchrepo.getBranchById(branchId);
    }

    public List<Branch> getBranchesByBankId(int bankId) {
        return branchrepo.getBranchesByBankId(bankId);
    }
}
