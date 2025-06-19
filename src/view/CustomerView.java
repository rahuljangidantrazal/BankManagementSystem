package view;

import model.*;
import controllers.*;

import java.util.Scanner;

import static util.InputUtil.*;
import static util.TablePrinterUtil.*;
import static constants.ViewMessages.AllViewMessages.*;

public class CustomerView {

    private final CustomerController customerController = CustomerController.getInstance();
    private final TransactionController transactionController = TransactionController.getInstance();
    private final UserController userController = UserController.getInstance();
    private final LoanController loanController = LoanController.getInstance();
    private final BranchController branchController = BranchController.getInstance();
    private final Scanner sc = new Scanner(System.in);

    public void start(User customer) {
        var accounts = customerController.getAccountsForUser(customer.getUserId());
        if (accounts.isEmpty()) {
            print(NO_ACTIVE_ACCOUNT_FOUND);
            return;
        }

        print(CUSTOMER_ACCOUNT_LIST_HEADER);
        print(ACCOUNT_TABLE_DIVIDER);
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            Branch branch = branchController.getBranchById(a.getBranchId());
            System.out.printf(ACCOUNT_TABLE_ROW_FORMAT,
                    i + 1, a.getAccountNumber(), a.getBalance(),
                    branch != null ? branch.getBranchName() : UNKNOWN,
                    branch != null ? branch.getIfscCode() : UNKNOWN);
        }
        print(ACCOUNT_TABLE_DIVIDER);

        System.out.print(SELECT_AN_ACCOUNT);
        int accChoice = Integer.parseInt(sc.nextLine()) - 1;
        if (accChoice < 0 || accChoice >= accounts.size()) {
            print(INVALID_ACCOUNT_CHOICE);
            return;
        }

        Account account = accounts.get(accChoice);
        System.out.printf(CUSTOMER_WELCOME, customer.getFirstName(), customer.getLastName());

