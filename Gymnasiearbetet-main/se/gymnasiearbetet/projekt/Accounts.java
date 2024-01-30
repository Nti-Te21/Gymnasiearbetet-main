package se.gymnasiearbetet.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Accounts {
    private String username;
    private String password;
    private Integer accountId;

    private Accounts(String username, String password, Integer accountId) {
        this.username = username;
        this.password = password;
        this.accountId = accountId;
    }

    static Accounts createAccount(Scanner scanner, List<Accounts> account_list) throws IOException {
        while (true) {
            System.out.println("Username:");
            var username = scanner.nextLine();
            System.out.println("Password:");
            var password = scanner.nextLine();
            var accountId = account_list.size() + 1;
            boolean nameTaken = false;
            for (Accounts currentAccount : account_list) {
                if (username.equals(currentAccount.username)) {
                    System.out.println("Username is alredy taken try a diffrent one");
                    nameTaken = true;
                    break;
                }
            }
            if (!nameTaken) {
                System.out.println(accountId);
                addAccountToFile(new Accounts(username, password, accountId));
                return new Accounts(username, password, accountId);
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

    public static List<Accounts> importAccountsFromFile() throws IOException {
        List<Accounts> account_list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] old_account = line.split(",");
                account_list.add(new Accounts(old_account[0], old_account[1], Integer.parseInt(old_account[2])));

            }
        }
        return account_list;
    }

    public static void addAccountToFile(Accounts account) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv", true))) {
            writer.write(account.username + "," + account.password + "," + account.accountId + "\n");
        }
    }
}
