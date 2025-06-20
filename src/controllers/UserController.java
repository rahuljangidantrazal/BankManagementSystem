package controllers;

import java.util.List;

import model.User;
import model.UserTypeEnum;
import service.UserService;

// *********************************************************************************************************
//  *  JAVA Class Name :   UserController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class serves as the interface between the view layer and the 
//  *                      UserService. It manages all user-related operations such as user registration, 
//  *                      login validation (admin and typed users), profile updates, joint holder management, 
//  *                      and various field-level validations (email, phone, Aadhar, PAN, username).
//  *                      It also supports fetching user details by ID or Aadhar, and listing PANs in use.
//  *                      This class follows the Singleton pattern to ensure consistent access across the system.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

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

    public User editUserDetails(int userId) {
        return userService.editUserDetails(userId);
    }

    public boolean updateUserDetails(int userId, String newPhone, String newEmail, String newPassword) {
        return userService.updateUserDetails(userId, newPhone, newEmail, newPassword);
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
