package seedu.duke;

import java.util.ArrayList;

public class MoneyList {
    private static final int INDEX_OFFSET = 1;

    private final ArrayList<String> moneyList;
    private final MTLogger logger;
    private final Storage storage;
    private final TextUI ui;
    private double totalBudget;

    public MoneyList(MTLogger logger, Storage storage, TextUI ui) {
        this.moneyList = new ArrayList<>();
        this.logger = logger;
        this.storage = storage;
        this.ui = ui;
        this.totalBudget = 0.0;
    }

    public ArrayList<String> getMoneyList() {
        return moneyList;
    }

    private int extractIndex(String input) {
        return Integer.parseInt(input.replaceAll("[^0-9]", "")) - INDEX_OFFSET;
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

    public void loadEntriesFromFile() throws MTException {
        ArrayList<String> loadedEntries = storage.loadEntries();
        if (loadedEntries != null) {
            moneyList.addAll(loadedEntries);
        }

        // Assert that moneyList is not null
        assert moneyList != null : "moneyList should not be null";

        logger.logInfo("Loaded " + moneyList.size() + " entries from file.");
        ui.print("Loaded " + moneyList.size() + " entries from file.");
    }

    public void addExpense(String input) throws MTException {
        try {
            // Assert that the input is not null and starts with "addExpense"
            assert input != null : "Input should not be null";
            assert input.startsWith("addExpense") : "Input should start with 'addExpense'";

            // remove all trailing, leading, and spaces between input
            input = input.replaceAll("\\s", "");
            String[] parts = input.substring(10).split("\\$/", 2);

            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new MTException("Your addExpense needs a description and a value.\n" +
                        "Format: addExpense <description> $/ <value>");
            } else {
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

    public void listSummary() throws MTException {
        if (moneyList.isEmpty()) {
            logger.logWarning("Expense list is empty.");
            throw new MTException("No entries available to display.");
        } else {
            ui.print("Expense list:");
            for (int i = 0; i < moneyList.size(); i++) {
                ui.print((i + INDEX_OFFSET) + ": " + moneyList.get(i));
            }
        }
    }

    public void findEntry(String input) throws MTException {
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
        } else {
            ui.print("Found Matching entries for: " + input);
            for (int i = 0; i < results.size(); i++) {
                ui.print((i + INDEX_OFFSET) + ": " + results.get(i));
            }
        }
    }

    public void getTotalExpense() {
        double total = 0.0;

        for (String entry : moneyList) {
            try {
                // Parse the amount from the entry string
                String[] parts = entry.split("Value=\\$");
                if (parts.length == 2) {
                    double amount = Double.parseDouble(parts[1].trim());
                    total += amount;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.logWarning("Error parsing amount from entry: " + entry);
            }
        }

        ui.print("Total expenses: " + total);
        logger.logInfo("Total expense calculated: " + total);
    }

    public void setTotalBudget(String input) {
        try {
            assert input != null : "Input should not be null";
            assert input.startsWith("setTotalBudget") : "Input should start with 'setTotalBudget'";

            // Remove the command and extract the budget value
            String budgetString = input.substring("setTotalBudget".length()).trim();

            // Parse the budget value from the string
            double budget = Double.parseDouble(budgetString);

            // Validate that the budget is not negative
            if (budget < 0) {
                logger.logWarning("Attempted to set a negative budget: " + budget);
                ui.print("Budget cannot be negative.");
                return;
            }

            // Set the total budget
            this.totalBudget = budget;
            logger.logInfo("Total budget set to: " + totalBudget);
            ui.print("Budget set to: $" + totalBudget);
        } catch (NumberFormatException e) {
            logger.logSevere("Invalid budget format: " + input, e);
            ui.print("Invalid budget format. Please enter a valid number " +
                    "(e.g., setTotalBudget 500.00).");
        } catch (Exception e) {
            logger.logSevere("Error setting budget: " + e.getMessage(), e);
            ui.print("An error occurred while setting the budget.");
        }
    }
}
