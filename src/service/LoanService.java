package service;

import model.Loan;
import model.LoanStatusEnum;
import repo.LoanRepo;

import java.util.List;

public class LoanService {

    private static final LoanRepo loanRepo = LoanRepo.getInstance();
    private static final LoanService instance = new LoanService();

    private LoanService() {
    }

    public static LoanService getInstance() {
        return instance;
    }

    public boolean requestLoan(Loan loan) {
        if (loan.getAmount() < 5000000) {
            loan.setStatus(LoanStatusEnum.APPROVED);
            loan.setApprovedBy(null);
            loan.setAutoApproved(true);
        } else {
            loan.setStatus(LoanStatusEnum.PENDING);
            loan.setApprovedBy(null);
            loan.setAutoApproved(false);
        }
        return loanRepo.applyLoan(loan);
    }

    public List<Loan> getPendingLoans() {
        return loanRepo.getPendingLoans();
    }

    public boolean approveLoan(int loanId, int managerId) {
        return loanRepo.updateLoanStatus(loanId, LoanStatusEnum.APPROVED, managerId);
    }

    public boolean rejectLoan(int loanId, int managerId) {
        return loanRepo.updateLoanStatus(loanId, LoanStatusEnum.REJECTED, managerId);
    }

    public boolean updateLoanStatus(int loanId, LoanStatusEnum status, int managerId) {
        return loanRepo.updateLoanStatus(loanId, status, managerId);
    }

    public List<Loan> getLoansByAccountId(int accountId) {
        return loanRepo.getLoansByAccountId(accountId);
    }

    public List<Loan> getLoanHistoryByPan(String pan) {
        return loanRepo.getLoanHistoryByPan(pan);
    }

}
