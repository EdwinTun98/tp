package seedu.duke;

import java.util.ArrayList;
import java.util.List;


public class TextUI {
    private static final String LINE_DIVIDER = "----------------------" +
            "---------------------------------------------------------";

    // List to track printed messages for testing
    private final List<String> printedMessages = new ArrayList<>();

    public void print(String str) {
        System.out.println(str); // Print to console
        printedMessages.add(str); // Store the message in the list
    }

    public void addLineDivider() {
        print(LINE_DIVIDER);
    }

    public void printAppLogo() {
        String logo = " __  __ _______ \n"
                + "|  \\/  |__  __|\n"
                + "| \\  / |  | |  \n"
                + "| |\\/| |  | |  \n"
                + "| |  | |  | |  \n"
                + "|_|  |_|  |_|  \n";
        print("Welcome user to\n" + logo);
    }

    public void printWelcomeMsg() {
        printAppLogo();
        print("What do you want to do today?");
    }

    public void printPromptMsg() {
        print("What do you want to do next?");
    }

    public void printExitMsg() {
        addLineDivider();
        print("Exiting program... Thank you for using MoneyTrail! :)");
    }

    public void printErrorMsg(MTException error) {
        print(error.getMessage());
    }

    public void printNumItems(int numItems) {
        print("You now have " + numItems + " entries.");
    }

    public void showAllAvailableCommands() {
        print("List of available commands:");
        print("1. help: Displays this list of available commands.");
        print("2. list: Lists out all entries.");
        print("3. addExpense <description> $/<value> c/<category> <: Adds a new expense entry. Category is optional.");
        print("4. totalExpense: Displays the total expense accumulated from all entries.");
        print("5. setTotalBudget <budget>: Sets a total spending budget to adhere to.");
        print("6. delete <entry_number>: Deletes an entry.");
        print("7. find <keyword>: Finds an entry based on the given keyword.");
        print("8. listCats: Lists out all entry categories in order of appearance.");
        print("9. exit: Exits the program.");
    }

    // Method to retrieve printed messages (for testing purposes)
    public List<String> getPrintedMessages() {
        return new ArrayList<>(printedMessages);
    }

    // Method to clear printed messages (useful for resetting state between tests)
    public void clearPrintedMessages() {
        printedMessages.clear();
    }
}
