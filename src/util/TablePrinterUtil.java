package util;

import constants.Constants;
import model.Loan;
import model.Transaction;
import static util.InputUtil.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   TablePrinterUtil.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This utility class handles formatted printing of loan and transaction data 
//  *                      in a tabular format for the console UI. It formats and displays lists of 
//  *                      transactions and loans for better readability and user experience.
//  *
//  *                      Provided Methods:
//  *                      - printLoanTable(List<Loan>)
//  *                      - printTransactionTable(List<Transaction>)
//  *                      - printPassbook(List<Transaction>)
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class TablePrinterUtil {

    public static void printLoanTable(List<Loan> loans) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.Format.DATE_FORMAT);

        print(Constants.Table.LOAN_BORDER);
        System.out.printf(Constants.Table.LOAN_HEADER_FORMAT,
                "Amount", "Purpose", "Status", "Date");
        print(Constants.Table.LOAN_BORDER);

        for (Loan loan : loans) {
            String date = loan.getAppliedOn().toLocalDate().format(dateFormatter);
            String status = loan.getStatus().name();

            System.out.printf(Constants.Table.LOAN_ROW_FORMAT,
                    loan.getAmount(),
                    loan.getReason(),
                    status,
                    date);
        }

        print(Constants.Table.LOAN_BORDER);
    }

    public static void printTransactionTable(List<Transaction> txns) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.Format.DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Constants.Format.TIME_FORMAT);

        System.out.printf(Constants.Table.TRANSACTION_HEADER_FORMAT,
                "Amount", "Type", "Performed By", "Date", "Time");
        print(Constants.Table.TRANSACTION_BORDER);

        for (Transaction txn : txns) {
            String date = txn.getTimestamp().toLocalDate().format(dateFormatter);
            String time = txn.getTimestamp().toLocalTime().format(timeFormatter);

            System.out.printf(Constants.Table.TRANSACTION_ROW_FORMAT,
                    txn.getAmount(),
                    txn.getType(),
                    txn.getPerformedByName(),
                    date,
                    time);
        }
    }

    public static void printPassbook(List<Transaction> txns) {
        if (txns == null || txns.isEmpty()) {
            print(Constants.Texts.NO_TRANSACTIONS);
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.Format.DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Constants.Format.TIME_FORMAT);

        print(Constants.Texts.PASSBOOK_TITLE);
        System.out.printf(Constants.Table.PASSBOOK_HEADER_FORMAT,
                "ID", "Type", "Amount", "Date", "Time", "Status");
        print(Constants.Table.PASSBOOK_BORDER);

        for (Transaction txn : txns) {
            String date = txn.getTimestamp().toLocalDate().format(dateFormatter);
            String time = txn.getTimestamp().toLocalTime().format(timeFormatter);
            String status = txn.isUndone() ? Constants.Texts.UNDONE : Constants.Texts.OK;

            System.out.printf(Constants.Table.PASSBOOK_ROW_FORMAT,
                    txn.getTransactionId(),
                    txn.getType(),
                    txn.getAmount(),
                    date,
                    time,
                    status);
        }

        print(Constants.Texts.PASSBOOK_END);
    }
}
