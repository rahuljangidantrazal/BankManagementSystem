package view;

import model.User;
import model.UserTypeEnum;
import controllers.UserController;

import static constants.ViewMessages.AllViewMessages.*;
import static util.InputUtil.*;

import java.util.Scanner;

public class UserView {
    private static final Scanner sc = new Scanner(System.in);

    public static void registerCustomer() {
        UserController.getInstance().registerUser();
    }

    public static void loginMenu() {
        print(USER_LOGIN_MENU_BLOCK);
        System.out.print(ENTER_OPTION);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                handleUserLogin(UserTypeEnum.CUSTOMER);
                break;
            case "2":
                handleUserLogin(UserTypeEnum.CASHIER);
                break;
            case "3":
                handleUserLogin(UserTypeEnum.MANAGER);
                break;
            case "0":
                return;
            default:
                print(INVALID_OPTION);
        }
    }

    private static void handleUserLogin(UserTypeEnum type) {
        while (true) {
            print(ENTER_USERNAME_GOBACK);
            String username = sc.nextLine().trim();
            if (username.equals("0")) {
                return;
            }

            print(ENTER_PASSWORD);
            String password = sc.nextLine().trim();

            User user = UserController.getInstance().validateLogin(username, password, type);
            if (user != null && user.getUserType() == type) {
                switch (type) {
                    case CUSTOMER:
                        new CustomerView().start(user);
                        break;
                    case CASHIER:
                        new CashierView().showDashboard(user);
                        break;
                    case MANAGER:
                        new ManagerView().showDashboard(user);
                        break;
                    default: {
                        return;
                    }
                }
                return;
            }

            print(INVALID_CREDENTIALS);
        }
    }

}
