package view;

import model.Bank;
import model.Branch;
import model.User;
import model.UserTypeEnum;
import controllers.*;
import model.Transaction;
import static util.TablePrinterUtil.printTransactionTable;
import util.InputUtil;
import util.validators.*;

import java.util.List;
import java.util.Scanner;

import static util.InputUtil.*;
import static constants.ViewMessages.AllViewMessages.*;
import static constants.Messages.BankMessages;

public class AdminView {
    private static final Scanner sc = new Scanner(System.in);
    private static final AdminController adminController = AdminController.getInstance();
    private static final UserController userController = UserController.getInstance();
    private static final TransactionController transactionController = TransactionController.getInstance();
    private static final BankController bankController = BankController.getInstance();

    public static void login() {
        System.out.print(ENTER_ADMIN_USERNAME);
        String username = sc.nextLine();
        System.out.print(ENTER_ADMIN_PASSWORD);
        String password = sc.nextLine();

        User admin = userController.validateAdminLogin(username, password);
        if (admin != null) {
            adminMenu();
        } else {
            print(LOGIN_FAILED);
        }
    }

    private static void adminMenu() {
        while (true) {
            print(ADMIN_MENU_HEADER);
            System.out.print(ENTER_OPTION);
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1 -> handleCreateBank();
                    case 2 -> handleCreateBranch();
                    case 3 -> handleCreateEmployee();
                    case 4 -> adminController.viewBanksAndBranches();
                    case 5 -> handleTransactionHistoryByPan();
                    case 0 -> {
                        print(LOGGED_OUT);
                        return;
                    }
                    default -> print(INVALID_OPTION);
                }
            } catch (NumberFormatException e) {
                print(ENTER_VALID_NUMBER);
            }
        }
    }

    private static void handleCreateBank() {
        String name = readNonEmpty(ENTER_BANK_NAME);
        double minBalance = Double.parseDouble(readNonEmpty(ENTER_MIN_BALANCE));

        boolean success = adminController.createBank(name, minBalance);
        print(success ? BANK_CREATED_SUCCESS : BANK_CREATION_FAILED);

        Integer bankId = adminController.getBankIdByName(name);
        if (bankId == null)
            return;

        if (InputUtil.askYesOrNo(ASK_CREATE_BRANCH)) {
            handleCreateBranch(bankId);
        }

        List<Branch> branches = adminController.getBranchesByBankId(bankId);
        if (!branches.isEmpty()) {
            Branch latest = branches.get(branches.size() - 1);
            if (InputUtil.askYesOrNo(ASK_CREATE_EMPLOYEE)) {
                handleCreateEmployee(bankId, latest.getBranchId());
            }
        }
    }

    private static void handleCreateBranch() {
        List<Bank> banks = adminController.getAllBanks();
        if (banks.isEmpty()) {
            print(NO_BANKS_FOUND);
            return;
        }

        banks.forEach(b -> print(DASH + b.getBankName()));
        String bankName = readNonEmpty(ENTER_BANK_NAME);
        Integer bankId = adminController.getBankIdByName(bankName);
        if (bankId == null) {
            print(INVALID_BANK_NAME);
            return;
        }

        handleCreateBranch(bankId);
    }

    private static void handleCreateBranch(int bankId) {
        String branchName = readNonEmpty(ENTER_BRANCH_NAME);
        boolean success = adminController.createBranch(bankId, branchName);
        print(success ? BRANCH_CREATED_SUCCESS : BRANCH_CREATION_FAILED);
    }

    private static void handleCreateEmployee() {
        List<Bank> banks = adminController.getAllBanks();
        if (banks.isEmpty()) {
            print(NO_BANKS_FOUND);
            return;
        }

        banks.forEach(b -> print(DASH + b.getBankName()));
        String bankName = readNonEmpty(ENTER_BANK_NAME);
        Integer bankId = adminController.getBankIdByName(bankName);
        if (bankId == null) {
            print(INVALID_BANK_NAME);
            return;
        }

        List<Branch> branches = adminController.getBranchesByBankId(bankId);
        if (branches.isEmpty()) {
            print(NO_BRANCHES_FOUND);
            return;
        }

        print(AVAILABLE_BRANCHES);
        branches.forEach(b -> System.out.printf(DISPLAY_AVAILABLE_BRANCHES,
                b.getBranchId(), b.getBranchName(), b.getIfscCode()));

        String input = readNonEmpty(ENTER_BRANCH_ID_OR_NAME);
        Branch selectedBranch = null;

        try {
            int branchId = Integer.parseInt(input);
            selectedBranch = branches.stream()
                    .filter(b -> b.getBranchId() == branchId)
                    .findFirst()
                    .orElse(null);
        } catch (NumberFormatException e) {
            selectedBranch = branches.stream()
                    .filter(b -> b.getBranchName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);
        }

        if (selectedBranch == null) {
            print(BRANCH_NOT_FOUND);
            return;
        }

        handleCreateEmployee(bankId, selectedBranch.getBranchId());
    }

    private static void handleCreateEmployee(int bankId, int branchId) {
        String firstName = readNonEmpty(ENTER_FIRST_NAME);
        String lastName = readNonEmpty(ENTER_LAST_NAME);
        String phone = readValidatedField(ENTER_PHONE,
                input -> input.matches(PHONE_REGEX) ? null : PHONE_INVALID);
        String email = readValidatedField(ENTER_EMAIL, EmailValidator::getErrorMessage);
        String aadhar = readValidatedField(ENTER_AADHAR, AadharValidator::getErrorMessage);
        String pan = readValidatedField(ENTER_PAN, PanValidator::getErrorMessage);
        String username = readNonEmpty(ENTER_USERNAME);
        String password = readValidatedField(ENTER_PASSWORD, PasswordValidator::getErrorMessage);
        UserTypeEnum type = chooseEmployeeType(branchId);

        try {
            User user = new User(firstName, lastName, phone, email, aadhar, pan, username, password, type, branchId);
            boolean success = adminController.createEmployee(user);
            print(success ? EMPLOYEE_CREATED : EMPLOYEE_CREATION_FAILED);
        } catch (IllegalArgumentException e) {
            print(EMPLOYEE_CREATION_ERROR_PREFIX + e.getMessage());
        }
    }

    public static UserTypeEnum chooseEmployeeType(int branchId) {
        while (true) {
            print(EMPLOYEE_TYPE_MENU);
            System.out.print(ENTER_EMPLOYEE_TYPE_CHOICE);
            String choice = sc.nextLine().trim();

            if (choice.equals("1"))
                return UserTypeEnum.CASHIER;

            if (choice.equals("2")) {
                if (adminController.branchHasManager(branchId)) {
                    print(MANAGER_EXISTS);
                } else {
                    return UserTypeEnum.MANAGER;
                }
            } else {
                print(ENTER_INVALID_EMPLOYEE_TYPE_CHOICE);
            }
        }
    }

    private static void handleTransactionHistoryByPan() {
        List<String> panList = userController.getAllPans();

        if (panList.isEmpty()) {
            print(NO_PAN_RECORD);
            return;
        }

        print(PAN_LIST);
        panList.forEach(p -> print(DASH + p));

        String pan = readValidatedField(ENTER_PAN, PanValidator::getErrorMessage);

        List<String> banks = bankController.getBankNamesByPan(pan);
        if (banks.isEmpty()) {
            print(NO_BANKS_FOUND);
            goBack();
            return;
        }

        print(BankMessages.AVAILABLE_BANKS);
        banks.forEach(b -> print(DASH + b));

        String bankName = readNonEmpty(ENTER_BANK_NAME);

        boolean matchFound = banks.stream()
                .anyMatch(b -> b.equalsIgnoreCase(bankName));

        if (!matchFound) {
            print(INVALID_BANK_NAME);
            goBack();
            return;
        }

        List<Transaction> txns = transactionController.getTransactionsByPan(pan, bankName);

        if (txns.isEmpty()) {
            print(NO_TXN_FOR_PASSBOOK);
        } else {
            printTransactionTable(txns);
        }

        goBack();
    }

}
