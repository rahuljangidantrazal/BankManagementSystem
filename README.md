
# Bank Management System (Console-Based Java Application)

**Author:** Rahul Jangid (rahul.jangid@antrazal.com)  
**Company:** Antrazal  
**Date:** 20-06-2025

---

## Overview

This is a console-based Bank Management System written in Java that supports multiple user roles and functionalities. It provides a secure and interactive command-line interface for managing banks, branches, customers, employees, and their financial transactions.

The system supports the following user roles:

- **Admin:** Manages banks, branches, and employees.
- **Customer:** Manages their own accounts including deposits, withdrawals, loans, and profile editing.
- **Cashier:** Views customer transaction history within their branch.
- **Manager:** Views and approves/rejects loan applications, and views transaction and loan history within their bank and branch.

---

## Key Features

- Multi-bank and multi-branch support.
- Role-based access control for Admin, Customer, Cashier, and Manager.
- Customer account creation with validations for Aadhar, PAN, Email, Phone, and Password.
- Joint account support with multiple holders.
- Secure login flows for all user roles.
- Deposit, withdrawal with password validation, and undo last transaction (within a time window).
- Loan application with automatic approval for loans below a threshold and manager approval for large loans.
- Transaction history and loan history viewing capabilities.
- Account soft deletion and profile editing.
- Passbook printing (transaction summary).
- JDBC-based persistent storage with database initialization at startup.

---

## Project Structure

### Entry Point

- **Main.java**  
  The main entry class that starts the application by calling `MainView.start()`, which displays the main menu and routes users accordingly.

### Views (Console UI)

- **MainView.java**  
  Displays the main application menu with options to login as Admin, login as User, register as a Customer, or exit.

- **UserView.java**  
  Handles user registration (currently customer only) and login menu for Customer, Cashier, and Manager roles. Redirects users to their role-specific dashboards after login.

- **RegisterUserView.java**  
  Interactive UI for registering new customers, including selecting bank and branch, validating user inputs (Aadhar, PAN, Email, Phone, Password), supporting joint accounts, and initial deposit.

- **CustomerView.java**  
  Provides the customer dashboard to manage their accounts: deposit, withdraw, view balance, transaction history, undo transactions, loan application, passbook printing, and profile editing.

- **CashierView.java**  
  Allows cashiers to view customer transaction history by PAN within their branch and bank context.

- **ManagerView.java**  
  Allows managers to approve or reject loan applications, view loan history by customer PAN, and view transaction history by PAN within their branch and bank.

### Controllers

Controllers implement business logic and interact with data access layers (DAOs). Examples include:

- `UserController`  
- `CustomerController`  
- `TransactionController`  
- `LoanController`  
- `BranchController`  
- `AccountOwnerController`

### Utilities

- **Validators:** Validate inputs such as Aadhar, PAN, Email, Phone, and Password.
- **DBConfig:** Initializes and manages database connection and schema setup.
- **InputUtil:** Utility methods for input reading and validation.
- **TablePrinterUtil:** Utility for formatting tables for transaction and loan history.

---

## How to Run

1. Ensure you have Java installed (JDK 11 or higher recommended).
2. Compile all `.java` files.
3. Run the `Main` class.
4. The application will initialize the database and display the main menu.
5. Follow the on-screen prompts to register users, login, and perform banking operations.

---

## Sample Flow

- **Start application** → Choose login or register.
- **Admin login:** Create banks, branches, and employees.
- **Customer registration:** Select bank, branch, enter personal details and initial deposit, optionally add joint account holder.
- **User login:** Depending on role, access dashboards to perform permitted actions.
- **Manager:** Approve or reject pending loans, view histories.
- **Cashier:** View customer transaction histories.
- **Customer:** Deposit, withdraw, apply for loans, undo transactions, print passbook.

---

## Notes

- All sensitive data inputs like passwords are validated with complexity requirements.
- Undo functionality is time-limited and only applies to the most recent transaction.
- Loan applications under ₹50,00,000 are auto-approved; larger amounts require manager approval.
- Accounts can be soft-deleted and restored by admin if needed.
- Extensive input validations ensure data integrity.

---

## Contact

For queries or contributions, contact Rahul Jangid at rahul.jangid@antrazal.com.

---

## License

This project is proprietary to Antrazal.

---

*Last updated: 20 June 2025*
