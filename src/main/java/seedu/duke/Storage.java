package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading and saving of entries to a text file.
 */
public class Storage {
    // relative path of 'F:\repos\tp\mt.txt' directory
    private static final String FILE_PATH = "mt.txt";
    private static final String BUDGET_FILE_PATH = "budgets.txt";
    private final MTLogger logger;

    private List<String> lastKnownState = new ArrayList<>();

    //@@author rchlai
    /**
     * Initializes a new Storage instance with a logger.
     */
    public Storage() {
        this.logger = new MTLogger(Storage.class.getName());
        // Initialize last known state
        try {
            this.lastKnownState = loadFileState(FILE_PATH);
        } catch (MTException error) {
            logger.logWarning("Could not initialize last known " +
                    "state: " + error.getMessage());
        }
    }
    //@@author

    /**
     * Loads the current state of a file.
     *
     * @param filePath Path to the file to load
     * @return List of lines in the file
     * @throws MTException If there's an error reading the file
     */
    private List<String> loadFileState(String filePath) throws MTException {
        List<String> currentState = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return currentState;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                currentState.add(scanner.nextLine());
            }
        } catch (FileNotFoundException error) {
            logger.logSevere("Failed to find file at " + filePath,
                    error);
            throw new MTException("File not found while loading " +
                    "state.");
        }

        return currentState;
    }

    //@@author rchlai
    /**
     * Saves all entries to the storage file.
     *
     * @param moneyList List of entries to save
     * @throws MTException If there's an error writing to file
     */
    public void saveExpenses(ArrayList<String> moneyList) throws MTException {
        logger.logInfo("Saving entries into " + FILE_PATH);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (String entry : moneyList) {
                writer.write(entry + "\n");
            }
            // Update last known state after saving
            lastKnownState = new ArrayList<>(moneyList);
        } catch (IOException error) {
            logger.logSevere("Error saving entries into " + FILE_PATH,
                    error);
            throw new MTException("Error saving entries: " +
                    error.getMessage());
        }
    }
    //@@author

    //@@author EdwinTun98
    /**
     * Saves the given budget list to a file.
     *
     * @param budgetList A HashMap containing budget categories and their corresponding Budget objects.
     * @throws MTException If an I/O error occurs while saving the budgets.
     */
    public void saveBudgets(HashMap<String, Budget> budgetList) throws MTException {
        if (budgetList == null || budgetList.isEmpty()) {
            logger.logWarning("No budgets to save.");
            return;
        }

        try (FileWriter writer = new FileWriter(BUDGET_FILE_PATH)) {
            for (Budget budget : budgetList.values()) {
                writer.write(budget.getCategory() + " " + budget.getAmount() + "\n");
            }
            logger.logInfo("Budgets successfully saved to " + BUDGET_FILE_PATH);
        } catch (IOException e) {
            logger.logSevere("Failed to save budgets", e);
            throw new MTException("Error saving budgets: " + e.getMessage());
        }
    }
    //@@author

    //@@author rchlai
    /**
     * Loads all entries from the storage file.
     *
     * @return List of loaded entries
     * @throws MTException If the file exists but cannot be read
     */
    public ArrayList<String> loadEntries() throws MTException {
        logger.logInfo("Loading previous entries from " + FILE_PATH);

        ArrayList<String> entries = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return entries;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                entries.add(scanner.nextLine());
            }
            // Update last known state after loading
            lastKnownState = new ArrayList<>(entries);
        } catch (FileNotFoundException error) {
            logger.logSevere("Failed to find file at " + FILE_PATH,
                    error);
            throw new MTException("File not found. Starting with an empty list.");
        }
        return entries;
    }

    //@@author EdwinTun98
    /**
     * Loads budgets from a file and returns them as a HashMap.
     *
     * @return A HashMap where keys are budget categories and values are Budget objects.
     * @throws MTException If the budget file is not found or contains corrupted entries.
     */
    public HashMap<String, Budget> loadBudgets() throws MTException {
        HashMap<String, Budget> budgets = new HashMap<>();
        File file = new File(BUDGET_FILE_PATH);

        if (!file.exists()) {
            return budgets;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+", 2);
                if (parts.length == 2) {
                    String category = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    budgets.put(category, new Budget(category, amount));
                }
            }
        } catch (FileNotFoundException error) {
            logger.logSevere("Budget file not found", error);
            throw new MTException("Budgets file not found.");
        } catch (NumberFormatException error) {
            throw new MTException("Corrupted budget entry: " + error.getMessage());
        }

        return budgets;
    }
    //@@author
}
