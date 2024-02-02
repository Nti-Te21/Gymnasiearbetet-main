import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Base64Example {

    public static String listToBase64(List<Object> inputList) {
        // Convert the list to a string
        String stringData = inputList.toString();

        // Convert the string to bytes
        byte[] byteData = stringData.getBytes();

        // Encode the bytes to base64
        byte[] base64Data = Base64.getEncoder().encode(byteData);

        return new String(base64Data);
    }

    public static List<Object> base64ToList(String base64String) {
        // Decode the base64 string to bytes
        byte[] base64Data = Base64.getDecoder().decode(base64String);

        // Convert the bytes to a string and remove square brackets
        String stringData = new String(base64Data).replaceAll("[\\[\\]]", "");

        // Split the string into an array
        String[] stringArray = stringData.split(", ");

        // Create a new list and add elements from the array
        List<Object> decodedList = new ArrayList<>();
        for (String element : stringArray) {
            decodedList.add(element);
        }

        return decodedList;
    }

    public static void main(String[] args) {
        // Example list
        List<Object> myList = new ArrayList<>();
        myList.add("a");
        myList.add("a");
        myList.add("a");

        // Convert the list to base64
        String encodedResult = listToBase64(myList);

        // Decode the base64 back to the list
        List<Object> decodedResult = base64ToList(encodedResult);

        // Print the results
        System.out.println("Original List: " + myList);
        System.out.println("Base64 Encoding: " + encodedResult);
        System.out.println("Decoded List: " + decodedResult);
    }
}
