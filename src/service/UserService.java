package service;

import java.util.List;

import model.*;
import repo.*;
import util.AccountUtil;
import view.RegisterUserView;

import static constants.Messages.UserServiceMessages.*;

// *********************************************************************************************************
//  *  JAVA Class Name :   UserService.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This service class provides business logic for user-related operations including
//  *                      registration (with support for joint accounts), login validation (admin, customer,
//  *                      employee), updating user profile, and checking existence by email, phone, PAN, etc.
//  *                      It interacts with repositories and view layers for a complete user workflow.
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

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
        try {
            User user = userRepo.getUserByUsername(username);

            if (user == null || !user.isActive() || !user.getPassword().equals(password)
                    || user.getUserType() != UserTypeEnum.ADMIN)
                return null;

            return user;

        } catch (Exception e) {
            return null;
        }
    }

    public RegistrationResult registerUser() {
        try {
            List<Bank> banks = bankRepo.getAllBanks();
            if (banks.isEmpty())
                return new RegistrationResult(false, NO_BANKS_FOUND_FOR_REGISTRATION);

            User primaryUser = RegisterUserView.registerNewCustomer(banks);
            if (primaryUser == null)
                return new RegistrationResult(false, null);

            Bank selectedBank = bankRepo.getBankById(primaryUser.getBranchId());
            Branch selectedBranch = branchRepo.getBranchById(primaryUser.getBranchId());
            primaryUser.setUserType(UserTypeEnum.CUSTOMER);

            if (primaryUser.getUserId() <= 0) {
                if (!userRepo.insertUser(primaryUser)) {
                    return new RegistrationResult(false, USER_REGISTRATION_FAILED);
                }
                primaryUser.setUserId(userRepo.getUserIdByUsername(primaryUser.getUsername()));
            }

            double initialDeposit = RegisterUserView.readInitialDeposit(selectedBank.getMinBalance());
            boolean isJoint = RegisterUserView.askIsJointAccount();

            Integer jointUserId = null;
            User jointUser = null;

            if (isJoint) {
                jointUser = RegisterUserView.collectBasicUserDetails(true);
                if (jointUser == null)
                    return new RegistrationResult(false, null);

                jointUser.setUserType(UserTypeEnum.CUSTOMER);
                jointUser.setBranchId(selectedBranch.getBranchId());

                if (!userRepo.insertUser(jointUser)) {
                    return new RegistrationResult(false, JOINT_USER_REGISTRATION_FAILED);
                }
                jointUserId = userRepo.getUserIdByUsername(jointUser.getUsername());
            }

            String accountNumber = AccountUtil.generateAccountNumber(
                    selectedBank.getBankName(), selectedBranch.getBranchId());
            Account account = new Account(accountNumber, selectedBranch.getBranchId(), initialDeposit, "ACTIVE");

            int accountId = accountRepo.insertAccount(account);
            if (accountId == -1) {
                return new RegistrationResult(false, ACCOUNT_CREATION_FAILED);
            }

            accountOwnerRepo.addOwner(accountId, primaryUser.getUserId(), true);
            if (isJoint && jointUserId != null) {
                accountOwnerRepo.addOwner(accountId, jointUserId, false);
            }

            return new RegistrationResult(true, null, account, selectedBranch, primaryUser, isJoint, jointUser);

        } catch (Exception e) {
            return new RegistrationResult(false, "Unexpected error during registration: " + e.getMessage());
        }
    }

    public User editUserDetails(int userId) {
        User user = userRepo.findById(userId);
        return (user != null && user.isActive()) ? user : null;
    }

    public boolean updateUserDetails(int userId, String phone, String email, String password) {
        User user = userRepo.findById(userId);
        if (user == null || !user.isActive())
            return false;

        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        return userRepo.updateUser(user);
    }

    public AddJointResult addJointHolder(int accountId, int primaryUserId) {
        try {
            Account account = accountRepo.getAccountById(accountId);
            if (account == null || !account.isActive()) {
                return new AddJointResult(false, ACCOUNT_NOT_FOUND);
            }

            List<Integer> owners = accountOwnerRepo.getOwnerIdsByAccountId(accountId);
            if (owners.size() > 1) {
                return new AddJointResult(false, JOINT_ACCOUNT_EXISTS);
            }

            User primaryUser = userRepo.findById(primaryUserId);
            if (primaryUser == null || !primaryUser.isActive()) {
                return new AddJointResult(false, PRIMARY_USER_NOT_FOUND);
            }

            User jointUser = RegisterUserView.collectBasicUserDetails(true);
            if (jointUser == null)
                return new AddJointResult(false, null);

            jointUser.setUserType(UserTypeEnum.CUSTOMER);
            jointUser.setBranchId(primaryUser.getBranchId());

            if (!userRepo.insertUser(jointUser)) {
                return new AddJointResult(false, JOINT_USER_REGISTRATION_FAILED);
            }

            int jointUserId = userRepo.getUserIdByUsername(jointUser.getUsername());
            if (jointUserId == -1) {
                return new AddJointResult(false, JOINT_USER_ID_FETCH_FAILED);
            }

            accountOwnerRepo.addOwner(accountId, jointUserId, false);
            return new AddJointResult(true, JOINT_HOLDER_ADDED_SUCCESS);

        } catch (Exception e) {
            return new AddJointResult(false, "Unexpected error adding joint holder: " + e.getMessage());
        }
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

    public static class RegistrationResult {
        public boolean success;
        public String message;
        public Account account;
        public Branch branch;
        public User primaryUser;
        public boolean isJoint;
        public User jointUser;

        public RegistrationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public RegistrationResult(boolean success, String message, Account account, Branch branch, User primaryUser,
                boolean isJoint, User jointUser) {
            this.success = success;
            this.message = message;
            this.account = account;
            this.branch = branch;
            this.primaryUser = primaryUser;
            this.isJoint = isJoint;
            this.jointUser = jointUser;
        }
    }

    public static class AddJointResult {
        public boolean success;
        public String message;

        public AddJointResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
