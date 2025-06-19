package view;

import util.DBConfig;

import java.util.Scanner;

import static constants.ViewMessages.AllViewMessages.*;
import static util.InputUtil.*;

public class MainView {

    private static final Scanner sc = new Scanner(System.in);

    public static void start() {
        DBConfig.initDatabase(); // Will look into it

        while (true) {
            print(MAIN_MENU);
            System.out.print(ENTER_OPTION);
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    AdminView.login();
                    break;
                case "2":
                    UserView.loginMenu();
                    break;
                case "3":
                    UserView.registerCustomer();
                    break;
                case "0":
                    print(THANK_YOU_END);
                    System.exit(0);
                default:
                    print(INVALID_OPTION);
            }

        }
    }
}
