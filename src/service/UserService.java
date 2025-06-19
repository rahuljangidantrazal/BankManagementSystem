package service;

import java.util.List;

import model.*;
import repo.*;
import util.AccountUtil;
import util.validators.EmailValidator;
import util.validators.PasswordValidator;
import view.RegisterUserView;

import static util.InputUtil.*;
import static constants.Messages.UserServiceMessages.*;

public class UserService {

    private static final UserRepo userRepo = UserRepo.getInstance();
    private static final BankRepo bankRepo = BankRepo.getInstance();
    private static final BranchRepo branchRepo = BranchRepo.getInstance();
    private static final AccountRepo accountRepo = AccountRepo.getInstance();
    private static final AccountOwnerRepo accountOwnerRepo = AccountOwnerRepo.getInstance();

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User validateAdminLogin(String username, String password) {
        User user = userRepo.getUserByUsername(username);

        if (user == null) {
            print(ADMIN_NOT_FOUND);
            return null;
        }

        if (!user.isActive()) {
            print(ADMIN_INACTIVE);
            return null;
        }

        if (!user.getPassword().equals(password)) {
            print(INVALID_PASSWORD);
            return null;
        }

        if (user.getUserType() != UserTypeEnum.ADMIN) {
            print(NOT_AN_ADMIN);
            return null;
        }

        return user;
    }

    public void registerUser() {
        List<Bank> banks = bankRepo.getAllBanks();
        if (banks.isEmpty()) {
            print(NO_BANKS_FOUND_FOR_REGISTRATION);
            return;
        }

        User primaryUser = RegisterUserView.registerNewCustomer(banks);
        if (primaryUser == null)
            return;

        Bank selectedBank = bankRepo.getBankById(primaryUser.getBranchId());
        Branch selectedBranch = branchRepo.getBranchById(primaryUser.getBranchId());

        primaryUser.setUserType(UserTypeEnum.CUSTOMER);

        if (primaryUser.getUserId() <= 0) {
            if (!userRepo.insertUser(primaryUser)) {
                print(USER_REGISTRATION_FAILED);
                return;
            }
            primaryUser.setUserId(userRepo.getUserIdByUsername(primaryUser.getUsername()));
        }

        double initialDeposit = RegisterUserView.readInitialDeposit(selectedBank.getMinBalance());
        boolean isJoint = RegisterUserView.askIsJointAccount();

        Integer jointUserId = null;
        if (isJoint) {
            User jointUser = RegisterUserView.collectBasicUserDetails(true);
            if (jointUser == null)
                return;

            jointUser.setUserType(UserTypeEnum.CUSTOMER);
            jointUser.setBranchId(selectedBranch.getBranchId());

            if (!userRepo.insertUser(jointUser)) {
                print(JOINT_USER_REGISTRATION_FAILED);
                return;
            }

            jointUserId = userRepo.getUserIdByUsername(jointUser.getUsername());
        }

        String accountNumber = AccountUtil.generateAccountNumber(selectedBank.getBankName(),
                selectedBranch.getBranchId());
        Account account = new Account(accountNumber, selectedBranch.getBranchId(), initialDeposit, "ACTIVE");

        int accountId = accountRepo.insertAccount(account);
        if (accountId == -1) {
            print(ACCOUNT_CREATION_FAILED);
            return;
        }

        accountOwnerRepo.addOwner(accountId, primaryUser.getUserId(), true);
        if (isJoint && jointUserId != null) {
            accountOwnerRepo.addOwner(accountId, jointUserId, false);
        }

        RegisterUserView.printAccountCreationSummary(account, selectedBranch, primaryUser, isJoint, jointUserId);
    }

    public void printUserDetails(User user) {
        print("Name        : " + user.getFirstName() + " " + user.getLastName());
        print("Username    : " + user.getUsername());
        print("Email       : " + user.getEmail());
        print("Phone       : " + user.getPhone());
        print("Aadhar      : " + user.getAadhar());
        print("PAN         : " + user.getPan());
    }

    public void editUserDetails(int userId) {
        User user = userRepo.findById(userId);
        if (user == null || !user.isActive()) {
            print(USER_NOT_FOUND_OR_INACTIVE);
            return;
        }

        print("Editing details for: " + user.getFirstName() + " " + user.getLastName());

        user.setPhone(readValidatedField("Enter Phone [" + user.getPhone() + "]",
                input -> input.matches("\\d{10}") ? null : "Phone must be 10 digits.", user.getPhone())
                + "Enter to Skip");

        user.setEmail(readValidatedField("Enter Email [" + user.getEmail() + "]", EmailValidator::getErrorMessage,
                user.getEmail()) + "Enter to Skip");

        user.setPassword(
                readValidatedField("Enter New Password", PasswordValidator::getErrorMessage) + "Enter to Skip");

        boolean updated = userRepo.updateUser(user);
        if (updated) {
            print(USER_UPDATE_SUCCESS);
        } else {
            print(USER_UPDATE_FAILED);
        }
    }

    public void addJointHolder(int accountId, int primaryUserId) {
        Account account = accountRepo.getAccountById(accountId);
        if (account == null || !account.isActive()) {
            print(ACCOUNT_NOT_FOUND);
            return;
        }

        List<Integer> owners = accountOwnerRepo.getOwnerIdsByAccountId(accountId);
        if (owners.size() > 1) {
            print(JOINT_ACCOUNT_EXISTS);
            return;
        }

        User primaryUser = userRepo.findById(primaryUserId);
        if (primaryUser == null || !primaryUser.isActive()) {
            print(PRIMARY_USER_NOT_FOUND);
            return;
        }

        print(REGISTERING_JOINT_HOLDER);
        User jointUser = RegisterUserView.collectBasicUserDetails(true);
        if (jointUser == null)
            return;

        jointUser.setUserType(UserTypeEnum.CUSTOMER);
        jointUser.setBranchId(primaryUser.getBranchId());

        if (!userRepo.insertUser(jointUser)) {
            print(JOINT_USER_REGISTRATION_FAILED);
            return;
        }

        int jointUserId = userRepo.getUserIdByUsername(jointUser.getUsername());
        if (jointUserId == -1) {
            print(JOINT_USER_ID_FETCH_FAILED);
            return;
        }

        accountOwnerRepo.addOwner(accountId, jointUserId, false);
        print(JOINT_HOLDER_ADDED_SUCCESS);
    }

    public User validateLoginByType(String username, String password, UserTypeEnum type) {
        User user = userRepo.getUserByUsernameAndPassword(username, password);
        if (user != null && user.getUserType() == type && user.isActive())
            return user;
        return null;
    }

    public User findByAadhar(String aadhar) {
        return userRepo.findByAadhar(aadhar);
    }

    public boolean existsByPhone(String phone) {
        return userRepo.existsByPhone(phone);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean existsByAadhar(String aadhar) {
        return userRepo.existsByAadhar(aadhar);
    }

    public boolean existsByPan(String pan) {
        return userRepo.existsByPan(pan);
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public User findById(int userId) {
        return userRepo.findById(userId);
    }

    public List<String> getAllPans() {
        return userRepo.getAllPans();
    }

}
