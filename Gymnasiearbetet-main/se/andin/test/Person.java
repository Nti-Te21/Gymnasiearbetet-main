package se.andin.test;

import java.io.*;
import java.util.Scanner;

import se.gymnasiearbetet.projekt.Accounts;

/**
 * test
 */
public class Person {
    private static String name;
    private static String age;

    private Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static Person createPerson() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Name:");
            name = scanner.nextLine();
            System.out.println("Age:");
            age = scanner.nextLine();

        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv", true))) {
            writer.write(name + "," + age + "\n");

        }
        return new Person(name, age);

    }

    public static Person loadAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            System.out.println("Reading from file");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println(parts[0] + " " + parts[1]);
                return new Person(parts[0], parts[1]);
            }
        } catch (IOException e) {
            // Handle exceptions if necessary
            System.out.println("exeption");
        }
        return null;
    }

    public String toString() {
        return "Person name: %s age: %s".formatted(name, age);
    }
