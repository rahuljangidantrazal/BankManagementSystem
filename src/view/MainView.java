package view;

import util.DBConfig;

import java.util.Scanner;

import static constants.ViewMessages.AllViewMessages.*;
import static util.InputUtil.*;

// *********************************************************************************************************
//  *  JAVA Class Name :   MainView.java
//  *  Author          :   <Rahul Jangid>(rahul.jangid@antrazal.com)
//  *  Company         :   Antrazal
//  *  Date            :   20-06-2025
//  *  Description     :   This class provides the main entry point for the console-based Bank Management System.
//  *                      It initializes the database configuration and presents the main menu options to users.
//  *                      Users can choose to login as Admin, login as User (Customer/Cashier/Manager),
//  *                      register a new Customer, or exit the application.
//  *
//  *                      Provided Methods:
//  *                      - start()
//  *
//  *******************************************************************************************************
//  *  JIRA ID     Developer
//  *  AWC      <Rahul Jangid>(rahul.jangid@antrazal.com)
//  ********************************************************************************************************

public class MainView {

    private static final Scanner sc = new Scanner(System.in);

    public static void start() {
        DBConfig.initDatabase();

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
