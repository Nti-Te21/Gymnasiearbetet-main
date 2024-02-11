package se.gymnasiearbetet.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Base64;
import java.util.HashMap;

public class Accounts {
    private String username;
    private String password;
    private String accountId;
    public static List<String> currentAccountRecords = new ArrayList<>();
    public static Map<String, List<String>> localRecordsData = new HashMap<>();

    private Accounts(String username, String password, String accountId) {
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
            var accountIdStr = String.valueOf(accountId);
            boolean nameTaken = false;
            for (Accounts currentAccount : account_list) {
                if (username.equals(currentAccount.username)) {
                    System.out.println("Username is alredy taken try a diffrent one");
                    nameTaken = true;
                    break;
                }
            }
            if (!nameTaken) {
                addAccountToFile(new Accounts(username, password, accountIdStr));
                return new Accounts(username, password, accountIdStr);
            }
        }
    }

    public static void importRecordsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("records.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String accountId = parts[0];
                    List<String> records = new ArrayList<>(Arrays.asList(parts));
                    localRecordsData.put(accountId, records);
                }
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

    public static List<Accounts> importAccountsFromFile() throws IOException {
        List<Accounts> account_list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] old_account = line.split(",");
                account_list.add(new Accounts(old_account[0], old_account[1], (old_account[2])));

            }
        }
        return account_list;
    }

    public static void addAccountToFile(Accounts account) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv", true))) {
            writer.write(account.username + "," + account.password + "," + account.accountId + "\n");
        }
    }

    public static void passwordManagment(Scanner scanner, Accounts loggedInAccount) throws IOException {
        boolean done = false;
        localRecordsData.clear();
        currentAccountRecords.clear();
        importRecordsFromFile();
        currentAccountRecords = localRecordsData.get(loggedInAccount.accountId);
        if (currentAccountRecords == null) {
            currentAccountRecords = new ArrayList<>();
        }
        while (!done) {
            System.out.println(
                    "\n1. Create stored password \n2. View passwords\n3. Log out \n4. Exit program and log out\n");
            var choice = scanner.nextLine();
            if (choice.equals("1")) {
                createRecord(scanner, loggedInAccount);
            } else if (choice.equals("2")) {
                System.out.println("\nViewing passwords coming soon");
            } else if (choice.equals("3")) {
                System.out.println("\nLogged out!");
                done = true;
                saveRecordsToFile();
            } else if (choice.equals("4")) {
                System.out.println("\nLogged out! See you next time!\n");
                done = true;
                saveRecordsToFile();
                System.exit(0);
            }

        }
    }

    public static void createRecord(Scanner scanner, Accounts loggedInAccount) throws IOException {
        System.out.println("Record name:");
        var recordName = scanner.nextLine();
        System.out.println("Username:");
        var username = scanner.nextLine();
        System.out.println("Password:");
        var password = scanner.nextLine();
        var record = new StringBuffer();
        record.append(recordName).append("\n");
        record.append(username).append("\n");
        record.append(password);
        String stringRecord = record.toString();
        byte[] byteRecord = stringRecord.getBytes();
        var base64Record = Base64.getEncoder().encodeToString(byteRecord);
        String fullRecord = new String("," + base64Record);
        if (currentAccountRecords.contains(loggedInAccount.accountId)) {
            currentAccountRecords.add(fullRecord);
        } else {
            currentAccountRecords.add(loggedInAccount.accountId);
            currentAccountRecords.add(fullRecord);
        }
    }

    public static void saveRecordsToFile() throws IOException {
        try {
            if (!localRecordsData.containsKey(currentAccountRecords.get(0))) {
                localRecordsData.put(currentAccountRecords.get(0), currentAccountRecords);
            }
        } catch (Exception e) {
            System.err.println("error index not there");
        }
        if (currentAccountRecords.isEmpty() != true) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("records.csv"))) {
                if (localRecordsData != null) {
                    boolean firstAccount = true;
                    for (String accountId : localRecordsData.keySet()) {
                        if (!firstAccount) {
                            writer.write("\n");
                        } else {
                            firstAccount = false;
                        }
                        List<String> records = localRecordsData.get(accountId);
                        if (records != null) {
                            for (String record : records) {
                                writer.write(record + ",");
                            }
                        }
                    }
                }
            }

        }
    }
}