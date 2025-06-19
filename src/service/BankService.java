package service;

import model.Bank;
import repo.BankRepo;
import constants.Messages;
import static util.InputUtil.*;

import java.util.List;
import java.util.Scanner;

public class BankService {

    private static BankService instance;
    private final BankRepo bankrepo;

    private BankService() {
        this.bankrepo = BankRepo.getInstance();
    }

    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }

    public void createBank(Scanner scanner) {
        System.out.print(Messages.BankMessages.ENTER_BANK_NAME);
        String name = scanner.nextLine();

        System.out.print(Messages.BankMessages.ENTER_MIN_BALANCE);
        double minBalance = Double.parseDouble(scanner.nextLine());

        Bank bank = new Bank(name, minBalance);

        boolean success = bankrepo.insertBank(bank);
        if (success) {
            print(Messages.BankMessages.BANK_CREATED_SUCCESS);
        } else {
            print(Messages.BankMessages.BANK_CREATION_FAILED);
        }
    }

    public void listAllBanks() {
        List<Bank> banks = bankrepo.getAllBanks();
        if (banks.isEmpty()) {
            print(Messages.BankMessages.NO_BANKS_FOUND);
            return;
        }
        print(Messages.BankMessages.AVAILABLE_BANKS);
        for (Bank bank : banks) {
            System.out.printf("%-25s | Min Balance: Rs.%.2f%n", bank.getBankName(), bank.getMinBalance());
        }
    }
}
