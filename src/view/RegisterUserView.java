package view;

import model.Account;
import model.Bank;
import model.Branch;
import model.User;
import controllers.AccountOwnerController;
import controllers.BranchController;
import controllers.UserController;
import util.validators.AadharValidator;
import util.validators.EmailValidator;
import util.validators.PanValidator;
import util.validators.PasswordValidator;

import static constants.ViewMessages.AllViewMessages.*;

import java.util.List;
import java.util.Scanner;

import constants.ViewMessages.AllViewMessages;

import static util.InputUtil.*;

// *********************************************************************************************************
//  *  JAVA Class Name :   RegisterUserView.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com)
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class handles the console-based UI for registering new users/customers.
//  *                      It provides interactive flows to select a bank and branch, validate user details,
//  *                      support joint accounts, collect initial deposits, and display account creation summary.
//  *
//  *                      Provided Methods:
//  *                      - registerNewCustomer(List<Bank>)
//  *                      - selectBank(List<Bank>)
//  *                      - selectBranch(List<Branch>)
//  *                      - collectBasicUserDetails(boolean, String)
//  *                      - collectBasicUserDetails(boolean)
//  *                      - readInitialDeposit(double)
//  *                      - askIsJointAccount()
//  *                      - printAccountCreationSummary(Account, Branch, User, boolean, Integer)
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)
//  ********************************************************************************************************

public class RegisterUserView {

    private static final Scanner sc = new Scanner(System.in);
    private static final UserController userController = UserController.getInstance();
    private static final BranchController branchController = BranchController.getInstance();
    private static final AccountOwnerController accountOwnerController = AccountOwnerController.getInstance();

    public static User registerNewCustomer(List<Bank> banks) {
        Bank selectedBank = selectBank(banks);
        if (selectedBank == null)
            return null;

        List<Branch> branches = branchController.getBranchesByBankId(selectedBank.getBankId());
        Branch selectedBranch = selectBranch(branches);
        if (selectedBranch == null)
            return null;

        String aadhar = readValidatedField(ENTER_AADHAR, AadharValidator::getErrorMessage);

        User existingUser = userController.findByAadhar(aadhar);
        if (existingUser != null) {
            boolean alreadyHasAccount = accountOwnerController.hasAccountInBank(existingUser.getUserId(),
                    selectedBank.getBankId());
            if (alreadyHasAccount) {
                print(ACCOUNT_EXISTS);
                return null;
            }

            print(OPENING_ACC);
            existingUser.setBranchId(selectedBranch.getBranchId());
            return existingUser;
        }

        User newUser = collectBasicUserDetails(false, aadhar);
        newUser.setBranchId(selectedBranch.getBranchId());

        return newUser;
    }

    public static Bank selectBank(List<Bank> banks) {
        if (banks.isEmpty()) {
            print(NO_BANKS_FOUND);
            return null;
        }

        while (true) {
            print(AVAILABLE_BRANCHES);
            for (Bank bank : banks) {
                System.out.printf(AllViewMessages.DISPLAY_BANK_INFO, bank.getBankName(), bank.getMinBalance());
            }

            System.out.print(ENTER_BANK_NAME);
            String bankName = sc.nextLine().trim();

            Bank selected = banks.stream()
                    .filter(b -> b.getBankName().equalsIgnoreCase(bankName))
                    .findFirst()
                    .orElse(null);

            if (selected != null)
                return selected;

            print(INVALID_BANK_NAME);
        }
    }

    public static Branch selectBranch(List<Branch> branches) {
        if (branches.isEmpty()) {
            print(NO_BRANCHES_FOUND);
            return null;
        }

        while (true) {
            print(AVAILABLE_BRANCHES);
            for (Branch branch : branches) {
                System.out.printf(AllViewMessages.DISPLAY_BRANCH_INFO, branch.getBranchName(), branch.getIfscCode());
            }

            System.out.print(ENTER_BRANCH_NAME);
            String branchName = sc.nextLine().trim();

            Branch selected = branches.stream()
                    .filter(b -> b.getBranchName().equalsIgnoreCase(branchName))
                    .findFirst()
                    .orElse(null);

            if (selected != null)
                return selected;

            print(INVALID_OPTION);
        }
    }

