package seedu.duke;

import java.util.List;
import java.util.ArrayList;

public class TextUI {
    private static final String LINE_DIVIDER = "----------------------" +
            "---------------------------------------------------------";

    private final List<String> printedMessages = new ArrayList<>(); // Store printed messages

    /**
     * Prints a string input.
     * @param str The input to be printed out
     */
    public void print(String str) {
        System.out.println(str);
        printedMessages.add(str); // Add the message to the list
    }

    /**
     * Returns the list of all printed messages for testing.
     *
     * @return A list of printed messages
     */
    public List<String> getPrintedMessages() {
        return printedMessages;
    }

    /**
     * Inserts a line divider to avoid text clutter.
     */
    public void addLineDivider() {
        print(LINE_DIVIDER);
    }

    /**
     * Prints the app logo and the welcome prompt.
     */
    public void printAppLogo() {
        String logo = " __  __ _______ \n"
                + "|  \\/  |__  __|\n"
                + "| \\  / |  | |  \n"
                + "| |\\/| |  | |  \n"
                + "| |  | |  | |  \n"
                + "|_|  |_|  |_|  \n";
        print("Welcome user to\n" + logo);
    }

    /**
     * Prints out the app logo before displaying the welcome message
     * when users enter the program.
     */
    public void printWelcomeMsg() {
        printAppLogo();
        print("What do you want to do today?");
    }

    /**
     * Prints the prompt message asking users what actions to take next.
     */
    public void printPromptMsg() {
        print("What do you want to do next?");
    }

    /**
     * Adds a line divider before printing out the prompt message
     * when users exit the program.
     */
    public void printExitMsg() {
        addLineDivider();
        print("Exiting program... Thank you for using MoneyTrail! :)");
    }

    /**
     * Prints out the error message from MTException.
     * @param error From MTException
     */
    public void printErrorMsg(MTException error) {
        print(error.getMessage());
    }

    /**
     * Prints the number of items with correct singular/plural form.
     * @param numItems The number of items/entries
     */
    public void printNumItems(int numItems) {
        String entryText = (numItems == 1) ? "entry" : "entries";
        print("You now have " + numItems + " " + entryText + ".");
    }

    /**
     * Prints all available commands required by the MT program in a list.
     */
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
}
