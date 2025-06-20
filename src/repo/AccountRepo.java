package repo;

import model.Account;
import util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static util.InputUtil.*;

import constants.Messages;
import static constants.RepoMessages.*;


// *********************************************************************************************************
//  *  JAVA Class Name :   AccountRepo.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This repository class manages direct database operations related to bank accounts. 
//  *                      It includes methods for inserting new accounts, handling deposits and withdrawals, 
//  *                      fetching balance and account information, soft closing accounts, and retrieving 
//  *                      accounts by user or PAN. It follows the Singleton pattern to ensure a single 
//  *                      instance is used across the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************


public class AccountRepo {

    private static AccountRepo instance;

    private AccountRepo() {
    }

    public static AccountRepo getInstance() {
        if (instance == null) {
            instance = new AccountRepo();
        }
        return instance;
    }

    public int insertAccount(Account account) {
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, account.getAccountNumber());
            ps.setInt(2, account.getBranchId());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getStatus());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Account getAccountById(int accountId) {
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("account_number"),
                        rs.getInt("branch_id"),
                        rs.getDouble("balance"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deposit(int accountId, double amount) {
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(DEPOSIT_TO_ACCOUNT)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean withdraw(int accountId, double amount) {
        try (Connection conn = DBConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(WITHDRAW_FROM_ACCOUNT)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getBalance(int accountId) {
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BALANCE_BY_ACCOUNT_ID)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Account> getAccountsByUserId(int userId) {
        List<Account> list = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ACCOUNTS_BY_USER_ID)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Account(
                        rs.getInt("account_id"),
                        rs.getString("account_number"),
                        rs.getInt("branch_id"),
                        rs.getDouble("balance"),
                        rs.getString("status")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ACCOUNT_BY_ACCOUNT_NUMBER)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("account_number"),
                        rs.getInt("branch_id"),
                        rs.getDouble("balance"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean closeAccount(int accountId) {
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(CLOSE_ACCOUNT)) {
            ps.setInt(1, accountId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Account> getAccountsByPan(String pan) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ACCOUNTS_BY_PAN)) {

            stmt.setString(1, pan);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("account_id"));
                acc.setAccountNumber(rs.getString("account_number"));
                acc.setBranchId(rs.getInt("branch_id"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setStatus(rs.getString("status"));
                accounts.add(acc);
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROER_FETCHING_BY_PAN + e.getMessage());
        }

        return accounts;
    }

}
