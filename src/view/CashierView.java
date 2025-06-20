package view;

import model.Branch;
import model.Transaction;
import model.User;
import controllers.BranchController;
import controllers.TransactionController;

import static util.InputUtil.*;
import static util.TablePrinterUtil.*;
import static constants.ViewMessages.AllViewMessages.*;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   CashierView.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class handles the console-based UI for the Cashier role. It provides
//  *                      a dashboard interface for viewing customer transaction history based on PAN 
//  *                      within the cashier's branch and bank context.
//  *
//  *                      Provided Methods:
//  *                      - showDashboard(User cashier)
//  *                      - handleTransactionHistoryByPan(User manager)
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class CashierView {

    private final TransactionController transactionController = TransactionController.getInstance();
    private final BranchController branchController = BranchController.getInstance();

    public void showDashboard(User cashier) {
        while (true) {
            print(CASHIER_MENU);
            String input = readNonEmpty(CASHIER_ENTER_OPTION);

            switch (input) {
                case "1" -> handleTransactionHistoryByPan(cashier);
                case "0" -> {
                    print(CASHIER_LOGOUT_MESSAGE);
                    return;
                }
                default -> {
                    print(CASHIER_INVALID_OPTION);
                    goBack();
                }
            }
        }
    }

    private void handleTransactionHistoryByPan(User manager) {
        String pan = readNonEmpty(ENTER_CUSTOMER_PAN);
        int branchId = manager.getBranchId();
        Branch branch = branchController.getBranchById(branchId);
        int bankId = branch.getBankId();

        List<Transaction> txns = transactionController.getAccountsByPanAndBank(pan, bankId);
        if (txns.isEmpty()) {
            print(NO_TRANSACTIONS_FOUND);
        } else {
            printTransactionTable(txns);
        }
        goBack();
    }
}
