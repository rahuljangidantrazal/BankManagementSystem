package controllers;

import model.Loan;
import model.LoanStatusEnum;
import service.LoanService;

import java.util.List;

public class LoanController {

    private static final LoanController instance = new LoanController();
    private static final LoanService loanService = LoanService.getInstance();

    private LoanController() {
    }

    public static LoanController getInstance() {
        return instance;
    }

    public boolean requestLoan(Loan loan) {
        return loanService.requestLoan(loan);
    }

    public List<Loan> getPendingLoans() {
        return loanService.getPendingLoans();
    }

    public boolean approveLoan(int loanId, int managerId) {
        return loanService.updateLoanStatus(loanId, LoanStatusEnum.APPROVED, managerId);
    }

    public boolean rejectLoan(int loanId, int managerId) {
        return loanService.updateLoanStatus(loanId, LoanStatusEnum.REJECTED, managerId);
    }

    public boolean updateLoanStatus(int loanId, LoanStatusEnum status, int managerId) {
        return loanService.updateLoanStatus(loanId, status, managerId);
    }

    public List<Loan> getLoansByAccountId(int accountId) {
        return loanService.getLoansByAccountId(accountId);
    }

    public List<Loan> getLoanHistoryByPan(String pan) {
        return loanService.getLoanHistoryByPan(pan);
    }
}