        showDashboard(customer, account);
    }

    private void showDashboard(User customer, Account account) {
        while (true) {
            print(CUSTOMER_MENU);
            System.out.print(ENTER_CHOICE);

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> handleDeposit(account, customer);
                case 2 -> handleWithdraw(account, customer);
                case 3 -> handleViewTransactionHistory(account);
                case 4 -> handleUndoTransaction(account, customer);
                case 5 -> handleViewBalance(account);
                case 6 -> handleAddJointHolder(account, customer);
                case 7 -> handleEditProfile(customer);
                case 8 -> handleAccountDetails(account, customer);
                case 9 -> handleApplyLoan(customer, account);
                case 10 -> handleLoanHistory(account);
                case 11 -> {
                    if (handleDeleteAccount(account, customer)) {
                        UserView.loginMenu();
                        return;
                    }
                }
                case 12 -> handlePrintPassbook(account);
                case 0 -> {
                    print(LOGGING_OUT);
                    UserView.loginMenu();
                    return;
                }
                default -> {
                    print(INVALID_OPTION);
                    goBack();
                }
            }
        }
    }

    private void handleDeposit(Account account, User customer) {
        double amount = readDouble(DEPOSIT_PROMPT);
        if (amount <= 0) {
            print(DEPOSIT_AMOUNT_INVALID);
        } else {
            boolean success = customerController.deposit(account.getAccountId(), customer.getUserId(), amount);
            print(success ? DEPOSIT_SUCCESS : DEPOSIT_FAILED);
        }
        goBack();
    }

    private void handleWithdraw(Account account, User customer) {
        double bal = customerController.getBalance(account.getAccountId());
        System.out.printf(BALANCE_DISPLAY, bal);
        double amount = readDouble(WITHDRAW_PROMPT);

        if (amount <= 0) {
            print(WITHDRAW_AMOUNT_INVALID);
            goBack();
            return;
        }

        String enteredPassword = readNonEmpty(ENTER_PASSWORD);

        if (!enteredPassword.equals(customer.getPassword())) {
            print(WITHDRAW_FAILED);
            goBack();
            return;
        }

        boolean success = customerController.withdraw(account.getAccountId(), customer.getUserId(), amount);
        print(success ? WITHDRAW_SUCCESS : WITHDRAW_FAILED);

        goBack();
    }

    private void handleViewTransactionHistory(Account account) {
        var txns = transactionController.getTransactionHistory(account.getAccountId());
        if (txns.isEmpty()) {
            print(NO_TRANSACTIONS_FOUND);
        } else {
            printTransactionTable(txns);
        }
        goBack();
    }

    private void handleUndoTransaction(Account account, User customer) {
        Transaction txn = customerController.getUndoableTransaction(account.getAccountId());
        if (txn != null) {
            System.out.printf(LAST_TRANSACTION_INFO, txn.getType(), txn.getAmount());
            if (askYesOrNo(UNDO_CONFIRMATION)) {
                boolean undone = customerController.undoTransaction(txn, customer.getUserId());
                print(undone ? TRANSACTION_UNDO_SUCCESS : TRANSACTION_UNDO_FAILED);
            } else {
                print(UNDO_CANCELLED);
            }
        } else {
            print(NO_ELIGIBLE_UNDO);
        }
        goBack();
    }

    private void handleViewBalance(Account account) {
        double bal = customerController.getBalance(account.getAccountId());
        System.out.printf(BALANCE_DISPLAY, bal);
        goBack();
    }

    private void handleAddJointHolder(Account account, User customer) {
        boolean hasJoint = AccountOwnerController.getInstance().hasJointHolder(account.getAccountId());

        if (hasJoint) {
            print(JOINT_ACCOUNT_EXISTS);
        } else {
            userController.addJointHolder(account.getAccountId(), customer.getUserId());
        }

        goBack();
    }

    private void handleEditProfile(User customer) {
        userController.editUserDetails(customer.getUserId());
        goBack();
    }

    private void handleAccountDetails(Account account, User customer) {
        AccountDetails details = customerController.getAccountDetails(account.getAccountId(), customer.getUserId());
        if (details == null) {
            print(ACCOUNT_DETAILS_FAILED);
            return;
        }

        print(ACCOUNT_NUMBER_LABEL + details.getAccount().getAccountNumber());
        print(BRANCH_NAME_LABEL + details.getBranch().getBranchName());
        print(IFSC_CODE_LABEL + details.getBranch().getIfscCode());

        print(PRIMARY_USER_LABEL);
        userController.printUserDetails(details.getPrimaryHolder());

        if (!details.getJointHolders().isEmpty()) {
            print(JOINT_HOLDERS_LABEL);
            for (User joint : details.getJointHolders()) {
                userController.printUserDetails(joint);
                print(JOINT_DIVIDER);
            }
        }
        goBack();
    }

    private void handleApplyLoan(User customer, Account account) {
        if (customer.getCreditScore() < 600) {
            print(LOW_CREDIT_SCORE_LOAN_DENIED);
            return;
        }
        double amount = readDouble(LOAN_AMOUNT_PROMPT);
        String reason = readNonEmpty(LOAN_REASON_PROMPT);

        Loan loan = new Loan();
        loan.setAccountId(account.getAccountId());
        loan.setAmount(amount);
        loan.setReason(reason);

        if (amount < 5000000) {
            loan.setStatus(LoanStatusEnum.APPROVED);
            loan.setAutoApproved(true);
            if (loanController.requestLoan(loan)) {
                transactionController.deposit(account.getAccountId(), customer.getUserId(), amount);
                print(String.format(LOAN_AUTO_APPROVED, amount));
            } else {
                print(LOAN_REQUEST_FAILED);
            }
        } else {
            loan.setStatus(LoanStatusEnum.PENDING);
            loan.setAutoApproved(false);
            print(loanController.requestLoan(loan)
                    ? LOAN_REQUEST_PENDING
                    : LOAN_REQUEST_FAILED);
        }
        goBack();
    }

    private void handleLoanHistory(Account account) {
        var loans = loanController.getLoansByAccountId(account.getAccountId());
        if (loans.isEmpty()) {
            print(NO_LOANS_FOUND);
        } else {
            printLoanTable(loans);
        }
        goBack();
    }

    private boolean handleDeleteAccount(Account account, User customer) {
        if (askYesOrNo(DELETE_CONFIRMATION)) {
            boolean success = AccountOwnerController.getInstance().softDeleteAccount(account.getAccountId(),
                    customer.getUserId());
            print(success ? ACCOUNT_DELETED : ACCOUNT_DELETE_FAILED);
            return success;
        } else {
            print(DELETE_CANCELLED);
        }
        goBack();
        return false;
    }

    private void handlePrintPassbook(Account account) {
        var txns = transactionController.getTransactionHistory(account.getAccountId());
        if (txns.isEmpty()) {
            print(NO_TXN_FOR_PASSBOOK);
        } else {
            printPassbook(txns);
        }
        goBack();
    }
}
