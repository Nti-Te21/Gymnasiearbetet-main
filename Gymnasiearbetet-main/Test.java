
import se.andin.test.Person;

public class Test {
    public static void main(String args[]) {

        var person2 = Person.loadAccountsFromFile();
        System.out.println(person2);
    }
}