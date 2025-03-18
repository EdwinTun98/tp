package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    private static final int INDEX_OFFSET = 1;

    public ArrayList<String> moneyList;
    // logger instance for MoneyTrail class
    private final MTLogger logger;
    private final Scanner in;
    private final Storage storage;
    private final TextUI ui;

    public MoneyTrail() {
        this.moneyList = new ArrayList<>();
        this.in = new Scanner(System.in);
        this.storage = new Storage();
        this.ui = new TextUI();

        this.logger = new MTLogger(MoneyTrail.class.getName());
    }

    public void run() {
        logger.logInfo("Starting CLI program.");

        try {
            loadEntriesFromFile();
        } catch (MTException error) {
            logger.logSevere("Error loading entries from file: "
                    + error.getMessage(), error);
            ui.printErrorMsg(error);
        }

        ui.printWelcomeMsg();

        while (true) {
            String input = in.nextLine();
            if (input.startsWith("delete")) {
                try {
                    deleteEntry(input);
                } catch (MTException error) {
                    logger.logWarning("Error deleting entry: "
                            + error.getMessage());
                    ui.printErrorMsg(error);
                } finally {
                    ui.addLineDivider();
                }
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                logger.logInfo("Exiting CLI program.");
                break;
            }
            if (input.startsWith("addExpense")) {
                try {
                    addExpense(input);
                } catch (MTException error) {
                    logger.logWarning("Error adding expense: " + error.getMessage());
                    ui.printErrorMsg(error);
                } finally {
                    ui.addLineDivider();
                }
                continue;
            }
            if (input.equalsIgnoreCase("help")) {
                // Display all available commands and their descriptions
                ui.print("List of available commands:");
                ui.print("1. addExpense <description> $/ <value> - Adds a new expense.");
                ui.print("2. delete <ENTRY_NUMBER> - Deletes the specified expense entry.");
                ui.print("3. help - Displays this list of commands.");
                ui.print("4. exit - Exits the program.");
                ui.addLineDivider();
                continue;
            }

        }

        ui.printExitMsg();
    }

    private int extractIndex(String input) {
        return Integer.parseInt(input.replaceAll(
                "[^0-9]", "")) - INDEX_OFFSET;
    }

    private void validateIndex(int index) throws MTException {
        if (index < 0 || index >= moneyList.size()) {
            logger.logWarning("Invalid index provided: " + index);
            throw new MTException("Invalid or unavailable entry number.");
        }
    }

    public void deleteEntry(String input) throws MTException {
        try {
            // Assert that the input is not null and starts with "delete"
            assert input != null : "Input should not be null";
            assert input.startsWith("delete") : "Input should start with 'delete'";

            // obtain the index to search for entry to be deleted
            int deleteIndex = extractIndex(input);
            validateIndex(deleteIndex);

            // display entry before deletion
            ui.print("This entry will be permanently deleted:");
            ui.print(moneyList.get(deleteIndex));

            // remove entry from moneyList
            moneyList.remove(deleteIndex);
            logger.logInfo("Deleted entry at index: " + deleteIndex);

            // save updated list
            storage.saveEntries(moneyList);
            // print out number of items left in moneyList
            ui.printNumItems(moneyList.size());
        } catch (NumberFormatException error) {
            logger.logSevere("Invalid delete command format: " + input, error);
            throw new MTException("Use: delete <ENTRY_NUMBER>");
        }
    }

    private void loadEntriesFromFile() throws MTException {
        ArrayList<String> loadedEntries = storage.loadEntries();
        if (loadedEntries != null) {
            moneyList.addAll(loadedEntries);
        }

        // Assert that moneyList is not null
        assert moneyList != null : "moneyList should not be null";

        logger.logInfo("Loaded " + moneyList.size() + " entries from file.");
        ui.print("Loaded " + moneyList.size() + " entries from file.");
    }

    public void addExpense(String input ) throws MTException {
        try {

            // Assert that the input is not null and starts with "addExpense"
            assert input != null : "Input should not be null";
            assert input.startsWith("addExpense") : "Input should start with 'addExpense'";

            //remove all trailing , leading and spaces between input;
            input = input.replaceAll("\\s","");
            String[] parts = input.substring(10).split("\\$/", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new MTException("Your addExpense needs a description and a value.\nFormat: addExpense <description> $/ <value>");
            } else{
                String description = parts[0].trim();
                Double amount = Double.parseDouble(parts[1].trim());
                Expense newExpense = new Expense(description, amount);
                // Add expense to moneyList (as a String representation)
                moneyList.add(newExpense.toString());

                // Log that the expense was added
                logger.logInfo("Added expense: " + newExpense);

                // Inform the user via the UI
                ui.print("Expense added: " + newExpense);

                // Save the updated list of entries
                storage.saveEntries(moneyList);

            }
        } catch (Exception error) {
            logger.logSevere("Error adding expense: " + error.getMessage(), error);
            throw new MTException("Failed to add expense: " + error.getMessage());
        }
    }


    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
