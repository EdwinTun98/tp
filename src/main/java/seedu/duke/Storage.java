package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles loading and saving of entries to a text file.
 */
public class Storage {
    // relative path of 'F:\repos\tp\mt.txt' directory
    private static final String FILE_PATH = "mt.txt";
    private static final String BUDGET_FILE_PATH = "budgets.txt";
    private final MTLogger logger;

    /**
     * Initializes a new Storage instance with a logger.
     */
    public Storage() {
        this.logger = new MTLogger(Storage.class.getName());
    }

    /**
     * Saves all entries to the storage file.
     *
     * @param moneyList List of entries to save
     * @throws MTException If there's an error writing to file
     */
    public void saveExpenses(ArrayList<String> moneyList) throws MTException {
        logger.logInfo("Saving entries into " + FILE_PATH);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (String entry: moneyList) {
                writer.write(entry + "\n");
            }
        } catch (IOException error) {
            logger.logSevere("Error saving entries into " + FILE_PATH, error);
            throw new MTException("Error saving entries: " + error.getMessage());
        }
    }

    //@@author EdwinTun98
    public void saveBudgets(HashMap<String, Double> budgetList) throws MTException {
        try (FileWriter writer = new FileWriter(BUDGET_FILE_PATH)) {
            for (Map.Entry<String, Double> entry : budgetList.entrySet()) {
                writer.write(String.format("%s %.2f\n", entry.getKey(), entry.getValue()));
            }
        } catch (IOException e) {
            logger.logSevere("Failed to save budgets", e);
            throw new MTException("Error saving budgets: " + e.getMessage());
        }
    }
    //@@author

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
        } catch (FileNotFoundException error) {
            logger.logSevere("Failed to find file at " + FILE_PATH, error);
            throw new MTException("File not found. Starting with an empty list.");
        }

        return entries;
    }

    //@@author EdwinTun98
    public HashMap<String, Double> loadBudgets() throws MTException {
        HashMap<String, Double> budgets = new HashMap<>();
        File file = new File(BUDGET_FILE_PATH);

        if (!file.exists()) return budgets;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+", 2);
                if (parts.length == 2) {
                    String category = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    budgets.put(category, amount);
                }
            }
        } catch (FileNotFoundException e) {
            logger.logSevere("Budget file not found", e);
            throw new MTException("Budgets file not found.");
        } catch (NumberFormatException e) {
            throw new MTException("Corrupted budget entry: " + e.getMessage());
        }

        return budgets;
    }
    //@@author
}
