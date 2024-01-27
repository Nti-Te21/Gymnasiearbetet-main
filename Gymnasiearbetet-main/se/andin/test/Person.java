package se.andin.test;

import java.io.*;
import java.util.Scanner;

/**
 * test
 */
public class Person {
    private String name;
    private String age;

    private Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static Person createPerson() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Name:");
            var name = scanner.nextLine();
            System.out.println("Age:");
            var age = scanner.nextLine();
            scanner.nextLine();
            return new Person(name, age);
        }
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
}
