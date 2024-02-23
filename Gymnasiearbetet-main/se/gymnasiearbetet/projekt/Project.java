package se.gymnasiearbetet.projekt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws IOException {
        List<Accounts> account_list = new ArrayList<>();
        var addAccounts = Accounts.importAccountsFromFile();
        account_list.addAll((List<Accounts>) addAccounts);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to my JAva password database program");
            boolean done = false;
            while (!done) {
                System.out.println("1. Create account \n2. Log in \n3. Exit program");
                var choice = scanner.nextLine();
                if (choice.equals("1")) {
                    Accounts account = Accounts.createAccount(scanner, account_list);
                    account_list.add(account);
                } else if (choice.equals("2")) {
                    var loggedInAccount = Accounts.log_in(scanner, account_list);
                    if (loggedInAccount != null) {
                        Accounts.passwordManagment(scanner, loggedInAccount);
                    }
                } else if (choice.equals("3")) {
                    done = true;
                }
            }
        }
    }
}