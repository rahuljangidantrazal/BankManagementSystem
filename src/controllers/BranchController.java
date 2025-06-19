package controllers;

import java.util.List;

import model.Branch;
import service.BranchService;

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
