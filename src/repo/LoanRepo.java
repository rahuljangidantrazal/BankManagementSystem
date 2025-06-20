package repo;

import model.Loan;
import model.LoanStatusEnum;
import util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static constants.RepoMessages.*;


// *********************************************************************************************************
//  *  JAVA Class Name :   LoanRepo.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This repository class handles all direct database operations related to loans. 
//  *                      It supports applying for loans (auto/manual), updating loan status, retrieving 
//  *                      pending or approved loans by account ID or PAN, and encapsulates result mapping 
//  *                      for Loan objects. It follows the Singleton pattern to provide a single consistent 
//  *                      access point for loan-related persistence operations.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************


public class LoanRepo {

    private static final LoanRepo instance = new LoanRepo();

    private LoanRepo() {
    }

    public static LoanRepo getInstance() {
        return instance;
    }

    public boolean applyLoan(Loan loan) {
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(APPLY_LOAN)) {
            stmt.setInt(1, loan.getAccountId());
            stmt.setDouble(2, loan.getAmount());
            stmt.setString(3, loan.getReason());
            stmt.setNull(4, java.sql.Types.INTEGER);
            stmt.setBoolean(5, loan.isAutoApproved());
            stmt.setString(6, loan.isAutoApproved() ? LoanStatusEnum.APPROVED.name() : LoanStatusEnum.PENDING.name());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Loan> getPendingLoans() {
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_PENDING_LOANS)) {
            stmt.setString(1, LoanStatusEnum.PENDING.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapResultToLoan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public boolean updateLoanStatus(int loanId, LoanStatusEnum status, int approvedBy) {
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_LOAN_STATUS)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, approvedBy);
            stmt.setInt(3, loanId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approveLoanManually(int loanId, int managerUserId) {
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(APPROVE_LOAN_MANUALLY)) {
            stmt.setString(1, LoanStatusEnum.APPROVED.name());
            stmt.setInt(2, managerUserId);
            stmt.setInt(3, loanId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Loan> getLoansByAccountId(int accountId) {
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_LOANS_BY_ACCOUNT_ID)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapResultToLoan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public List<Loan> getLoanHistoryByPan(String pan) {
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_LOAN_HISTORY_BY_PAN)) {
            stmt.setString(1, pan);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapResultToLoan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    private Loan mapResultToLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setLoanId(rs.getInt("loan_id"));
        loan.setAccountId(rs.getInt("account_id"));
        loan.setAmount(rs.getDouble("amount"));
        loan.setAppliedOn(rs.getTimestamp("applied_on").toLocalDateTime());
        loan.setApprovedBy((Integer) rs.getObject("approved_by"));
        loan.setAutoApproved(rs.getBoolean("is_auto_approved"));
        loan.setStatus(LoanStatusEnum.valueOf(rs.getString("status")));
        loan.setReason(rs.getString("reason"));
        return loan;
    }
}
