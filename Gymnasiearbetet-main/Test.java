
import java.io.IOException;

import se.andin.test.Person;

public class Test {
    public static void main(String args[]) throws IOException {
        var person = Person.createPerson();
        System.out.println(person);
        var person2 = Person.loadAccountsFromFile();
        System.out.println(person2);
    }
}