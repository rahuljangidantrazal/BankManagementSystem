package controllers;

import model.Bank;
import repo.BankRepo;

import java.util.List;

// *********************************************************************************************************
//  *  JAVA Class Name :   BankController.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com) 
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This controller class provides a centralized interface for managing bank-related 
//  *                      operations. It delegates data handling to the BankRepo and offers methods to insert 
//  *                      banks, check for existing banks by name, retrieve bank details by ID or name, and 
//  *                      fetch all banks or banks associated with a specific PAN. 
//  *                      It follows the Singleton pattern for consistent access throughout the application.
//  * 
//  *******************************************************************************************************
//  *  JIRA ID     Developer                                               
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)       
// ********************************************************************************************************

public class BankController {

    private static final BankRepo bankRepo = BankRepo.getInstance();
    private static final BankController instance = new BankController();

    private BankController() {
    }

    public static BankController getInstance() {
        return instance;
    }

    public boolean insertBank(Bank bank) {
        return bankRepo.insertBank(bank);
    }

    public boolean bankExistsByName(String bankName) {
        return bankRepo.bankExistsByName(bankName);
    }

    public List<Bank> getAllBanks() {
        return bankRepo.getAllBanks();
    }

    public Integer getBankIdByName(String bankName) {
        return bankRepo.getBankIdByName(bankName);
    }

    public Bank getBankById(int bankId) {
        return bankRepo.getBankById(bankId);
    }

    public List<String> getBankNamesByPan(String pan) {
        return bankRepo.getBankNamesByPan(pan);
    }
}