    public static User collectBasicUserDetails(boolean isJoint, String existingAadhar) {
        String prefix = isJoint ? AllViewMessages.JOINT_ACCOUNT_PREFIX : "";

        String firstName = readNonEmpty(prefix + ENTER_FIRST_NAME);
        String lastName = readNonEmpty(prefix + ENTER_LAST_NAME);

        String phone;
        while (true) {
            phone = readValidatedField(prefix + ENTER_PHONE,
                    input -> input.matches(PHONE_REGEX) ? null : PHONE_INVALID);
            if (userController.existsByPhone(phone)) {
                print(prefix + ALREADY_EXISTS);
            } else
                break;
        }

        String email;
        while (true) {
            email = readValidatedField(prefix + ENTER_EMAIL, EmailValidator::getErrorMessage);
            if (userController.existsByEmail(email)) {
                print(prefix + ALREADY_EXISTS);
            } else
                break;
        }

        String aadhar;
        if (existingAadhar != null) {
            aadhar = existingAadhar;
        } else {
            while (true) {
                aadhar = readValidatedField(prefix + ENTER_AADHAR, AadharValidator::getErrorMessage);
                if (userController.existsByAadhar(aadhar)) {
                    print(prefix + ALREADY_EXISTS);
                } else
                    break;
            }
        }

        String pan;
        while (true) {
            pan = readValidatedField(prefix + ENTER_PAN, PanValidator::getErrorMessage);
            if (userController.existsByPan(pan)) {
                print(prefix + ALREADY_EXISTS);
            } else
                break;
        }

        String username;
        while (true) {
            username = readNonEmpty(prefix + ENTER_USERNAME);
            if (userController.existsByUsername(username)) {
                print(prefix + ALREADY_EXISTS);
            } else
                break;
        }

        String password = readValidatedField(prefix + ENTER_PASSWORD, PasswordValidator::getErrorMessage);

        return new User(firstName, lastName, phone, email, aadhar, pan, username, password, null, -1);
    }

    public static double readInitialDeposit(double minBalance) {
        while (true) {
            System.out.printf(AllViewMessages.INITIAL_DEPOSIT_PROMPT, minBalance);
            try {
                double amount = Double.parseDouble(sc.nextLine());
                if (amount >= minBalance)
                    return amount;
                System.out.printf(AllViewMessages.MIN_BALANCE_ERROR + "%n", minBalance);
            } catch (NumberFormatException e) {
                print(AllViewMessages.INVALID_AMOUNT_ERROR);
            }
        }
    }

    public static boolean askIsJointAccount() {
        return askYesOrNo(AllViewMessages.IS_JOINT_ACCOUNT_PROMPT);
    }

    public static User collectBasicUserDetails(boolean isJoint) {
        return collectBasicUserDetails(isJoint, null);
    }

    public static void printAccountCreationSummary(Account account, Branch branch, User primaryUser, boolean isJoint,
            Integer jointUserId) {
        print(ACCOUNT_CREATED_HEADER);
        print(ACCOUNT_NUMBER + account.getAccountNumber());
        print(BRANCH_NAME + branch.getBranchName());
        print(IFSC_CODE + branch.getIfscCode());
        print(SECTION_SEPARATOR);
        print(PRIMARY_USER);
        printUserDetails(primaryUser);

        if (isJoint && jointUserId != null) {
            User jointUser = UserController.getInstance().findById(jointUserId);
            print(JOINT_ACCOUNT_HOLDER);
            printUserDetails(jointUser);
        }
        print(SECTION_END);
    }

    private static void printUserDetails(User user) {
        print(USER_NAME + user.getFirstName() + " " + user.getLastName());
        print(USER_USERNAME + user.getUsername());
        print(USER_EMAIL + user.getEmail());
        print(USER_PHONE + user.getPhone());
        print(USER_AADHAR + user.getAadhar());
        print(USER_PAN + user.getPan());
    }

}
