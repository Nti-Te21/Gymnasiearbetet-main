package se.gymnasiearbetet.projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Accounts {
    private String username;
    private String password;
    private Boolean loggedIn;
    private String accountId;
    private List<Accounts> accountList = new ArrayList<>();

    private Accounts(String username, String password, Boolean logged_in, String account_id) {
        this.username = username;
        this.password = password;
        this.loggedIn = logged_in;
        this.accountId = account_id;
    }

    static Accounts createAccount(Scanner scanner, List<Accounts> account_list) {
        while (true) {
            System.out.println("Username:");
            var username = scanner.nextLine();
            System.out.println("Password:");
            var password = scanner.nextLine();
            boolean nameTaken = false;
            for (Accounts currentAccount : account_list) {
                if (username.equals(currentAccount.username)) {
                    System.out.println("Username is alredy taken try a diffrent one");
                    nameTaken = true;
                    break;
                }
            }
            if (!nameTaken) {
                return new Accounts(username, password, false, "1");
            }
        }
    }

    public static Accounts log_in(Scanner scanner, List<Accounts> account_list) {
        System.out.println("Username:");
        var username = scanner.nextLine();
        System.out.println("Password:");
        var password = scanner.nextLine();
        for (Accounts currentAccount : account_list) {
            if (username.equals(currentAccount.username) && password.equals(currentAccount.password)) {
                System.out.println("Logged in sucscefully!");
                return currentAccount;
            }
        }

        System.out.println("Log in failed!");
        return null;

    }

    public String toString() {
        return "Username: %s Password: %s".formatted(username, password);
    }

}
