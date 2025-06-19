package controllers;

import java.util.List;

import model.User;
import model.UserTypeEnum;
import service.UserService;

public class UserController {

    private static final UserController instance = new UserController();
    private static final UserService userService = UserService.getInstance();

    private UserController() {
    }

    public static UserController getInstance() {
        return instance;
    }

    public User validateAdminLogin(String username, String password) {
        return userService.validateAdminLogin(username, password);
    }

    public User validateLogin(String username, String password, UserTypeEnum type) {
        return userService.validateLoginByType(username, password, type);
    }

    public void registerUser() {
        userService.registerUser();
    }

    public void printUserDetails(User user) {
        userService.printUserDetails(user);
    }

    public void editUserDetails(int userId) {
        userService.editUserDetails(userId);
    }

    public void addJointHolder(int accountId, int primaryUserId) {
        userService.addJointHolder(accountId, primaryUserId);
    }

    public User findByAadhar(String aadhar) {
        return userService.findByAadhar(aadhar);
    }

    public boolean existsByPhone(String phone) {
        return userService.existsByPhone(phone);
    }

    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    public boolean existsByAadhar(String aadhar) {
        return userService.existsByAadhar(aadhar);
    }

    public boolean existsByPan(String pan) {
        return userService.existsByPan(pan);
    }

    public boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }

    public User findById(int userId) {
        return userService.findById(userId);
    }

    public List<String> getAllPans() {
        return userService.getAllPans();
    }

}
