package seedu.duke;

public class TextUI {
    private static final String LINE_DIVIDER = "----------------------" +
            "---------------------------------------------------------";

    public void print(String str) {
        System.out.println(str);
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
        print("3. addExpense <DESCRIPTION> $/<value>: Adds a new expense entry.");
        print("4. totalExpense: Displays the total expense accumulated from all entries.");
        print("5. setTotalBudget <BUDGET>: Sets a total spending budget to adhere to.");
        print("6. delete <ENTRY_NUMBER>: Deletes an entry.");
        print("7. find <KEYWORD>: Finds an entry based on the given keyword.");
        print("8. exit: Exits the program.");
    }
}
