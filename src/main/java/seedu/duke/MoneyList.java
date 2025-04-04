package seedu.duke;

import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class MoneyList {
    private static final int INDEX_OFFSET = 1;

    private final ArrayList<String> moneyList;
    private final HashMap<String, Budget> budgetList = new HashMap<>();
    private final MTLogger logger;
    private final Storage storage;
    private final TextUI ui;

    /**
     * Creates a MoneyList instance with the specified logger, storage, and UI handler.
     */
    public MoneyList(MTLogger logger, Storage storage, TextUI ui) {
        this.moneyList = new ArrayList<>();
        this.logger = logger;
        this.storage = storage;
        this.ui = ui;
    }

    /** @return The list of all money entries (expenses/incomes) */
    public ArrayList<String> getMoneyList() {
        return moneyList;
    }

    /** @return The map of budget categories to their Budget objects */
    public HashMap<String, Budget> getBudgetList() {
        return budgetList;
    }

    /**
     * Extracts index number from user input.
     * @param input Raw user input (e.g., "delete 1")
     * @return Zero-based index number
     */
    private int extractIndex(String input) {
        return Integer.parseInt(input.replaceAll("[^0-9]", ""))
                - INDEX_OFFSET;
    }

    /**
     * Validates if index exists in money list.
     * @param index Index to validate
     * @throws MTException If index is out of bounds
     */
    private void validateIndex(int index) throws MTException {
        if (index < 0 || index >= moneyList.size()) {
            logger.logWarning("Invalid index provided: " + index);
            throw new MTException("Invalid or unavailable entry number.");
        }
    }

    /**
     * Deletes an entry by its index number.
     * @param input The delete command with index (e.g., "delete 1")
     * @throws MTException If index is invalid or entry doesn't exist
     */
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
            storage.saveExpenses(moneyList);
            // print out number of items left in moneyList
            ui.printNumItems(moneyList.size());
        } catch (NumberFormatException error) {
            logger.logSevere("Invalid delete command format: " + input, error);
            throw new MTException("Use: delete <ENTRY_NUMBER>");
        }
    }

    /**
     * Loads all entries and budgets from storage file.
     * @throws MTException If file loading fails
     */
    public void loadEntriesFromFile() throws MTException {
        ArrayList<String> loadedEntries = storage.loadEntries();
        if (loadedEntries != null) {
            moneyList.addAll(loadedEntries);
        }

        HashMap<String, Budget> loadedBudgets = storage.loadBudgets();
        if (loadedBudgets != null) {
            budgetList.putAll(loadedBudgets);
        }

        // Assert that moneyList is not null
        assert moneyList != null : "moneyList should not be null";

        logger.logInfo("Loaded " + moneyList.size() + " entries from file.");
        ui.print("Loaded " + moneyList.size() + " entries from file.");
    }

    //@@author Hansel-K
    /**
     * Adds a new expense entry from user input.
     * @param input Expense details in format: "addExp desc $/amt c/cat d/date"
     * @throws MTException If input format is invalid
     */
    public void addExpense(String input) throws MTException {
        try {
            validateInput(input);
            input = input.trim(); // Remove unnecessary spaces

            // Default parameters
            String description = "";
            Double amount = 0.00;
            String category = "Uncategorized"; // Default category

            String date = "no date"; // Default date

            if (input.contains("$/")) {
                description = extractDescription(input);
                String afterAmountPart = extractAfterAmountPart(input);

                validateMarkers(afterAmountPart);

                amount = extractAmount(afterAmountPart);
                category = extractCategory(afterAmountPart);
                date = extractDate(afterAmountPart);

                amount = formatAmount(amount);

                logger.logInfo("Amount after formatting: " + amount);
            } else {
                throw new MTException("Invalid format. Use: addExp <description> $/<amount> [c/<category>] [d/<date>]");
            }

            validateAmount(amount);
            saveExpense(description, amount, category, date);
        } catch (NumberFormatException error) {
            logger.logSevere("Invalid amount format: " + input, error);
            throw new MTException("Invalid amount format. Please ensure it is a numeric value.");
        } catch (Exception error) {
            logger.logSevere("Error adding expense: " + error.getMessage(), error);
            throw new MTException("Failed to add expense: " + error.getMessage());
        }
    }

    /**
     * Validates expense input isn't null/empty.
     * @param input User input to validate
     * @throws MTException If input is invalid
     */
    private void validateInput(String input) throws MTException {
        if (input == null) {
            throw new MTException("Input should not be null.");
        }
    }

    /**
     * Extracts description from expense input.
     * @param input Raw user input
     * @return Isolated description text
     */
    private String extractDescription(String input) {
        String[] parts1 = input.substring(("addExp").length()).split("\\$/", 2);
        String description = parts1[0].trim();
        logger.logInfo("Description: " + description);
        return description;
    }

    /**
     * Gets portion of input after amount marker.
     * @param input Raw user input
     * @return Substring after "$/"
     */
    private String extractAfterAmountPart(String input) {
        return input.substring(("addExp").length()).split("\\$/", 2)[1];
    }

    /**
     * Checks for duplicate category/date markers.
     * @param afterAmountPart Input portion after amount
     * @throws MTException If duplicate markers found
     */
    private void validateMarkers(String afterAmountPart) throws MTException {
        if (afterAmountPart.split("c/").length - 1 > 1) {
            throw new MTException("Invalid format. Multiple category markers detected.");
        }
        if (afterAmountPart.split("d/").length - 1 > 1) {
            throw new MTException("Invalid format. Multiple date markers detected.");
        }
    }

    /**
     * Parses amount value from input segment.
     * @param afterAmountPart Input portion containing amount
     * @return Parsed double value
     * @throws NumberFormatException If amount format invalid
     */
    private Double extractAmount(String afterAmountPart) throws NumberFormatException {
        String amountString = afterAmountPart.split("c/|d/", 2)[0].trim();
        if (amountString.matches("-?\\d+(\\.\\d+)?")) {
            return Double.parseDouble(amountString);
        } else {
            throw new NumberFormatException("Invalid amount format: " + amountString);
        }
    }

    /**
     * Extracts category from input segment.
     * @param afterAmountPart Input portion after amount
     * @return Category name or "Uncategorized"
     */
    private String extractCategory(String afterAmountPart) {
        if (afterAmountPart.contains("c/")) {
            return afterAmountPart.split("c/")[1].split("d/", 2)[0].trim();
        }
        return "Uncategorized"; // Default category
    }

    /**
     * Extracts date from input segment.
     * @param afterAmountPart Input portion after amount
     * @return Date string or "no date"
     */
    private String extractDate(String afterAmountPart) {
        if (afterAmountPart.contains("d/")) {
            return afterAmountPart.split("d/", 2)[1].trim();
        }
        return "no date"; // Default date
    }

    /**
     * Formats amount to 2 decimal places.
     * @param amount Raw amount value
     * @return Formatted double value
     */
    private Double formatAmount(Double amount) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(amount));
    }

    /**
     * Validates amount is positive.
     * @param amount Value to check
     * @throws MTException If amount ≤ 0
     */
    private void validateAmount(Double amount) throws MTException {
        if (amount <= 0) {
            throw new MTException("Amount must be greater than zero.");
        }
    }

    /**
     * Creates and saves new expense entry.
     * @param description Expense description
     * @param amount Formatted amount
     * @param category Expense category
     * @param date Expense date
     * @throws MTException If save fails
     */
    private void saveExpense(String description, Double amount, String category, String date) throws MTException {
        Expense newExpense = new Expense(description, amount, category, date);
        moneyList.add(newExpense.toString()); // Add the expense to the money list
        logger.logInfo("Added expense: " + newExpense); // Log the new expense
        ui.print("Expense added: " + newExpense); // Print confirmation to the user
        storage.saveExpenses(moneyList); // Persist the expense data
    }
    //@@author

    //@@author limleyhooi
    /**
     * Adds a new income entry from user input.
     * @param input Income details in format: "addIncome desc $/amt d/date"
     * @throws MTException If input format is invalid
     */
    public void addIncome(String input) throws MTException {
        try {
            if (input == null) {
                throw new MTException("Input should not be null");
            }
            input = input.trim();

            if (!input.startsWith("addIncome") || !input.contains("$/")) {
                throw new MTException("Invalid format. Use: addIncome <description> $/<amount> [d/<date>]");
            }

            String content = input.substring("addIncome".length()).trim();

            String[] parts = content.split("\\$/", 2);
            String description = parts[0].trim();
            String remainder = parts[1].trim();

            double amount = 0.0;
            String date = "no date";

            // Check if there's a date marker "d/" in the remainder
            if (remainder.contains("d/")) {
                String[] parts2 = remainder.split("d/", 2);
                String amountString = parts2[0].trim();
                amount = Double.parseDouble(amountString);
                date = parts2[1].trim();
            } else {
                amount = Double.parseDouble(remainder.trim());
            }

            if (amount <= 0) {
                throw new MTException("Amount must be greater than zero.");
            }

            Income newIncome = new Income(description, amount, date);
            moneyList.add(newIncome.toString());
            logger.logInfo("Added income: " + newIncome);
            ui.print("Income added: " + newIncome);
            storage.saveExpenses(moneyList);
        } catch (NumberFormatException error) {
            logger.logSevere("Invalid amount format in addIncome: " + input, error);
            throw new MTException("Invalid amount format. Please ensure it is a numeric value.");
        } catch (Exception error) {
            logger.logSevere("Error adding income: " + error.getMessage(), error);
            throw new MTException("Failed to add income: " + error.getMessage());
        }
    }//@@author

    //@@author EdwinTun98
    /**
     * Edits an existing expense entry in the money list.
     *
     * @param index     The index of the entry to be edited.
     * @param newDesc   The new description (optional).
     * @param newAmount The new amount (optional).
     * @param newCat    The new category (optional).
     * @param newDate   The new date (optional).
     * @throws MTException If the index is invalid or the entry is corrupted.
     */
    public void editExpense(int index, String newDesc, Double newAmount,
                            String newCat, String newDate) throws MTException {
        // Check if the provided index is within the bounds
        validateIndex(index);

        // Get the old string entry and convert it to an Expense object
        String oldEntry = moneyList.get(index);
        Expense oldExpense = Expense.parseString(oldEntry);

        // If no new description is provided, use the old one
        if (newDesc == null || newDesc.isEmpty()) {
            newDesc = oldExpense.getDescription();
        }
        if (newAmount <= 0.00) {
            newAmount = oldExpense.getAmount();
        }
        if (newCat == null || newCat.isEmpty()) {
            newCat = oldExpense.getCategory();
        }
        if (newDate == null || newDate.isEmpty()) {
            newDate = oldExpense.getDate();
        }

        Expense updatedExpense = new Expense(newDesc, newAmount, newCat, newDate);
        moneyList.set(index, updatedExpense.toString());

        moneyList.set(index, updatedExpense.toString());
        ui.print("Entry updated. " + updatedExpense);
        logger.logInfo("Entry updated: " + updatedExpense);
        storage.saveExpenses(moneyList);
    }

    /**
     * Lists all expense entries with formatted output.
     *
     * @throws MTException If the money list is empty.
     */
    public void listSummary() throws MTException {
        if (moneyList.isEmpty()) {
            logger.logWarning("Expense list is empty.");
            throw new MTException("No entries available to display.");
        }

        ui.print("Expense list:");
        for (int i = 0; i < moneyList.size(); i++) {
            ui.print((i + INDEX_OFFSET) + ": " + moneyList.get(i));
        }
    }

    /**
     * Displays all budget categories and their limits.
     * @throws MTException If no budgets exist
     */
    public void listBudgets() throws MTException {
        if (budgetList.isEmpty()) {
            throw new MTException("No budgets have been set.");
        }

        ui.print("-------- Overall Budgets --------");

        // 1. Print the total budget first if it exists
        Budget total = budgetList.get("Overall");
        if (total != null) {
            ui.print("- " + total.toString());
            ui.print(" ");
        }

        ui.print("-------- Category Budgets: --------");
        // 2. Print the other budgets (skip "total")
        for (Map.Entry<String, Budget> entry : budgetList.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("Overall")) {
                ui.print("- " + entry.getValue().toString());
                ui.print(" ");
            }
        }
    }

    /**
     * Finds entries containing the search term.
     * @param input The keyword to search for
     * @throws MTException If no matches found
     */
    public void findEntry(String input) throws MTException {
        // Validate the input for null, empty, or whitespace-only
        if (input == null || input.trim().isEmpty()) {
            logger.logWarning("Invalid entry provided.");
            throw new MTException("Please enter a keyword to search.");
        }

        ArrayList<String> results = new ArrayList<>();

        // Iterate through the moneyList to find case-insensitive matches
        for (String entry : moneyList) {
            if (entry.toLowerCase().contains(input.toLowerCase())) {
                results.add(entry);
            }
        }

        // Handle the case when no matches are found
        if (results.isEmpty()) {
            logger.logWarning("No matching entries found for: " + input);
            throw new MTException("No matching entries found for: " + input
                    + ". Please enter a valid keyword to search.");
        }

        // Print matching entries cat
        ui.print("Found Matching entries for: " + input);
        for (int i = 0; i < results.size(); i++) {
            ui.print((i + INDEX_OFFSET) + ": " + results.get(i));
        }
    }

    public void setCategoryLimit(String category, double amount) throws MTException {
        setCategoryLimit(category, String.valueOf(amount));
    }

    /**
     * Sets spending limit for a specific category.
     * @param category The budget category name
     * @param amountStr The limit amount as string
     * @throws MTException If amount is invalid
     */
    public void setCategoryLimit(String category, String amountStr) throws MTException {
        if (isEmptyOrNull(amountStr)) {
            throw new MTException("Budget amount cannot be empty.");
        }

        double amount;

        try {
            amount = Double.parseDouble(amountStr.trim());
        } catch (NumberFormatException e) {
            throw new MTException("Invalid amount. Please enter a valid number.");
        }

        if (amount < 0) {
            throw new MTException("Category budget cannot be negative.");
        }

        Budget budget = new Budget(category, amount);
        budgetList.put(category, budget);

        ui.print("Budget for category '" + category + "' set to $" + String.format("%.2f", amount));
        logger.logInfo("Set budget: " + category + " = " + amount);

        storage.saveBudgets(budgetList);
    }

    /**
     * Compares expenses against budget for a category.
     * @param budgetInput Category name or "Overall" for total budget
     * @throws MTException If budget isn't set
     */
    public void checkExpenses(String budgetInput) throws MTException {
        if (isEmptyOrNull(budgetInput)) {
            throw new MTException("Please specify a category or use 'Overall'.");
        }

        if (isTotalBudgetCheck(budgetInput)) {
            handleTotalBudgetCheck();
        } else {
            handleCategoryBudgetCheck(budgetInput.trim().toLowerCase());
        }
    }

    /**
     * Checks if string is null/empty.
     * @param input String to check
     * @return True if null or whitespace
     */
    private boolean isEmptyOrNull(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Determines if checking total budget.
     * @param input User input to check
     * @return True if input is "Overall"
     */
    private boolean isTotalBudgetCheck(String input) {
        return input.equalsIgnoreCase("Overall");
    }

    /**
     * Handles total budget expense check.
     * @throws MTException If no total budget set
     */
    private void handleTotalBudgetCheck() throws MTException {
        Budget overAllBudget = budgetList.get("Overall");
        if (overAllBudget == null) {
            logger.logWarning("No Overall budget set.");
            throw new MTException("No Overall budget set.");
        }
        double overallExpense = getTotalExpenseValue(null);
        printTotalBudgetSummary(overAllBudget, overallExpense);
    }

    /**
     * Handles category-specific budget check.
     * @param category Budget category to check
     * @throws MTException If category budget not set
     */
    private void handleCategoryBudgetCheck(String category) throws MTException {
        Budget categoryBudget = budgetList.get(category);
        if (categoryBudget == null) {
            logger.logWarning("No category budget set.");
            throw new MTException("No category budget set.");
        }

        double expenses = getTotalExpenseValue(category);
        printCategoryBudgetSummary(categoryBudget, expenses);
    }

    /**
     * Calculates total expenses for a category.
     * @param category Specific category or null for all expenses
     * @return The summed expense amount
     */
    public double getTotalExpenseValue(String category) {
        double totalExpenses = 0.0;

        for (String entry : moneyList) {
            if (entry.startsWith("Expense: ")) {
                try {
                    Expense expense = Expense.parseString(entry);
                    if (category == null || expense.getCategory().equalsIgnoreCase(category)) {
                        totalExpenses += expense.getAmount();
                    }
                } catch (MTException e) {
                    logger.logWarning("Skipping malformed expense entry: " + entry);
                }
            }
        }

        return totalExpenses;
    }

    /**
     * Prints total budget summary comparison.
     * @param totalBudget Overall Budget object
     * @param totalExpenses Calculated expense total
     */
    private void printTotalBudgetSummary(Budget totalBudget, double totalExpenses) {
        ui.print("-------- OVERALL BUDGET EXPENSES SUMMARY --------");
        ui.print(String.format(totalBudget.toString()));
        ui.print(String.format("Overall Expenses: $%.2f", totalExpenses));
        ui.print(String.format("Remaining: $%.2f", totalBudget.getAmount() - totalExpenses));
    }

    /**
     * Prints category budget summary.
     * @param budget Category Budget object
     * @param spent Calculated spending amount
     */
    private void printCategoryBudgetSummary(Budget budget, double spent) {
        ui.print("-------- CATEGORY EXPENSES BUDGET CHECK --------");
        ui.print(budget.toString());
        ui.print(String.format("Total Spent: $%.2f", spent));
        ui.print(String.format("Remaining: $%.2f", budget.getAmount() - spent));
    }
    //@@author

    //@@author Hansel-K
    /** Displays the sum of all expenses */
    public void getTotalExpense() {
        double total = 0.0;

        for (String entry : moneyList) {
            try {
                // Split entry data to get entry amount
                String[] parts1 = entry.split("\\$");
                String[] parts2 = parts1[1].split("\\{");

                if (parts1.length == 2 && parts2.length == 2) {
                    double expenseAmount = Double.parseDouble(parts2[0].trim());
                    logger.logInfo("Expense amount: " + expenseAmount);

                    total += expenseAmount;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.logWarning("Error parsing amount from entry: " + entry);
            }
        }

        ui.print(String.format("Total expenses: $%.2f", total));
        logger.logInfo("Total expense calculated: " + String.format("%.2f", total));
    }

    public void setTotalBudget(String input) throws MTException {
        try {
            assert input != null : "Input should not be null";
            assert input.startsWith("setTotBgt") : "Input should start with 'setTotBgt'";

            // Extract the budget value after the command
            String budgetString = input.substring("setTotBgt".length()).trim();
            Double budget = Double.parseDouble(budgetString);

            // Validate amount
            if (budget < 0) {
                logger.logWarning("Attempted to set a negative budget: " + budget);
                ui.print("Budget cannot be negative.");
                return;
            }

            // Format to 2 decimal places
            DecimalFormat df = new DecimalFormat("#.00");
            budget = Double.valueOf(df.format(budget));

            // Store as "TOTAL" category
            Budget overallBudgetSet = new Budget("Overall", budget);
            budgetList.put("Overall", overallBudgetSet);

            // Save budgets to file
            storage.saveBudgets(budgetList);

            logger.logInfo(String.format("Total budget set to: $%.2f", budget));
            ui.print(String.format("Total budget set to: $%.2f", budget));
        } catch (NumberFormatException e) {
            logger.logSevere("Invalid budget format: " + input, e);
            throw new MTException("Invalid amount format. Please ensure it is a numeric value.");
        } catch (Exception e) {
            logger.logSevere("Error setting budget: " + e.getMessage(), e);
            ui.print("An error occurred while setting the budget.");
        }
    }

    /** @return The current total budget amount */
    public double getTotalBudget() {
        Budget total = budgetList.get("Overall");
        return (total == null) ? 0.0 : total.getAmount();
    }
    //@@author

    //@@author Hansel-K
    /** Displays all unique expense categories */
    public void listCats() {
        try {
            // Check if the money list is empty
            if (moneyList.isEmpty()) {
                handleEmptyMoneyList(); // Handle empty money list scenario
                return;
            }

            // Extract unique categories from the money list
            LinkedHashSet<String> categories = extractUniqueCategories();

            // Check if no categories were found
            if (categories.isEmpty()) {
                handleNoCategoriesFound(); // Handle no categories scenario
                return;
            }

            // Display the unique categories to the user
            displayCategories(categories);
        } catch (Exception e) {
            // Handle any exceptions that occur while listing categories
            handleListCategoriesError(e);
        }
    }

    /**
     * Handles empty money list case for categories.
     */
    private void handleEmptyMoneyList() {
        ui.print("No entries available to display categories."); // Inform user
        logger.logWarning("The money list is empty. No categories to display."); // Log warning
    }

    /**
     * Extracts unique categories from entries.
     * @return Set of unique category names
     */
    private LinkedHashSet<String> extractUniqueCategories() {
        LinkedHashSet<String> categories = new LinkedHashSet<>(); // To preserve order and ensure uniqueness

        // Iterate through each entry in the money list
        for (String entry : moneyList) {
            try {
                // Extract the category from the entry string
                String category = extractCategoryFromEntry(entry);
                categories.add(category); // Add the category to the set
            } catch (Exception e) {
                // Log a warning if category extraction fails
                logger.logWarning("Error extracting category from entry: " + entry);
            }
        }
        return categories; // Return the unique categories
    }

    /**
     * Parses category from entry string.
     * @param entry Full entry string
     * @return Extracted category name
     */
    private String extractCategoryFromEntry(String entry) {
        // Extract the substring after "{" and trim any leading/trailing spaces
        String beforeCat = entry.substring(entry.indexOf("{") + 1).trim();

        // Split the string at "}" to get the category
        String[] parts = beforeCat.split("}", 2);

        return parts[0]; // Return the extracted category
    }

    /**
     * Handles no-categories case.
     */
    private void handleNoCategoriesFound() {
        ui.print("No categories found."); // Inform user
        logger.logWarning("No categories could be extracted from the money list."); // Log warning
    }

    /**
     * Displays categories to user.
     * @param categories Set of categories to display
     */
    private void displayCategories(LinkedHashSet<String> categories) {
        ui.print("Categories (in order of appearance):"); // Inform user

        // Iterate through and print each category
        for (String category : categories) {
            ui.print("- " + category);
        }
        logger.logInfo("Displayed " + categories.size() + " unique categories."); // Log number of categories displayed
    }

    /**
     * Handles category listing errors.
     * @param e Exception that occurred
     */
    private void handleListCategoriesError(Exception e) {
        logger.logSevere("An error occurred while listing categories: " + e.getMessage(), e); // Log severe error
        ui.print("An error occurred while listing categories. Please try again."); // Inform user
    }

    /**
     * Clears all entries from the money list.
     * If the money list is empty, it notifies the user and skips the clearing process.
     * Otherwise, it clears the list, saves the updated list to storage, and logs the action.
     *
     * @throws MTException if an error occurs while saving the cleared list to storage.
     */
    public void clearEntries() throws MTException {
        // Check if the money list is empty
        if (moneyList.isEmpty()) {
            // Notify the user that there are no entries to clear
            ui.print("No entries to clear");
            return; // Exit the method as there is nothing to do
        }
        // Clear all entries from the money list
        moneyList.clear();

        // Save the updated (now empty) money list to storage
        storage.saveExpenses(moneyList);

        // Log the action of clearing all entries for debugging and tracking purposes
        logger.logInfo("All entries have been cleared from the money list.");

        // Notify the user that all entries have been successfully cleared
        ui.print("All entries cleared");
    }
    //@@author
}
