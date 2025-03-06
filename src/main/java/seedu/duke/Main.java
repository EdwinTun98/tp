import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String LINE_SPACING = "____________________________________________________________";

    private static ArrayList<Entry> entries = new ArrayList<>();
    private static Scanner inputObj = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(LINE_SPACING);
        System.out.println("Hi I'm *Insert Name Here*!"); // Insert name in string
        System.out.println("Type 'help' to see the full list of commands!");
        System.out.println(LINE_SPACING);

        while (true) {
            String userInput = inputObj.nextLine().trim();

            if (userInput.equalsIgnoreCase("exit")) {
                exit();
                break; //terminate program
            }
            try {
                handleUserInput(userInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void handleUserInput(String userInput) {
        try {
            if (userInput.equals("exit")) {
                exit();
            } else if (userInput.equals("help")) {
                help();
            } else if (userInput.startsWith("addEntry")) {
                // addEntry (income/expense) (description) $/(value)
                addEntry(userInput);
            } else if (userInput.startsWith("addCat")) {
                // addCat (category)
                addCat(userInput);
            } else if (userInput.startsWith("setCatBudget")) {
                // setCatBudget (category) $/(value)
                setCatBudget(userInput);
            } else if (userInput.startsWith("checkCatBudget")) {
                // checkCatBudget (category)
                checkCatBudget(userInput);
            } else {
                System.out.println("Command not recognised");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void exit() {
        System.out.println(LINE_SPACING);
        System.out.println("Exiting application... Goodbye!");
        System.out.println(LINE_SPACING);
    }

    private static void help() {
        System.out.println(LINE_SPACING);
        System.out.println("Here is the list of commands:"); // *add descriptions for commands
        System.out.println("addEntry (income/expense) (description) $/(value)");
        System.out.println("addCat (category)");
        System.out.println("setCatBudget (category) $/(value)");
        System.out.println("checkCatBudget (category)");
        System.out.println("exit - to exit the application");
        System.out.println(LINE_SPACING);
    }

    private static void addEntry(String userInput) {
        // placeholder, add method
    }

    private static void addCat(String userInput) {
        // placeholder, add method
    }

    private static void setCatBudget(String userInput) {
        // placeholder, add method
    }

    private static void checkCatBudget(String userInput) {
        // placeholder, add method
    }
}