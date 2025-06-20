package repo;

import util.DBConfig;
import constants.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static util.InputUtil.*;
import static constants.RepoMessages.*;

import model.Bank;


// *********************************************************************************************************
//  *  JAVA Class Name :   BankRepo.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This repository class manages direct database interactions related to banks.
//  *                      It provides functionality for creating banks, checking bank existence, retrieving 
//  *                      banks by ID or name, and listing banks associated with a PAN. It ensures no duplicate 
//  *                      bank creation and uses the Singleton pattern for consistent access across the system.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************



public class BankRepo {

    private static BankRepo instance;

    private BankRepo() {
    }

    public static BankRepo getInstance() {
        if (instance == null) {
            instance = new BankRepo();
        }
        return instance;
    }

    public boolean bankExistsByName(String bankName) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(BANK_EXISTS_BY_NAME)) {

            stmt.setString(1, bankName.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_CHECKING_BANK + e.getMessage());
            return false;
        }
    }

    public boolean insertBank(Bank bank) {
        if (bankExistsByName(bank.getBankName())) {
            print(Messages.RepoMessages.BANK_ALREADY_EXISTS + bank.getBankName());
            return false;
        }

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(INSERT_BANK)) {

            pstmt.setString(1, bank.getBankName());
            pstmt.setDouble(2, bank.getMinBalance());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_INSERTING_BANK + e.getMessage());
            return false;
        }
    }

    public List<Bank> getAllBanks() {
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(ALL_FROM_BANK);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bank bank = new Bank();
                bank.setBankId(rs.getInt("bank_id"));
                bank.setBankName(rs.getString("bank_name"));
                bank.setMinBalance(rs.getDouble("min_balance"));
                banks.add(bank);
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BANKS + e.getMessage());
        }

        return banks;
    }

    public Integer getBankIdByName(String bankName) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_BANK_ID_BY_NAME)) {

            stmt.setString(1, bankName.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("bank_id");
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BANK + e.getMessage());
        }

        return null;
    }

    public Bank getBankById(int bankId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_BANK_BY_ID)) {

            stmt.setInt(1, bankId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bank bank = new Bank();
                bank.setBankId(rs.getInt("bank_id"));
                bank.setBankName(rs.getString("bank_name"));
                bank.setMinBalance(rs.getDouble("min_balance"));
                return bank;
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BANK + e.getMessage());
        }

        return null;
    }

    public List<String> getBankNamesByPan(String pan) {
        List<String> bankNames = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_BANK_NAMES_BY_PAN)) {
            ps.setString(1, pan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bankNames.add(rs.getString("bank_name"));
            }
        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BANKS + e.getMessage());
        }

        return bankNames;
    }

    public Bank getBankByAccountId(int accountId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_BANK_BY_ACCOUNT_ID)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bank bank = new Bank();
                bank.setBankId(rs.getInt("bank_id"));
                bank.setBankName(rs.getString("bank_name"));
                bank.setMinBalance(rs.getDouble("min_balance"));
                return bank;
            }

        } catch (SQLException e) {
            System.out.println(ERROR_BANK + e.getMessage());
        }

        return null;
    }

}
