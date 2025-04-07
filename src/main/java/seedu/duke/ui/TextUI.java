package seedu.duke.ui;

import java.util.List;
import java.util.ArrayList;

import seedu.duke.exception.MTException;

public class TextUI {
    private static final String LINE_DIVIDER = "----------------------" +
            "---------------------------------------------------------";

    private final List<String> printedMessages = new ArrayList<>(); // Store printed messages

    /**
     * Prints a string input.
     *
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
     *
     * @param error From MTException
     */
    public void printErrorMsg(MTException error) {
        print(error.getMessage());
    }

    /**
     * Prints the number of items with correct singular/plural form.
     *
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
        print("Parameters in brackets (i.e., []) are optional.");
        print("List of available commands:");
        print("1. help: Displays this list of available commands.");
        print("2. list: Lists out all entries.");
        print("3. addExp <description> $/<value> [c/<category>] [d/<date>]: Adds a new expense entry.");
        print("4. addIncome <description> $/<value> [d/<date>]: Adds a new income entry.");
        print("5. totalExp: Displays the total expense accumulated.");
        print("6. setTotBgt <value>: Sets a total spending budget.");
        print("7. setCatBgt c/<category> <value>: Sets a budget for a specific category.");
        print("8. listBgt: Lists out all category budgets.");
        print("9. listCat: Lists out all entry categories in order of appearance.");
        print("10. del <index>: Deletes an entry.");
        print("11. find <keyword>: Finds an entry based on the keyword.");
        print("12. edit <index> <description> [$/<amount>] [c/<category>] [d/<date>]: " +
                "Modify the full entry or just selected details.");
        print("13. check Overall / check Category: Shows overall expense or total expense for searched category\n" +
                "    and show set budget and total expenditure of respective category.");
        print("14. clear: Clears all entries.");
        print("15. exit: Exits the program.");
    }
}
