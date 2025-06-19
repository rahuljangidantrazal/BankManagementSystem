package controllers;

import model.Bank;
import repo.BankRepo;

import java.util.List;

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
