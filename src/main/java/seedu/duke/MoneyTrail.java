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

    public void run() throws MTException {
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
            String input = in.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("list")) {
                    listSummary();
                    continue;
                }

                if (input.startsWith("find ")) {
                    findEntry(input.substring(5));
                    continue;
                }

                if (input.startsWith("delete")) {
                    deleteEntry(input);
                    continue;
                }
            } catch (MTException error) {
                logger.logWarning("Error processing command: " + error.getMessage());
                ui.printErrorMsg(error);
            }

            if (input.equalsIgnoreCase("exit")) {
                logger.logInfo("Exiting CLI program.");
                break;
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

    /**
     * Lists all stored money trail entries.
     */
    private void listSummary() throws MTException {
        if (moneyList.isEmpty()) {
            logger.logWarning("Expense list is empty.");
            throw new MTException("No entries available to display.");
        }
        else {
            ui.print("Expense list:");
            for (int i = 0; i < moneyList.size(); i++) {
                ui.print((i+ INDEX_OFFSET) + ": " + moneyList.get(i));
            }
        }
    }

    /**
     * Finds and displays entries containing a specific keyword.
     */
    private void findEntry(String input) throws MTException {
        if (input.isEmpty()) {
            logger.logWarning("Invalid entry provided.");
            throw new MTException("Please enter a keyword to search.");
        }

        ArrayList<String> results = new ArrayList<>();
        for (String entry : moneyList) {
            if (entry.toLowerCase().contains(input.toLowerCase())) {
                results.add(entry);
            }
        }

        if (results.isEmpty()) {
            logger.logWarning("No matching entries found for: " + input);
            throw new MTException("enter a valid keyword to search.");
        }
        else {
            ui.print("Found Matching entries for: " + input);
            for (int i = 0; i < results.size(); i++) {
                ui.print((i+ INDEX_OFFSET) + ": " + results.get(i));
            }
        }
    }

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) throws MTException {
        new MoneyTrail().run();
    }
}
