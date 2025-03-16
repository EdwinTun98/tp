package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    // relative path of 'F:\repos\tp\mt.txt' directory
    private static final String FILE_PATH = "mt.txt";
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
        this.storage = new Storage(FILE_PATH);
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

        logger.logInfo("Loaded " + moneyList.size() + " entries from file.");

        ui.print("Loaded " + moneyList.size() + " entries from file.");
    }

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
