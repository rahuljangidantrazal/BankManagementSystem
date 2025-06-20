package view;

import java.util.List;
import java.util.Scanner;

import constants.Messages;
import model.Branch;
import model.Loan;
import model.LoanStatusEnum;
import model.Transaction;
import model.User;
import controllers.LoanController;
import controllers.TransactionController;
import controllers.BranchController;
import static util.InputUtil.*;
import static util.TablePrinterUtil.*;

import static constants.ViewMessages.AllViewMessages.*;

// *********************************************************************************************************
//  *  JAVA Class Name :   ManagerView.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com)
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class handles the console-based UI for the Manager role. It provides
//  *                      functionality for managers to approve or reject loan applications,
//  *                      view loan history by customer PAN, and view transaction history by PAN
//  *                      within their branch and bank context.
//  *
//  *                      Provided Methods:
//  *                      - showDashboard(User)
//  *                      - handleApproveLoans(User)
//  *                      - handleLoanHistoryByPan()
//  *                      - handleTransactionHistoryByPan(User)
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)
//  ********************************************************************************************************

public class ManagerView {

    private final LoanController loanController = LoanController.getInstance();
    private final TransactionController transactionController = TransactionController.getInstance();
    private final BranchController branchController = BranchController.getInstance();
    private final Scanner sc = new Scanner(System.in);

    public void showDashboard(User manager) {
        while (true) {
            print(MANAGER_MENU);
            System.out.print(ENTER_OPTION);
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> handleApproveLoans(manager);
                case 2 -> handleLoanHistoryByPan();
                case 3 -> handleTransactionHistoryByPan(manager);
                case 0 -> {
                    print(LOGGING_OUT);
                    return;
                }
                default -> {
                    print(INVALID_OPTION);
                    goBack();
                }
            }
        }
    }

    private void handleApproveLoans(User manager) {
        List<Loan> pendingLoans = loanController.getPendingLoans();
        if (pendingLoans.isEmpty()) {
            print(NO_LOANS_FOUND);
            return;
        }

        for (Loan loan : pendingLoans) {
            print(String.format(LOAN_INFO_FORMAT,
                    loan.getLoanId(), loan.getAmount(), loan.getAccountId(), loan.getReason()));

            String choice = readNonEmpty(APPROVE_REJECT_SKIP_PROMPT).trim().toUpperCase();

            switch (choice) {
                case "A" -> {
                    loanController.updateLoanStatus(loan.getLoanId(), LoanStatusEnum.APPROVED, manager.getUserId());
                    print(Messages.LoanMessages.LOAN_REQUEST_APPROVED);
                }
                case "R" -> {
                    loanController.updateLoanStatus(loan.getLoanId(), LoanStatusEnum.REJECTED, manager.getUserId());
                    print(Messages.LoanMessages.LOAN_REJECTED_SUCCESS);
                }
                case "S" -> {
                    print("Skipped.");
                    continue;
                }
                default -> {
                    print("Invalid input. Skipping...");
                    continue;
                }
            }
        }
    }

    private void handleLoanHistoryByPan() {
        String pan = readNonEmpty(ENTER_PAN);
        List<Loan> loans = loanController.getLoanHistoryByPan(pan);
        if (loans.isEmpty()) {
            print(NO_LOANS_FOUND);
        } else {
            printLoanTable(loans);
        }
        goBack();
    }

    private void handleTransactionHistoryByPan(User manager) {
        String pan = readNonEmpty(ENTER_PAN);
        int branchId = manager.getBranchId();
        Branch branch = branchController.getBranchById(branchId);
        int bankId = branch.getBankId();

        List<Transaction> txns = transactionController.getAccountsByPanAndBank(pan, bankId);
        ;
        if (txns.isEmpty()) {
            print(NO_TRANSACTIONS_FOUND);
        } else {
            printTransactionTable(txns);
        }
        goBack();
    }
}
