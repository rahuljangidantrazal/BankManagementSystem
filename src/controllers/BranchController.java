package controllers;

import java.util.List;

import model.Branch;
import service.BranchService;

// *********************************************************************************************************
//  *  JAVA Class Name :   BranchController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class provides an interface for managing branch-related operations. 
//  *                      It delegates the business logic to the BranchService and allows retrieval of branch 
//  *                      details by branch ID and listing all branches under a specific bank.
//  *                      It is implemented as a Singleton to ensure consistent access throughout the system.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class BranchController {

    private static final BranchController instance = new BranchController();
    private final BranchService branchService;

    private BranchController() {
        this.branchService = BranchService.getInstance();
    }

    public static BranchController getInstance() {
        return instance;
    }

    public Branch getBranchById(int branchId) {
        return branchService.getBranchById(branchId);
    }

    public List<Branch> getBranchesByBankId(int bankId) {
        return branchService.getBranchesByBankId(bankId);
    }
}
