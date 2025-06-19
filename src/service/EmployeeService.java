package service;

import model.Loan;
import model.Transaction;
import model.Account;
import repo.AccountRepo;
import repo.LoanRepo;
import repo.TransactionRepo;
import static util.InputUtil.*;

import java.util.List;

import static util.TablePrinterUtil.printLoanTable;
import static util.TablePrinterUtil.printTransactionTable;
import constants.Messages;

public class EmployeeService {

    private static final EmployeeService instance = new EmployeeService();

    private final LoanRepo loanRepo = LoanRepo.getInstance();
    private final TransactionRepo transactionRepo = TransactionRepo.getInstance();
    private final AccountRepo accountRepo = AccountRepo.getInstance();

    private EmployeeService() {}

    public static EmployeeService getInstance() {
        return instance;
    }

    // Transaction history by PAN
    public void showTransactionHistoryByPan(String pan) {
        List<Account> accounts = accountRepo.getAccountsByPan(pan);
        if (accounts.isEmpty()) {
            print(Messages.EmployeeMessages.NO_ACCOUNTS_FOUND_FOR_PAN + pan);
            return;
        }

        List<Transaction> transactions = accounts.stream()
                .flatMap(account -> transactionRepo.getTransactionsByAccountId(account.getAccountId()).stream())
                .toList();

        if (transactions.isEmpty()) {
            print(Messages.EmployeeMessages.NO_TRANSACTIONS_FOUND);
        } else {
            printTransactionTable(transactions);
        }
    }

    // Loan history by PAN
    public void showLoanHistoryByPan(String pan) {
        List<Loan> loans = loanRepo.getLoanHistoryByPan(pan);
        if (loans.isEmpty()) {
            print(Messages.EmployeeMessages.NO_LOANS_FOUND_FOR_PAN + pan);
        } else {
            printLoanTable(loans);
        }
    }
}
