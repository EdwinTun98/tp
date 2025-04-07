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
    private static final String FILE_PATH = "mt.txt";
    private static final String BUDGET_FILE_PATH = "budgets.txt";
    private static final String BUDGET_DELIMITER = "\\s+";
    private final MTLogger logger;

    private List<String> lastKnownState = new ArrayList<>();

    public Storage() {
        this.logger = new MTLogger(Storage.class.getName());
        try {
            this.lastKnownState = loadFileState(FILE_PATH);
        } catch (MTException error) {
            logger.logWarning("Could not initialize last known state: "
                    + error.getMessage());
        }
    }

    /**
     * Saves all entries to the storage file.
     */
    public void saveExpenses(ArrayList<String> moneyList) throws MTException {
        logger.logInfo("Saving entries into " + FILE_PATH);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (String entry : moneyList) {
                if (isValidEntry(entry)) {
                    writer.write(entry + "\n");
                } else {
                    logger.logWarning("Attempted to save invalid entry: "
                            + entry);
                }
            }
            lastKnownState = new ArrayList<>(moneyList);
        } catch (IOException error) {
            logger.logSevere("Error saving entries", error);
            throw new MTException("Error saving entries: " +
                    error.getMessage());
        }
    }

    /**
     * Loads all entries from the storage file, skipping any corrupted lines.
     */
    public ArrayList<String> loadEntries() throws MTException {
        logger.logInfo("Loading previous entries from " + FILE_PATH);
        ArrayList<String> validEntries = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return validEntries;
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                if (!line.isEmpty()) {
                    if (isValidEntry(line)) {
                        validEntries.add(line);
                    } else {
                        logger.logWarning("Skipping corrupted entry at " +
                                "line " + lineNumber + ": " + line);
                    }
                }
            }
            lastKnownState = new ArrayList<>(validEntries);
        } catch (FileNotFoundException error) {
            logger.logSevere("Failed to find file", error);
            throw new MTException("File not found. Starting with empty " +
                    "list.");
        }

        return validEntries;
    }

    /**
     * Validates if an entry string matches the expected format.
     */
    private boolean isValidEntry(String entry) {
        if (entry == null || entry.trim().isEmpty()) {
            return false;
        }

        // Basic format check
        if (!entry.contains(":") || !entry.contains("Expense")) {
            return false;
        }

        return true;
    }

    /**
     * Saves budgets to file, validating each entry first.
     */
    public void saveBudgets(HashMap<String,
            Budget> budgetList) throws MTException {
        if (budgetList == null) {
            logger.logWarning("Null budget list provided");
            return;
        }

        try (FileWriter writer = new FileWriter(BUDGET_FILE_PATH)) {
            for (Budget budget : budgetList.values()) {
                String entry = budget.getCategory() + " " +
                        budget.getAmount();

                if (isValidBudgetEntry(entry)) {
                    writer.write(entry + "\n");
                } else {
                    logger.logWarning("Attempted to save invalid budget: " + entry);
                }
            }
            logger.logInfo("Budgets successfully saved");
        } catch (IOException error) {
            logger.logSevere("Failed to save budgets", error);
            throw new MTException("Error saving budgets: " + error.getMessage());
        }
    }

    /**
     * Loads budgets from file, skipping any corrupted lines.
     */
    public HashMap<String, Budget> loadBudgets() throws MTException {
        HashMap<String, Budget> budgets = new HashMap<>();
        File file = new File(BUDGET_FILE_PATH);

        if (!file.exists()) {
            return budgets;
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;

            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                if (!line.isEmpty()) {
                    try {
                        if (isValidBudgetEntry(line)) {
                            String[] parts = line.split(BUDGET_DELIMITER, 2);
                            String category = parts[0];
                            double amount = Double.parseDouble(parts[1]);
                            budgets.put(category, new Budget(category, amount));
                        } else {
                            logger.logWarning("Skipping corrupted budget at line "
                                    + lineNumber + ": " + line);
                        }
                    } catch (Exception error) {
                        logger.logWarning("Skipping corrupted budget at line "
                                + lineNumber + ": " + line);
                    }
                }
            }
        } catch (FileNotFoundException error) {
            logger.logSevere("Budget file not found", error);
            throw new MTException("Budgets file not found.");
        }

        return budgets;
    }

    /**
     * Validates if a budget entry string matches the expected format.
     */
    private boolean isValidBudgetEntry(String entry) {
        if (entry == null || entry.trim().isEmpty()) {
            return false;
        }

        // Should contain exactly one space separating category and amount
        String[] parts = entry.split(BUDGET_DELIMITER);
        if (parts.length != 2) {
            return false;
        }

        // Second part should be a valid number
        try {
            Double.parseDouble(parts[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Loads the current state of a file for internal use.
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
            logger.logSevere("Failed to find file", error);
            throw new MTException("File not found while loading state.");
        }
        
        return currentState;
    }
}
