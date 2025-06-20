package model;

// *********************************************************************************************************

//  *  JAVA Class Name :   User.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class represents a user in the banking system. It encapsulates personal 
//  *                      and authentication details such as name, contact info, Aadhar, PAN, username, 
//  *                      password, user type (e.g., Customer, Cashier, Manager), and associated branch. 
//  *                      It also includes user-specific metadata like credit score and active status.
//  *                      This class is central to managing both customers and employees within the system.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String aadhar;
    private String pan;
    private String username;
    private String password;
    private UserTypeEnum userType;
    private Integer branchId;
    private int creditScore;
    private boolean isActive;

    public User() {
    }

    public User(String firstName, String lastName, String phone, String email,
            String aadhar, String pan, String username, String password,
            UserTypeEnum userType, Integer branchId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.aadhar = aadhar;
        this.pan = pan;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.branchId = branchId;
        this.creditScore = 750;
        this.isActive = true;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
