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
    //private double totalBudget;

    public MoneyList(MTLogger logger, Storage storage, TextUI ui) {
        this.moneyList = new ArrayList<>();
        this.logger = logger;
        this.storage = storage;
        this.ui = ui;
        //this.totalBudget = 0.0;
    }

    public ArrayList<String> getMoneyList() {
        return moneyList;
    }

    private int extractIndex(String input) {
        return Integer.parseInt(input.replaceAll("[^0-9]", ""))
                - INDEX_OFFSET;
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
            storage.saveExpenses(moneyList);
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

        HashMap<String, Budget> loadedBudgets = storage.loadBudgets();
        if (loadedBudgets != null) {
            budgetList.putAll(loadedBudgets);
        }

        // Assert that moneyList is not null
        assert moneyList != null : "moneyList should not be null";

        logger.logInfo("Loaded " + moneyList.size() + " entries from file.");
        ui.print("Loaded " + moneyList.size() + " entries from file.");
    }

    public void addExpense(String input) throws MTException {
        try {
            // Check that the input is not null and trim it.
            if (input == null) {
                throw new MTException("Input should not be null");
            }

            input = input.trim();

            // Default parameters
            String description = "";
            Double amount = 0.00;
            String category = "Uncategorized";
            String date = "no date"; // default date

            // Ensure the input contains the amount marker "$/"
            if (input.contains("$/")) {
                // Extract everything after "addExpense "
                String[] parts1 = input.substring(("addExp").length()).split("\\$/", 2);
                description = parts1[0].trim();
                logger.logInfo("Description: " + description);

                // Check for duplicate markers
                String afterAmountPart = parts1[1];
                if (afterAmountPart.split("c/").length - 1 > 1) {
                    throw new MTException("Invalid format. Multiple category markers detected.");
                }

                if (afterAmountPart.split("d/").length - 1 > 1) {
                    throw new MTException("Invalid format. Multiple date markers detected.");
                }

                // Process the amount, category, and date as before
                if (afterAmountPart.contains("c/")) {
                    // Split on "c/" to separate the amount from the category (and possibly date)
                    String[] parts2 = afterAmountPart.split("c/", 2);
                    //@@author limleyhooi
                    String amountString = parts2[0].trim();
                    // Validate and parse the amount
                    if (amountString.matches("-?\\d+(\\.\\d+)?")) {
                        amount = Double.parseDouble(amountString);
                    } else {
                        throw new NumberFormatException("Invalid amount format: " + amountString);
                    }

                    // Check if the category part contains the date marker "d/"
                    if (parts2[1].contains("d/")) {
                        String[] parts3 = parts2[1].split("d/", 2);
                        category = parts3[0].trim();
                        date = parts3[1].trim();
                    } else {
                        // No date provided; just use the category
                        category = parts2[1].trim();
                    }
                } else if (afterAmountPart.contains("d/")) {
                    // If there's no category marker but there is a date marker,
                    // split on "d/" to get the amount and date.
                    String[] parts2 = afterAmountPart.split("d/", 2);
                    String amountString = parts2[0].trim();
                    if (amountString.matches("-?\\d+(\\.\\d+)?")) {
                        amount = Double.parseDouble(amountString);
                    } else {
                        throw new NumberFormatException("Invalid amount format: " + amountString);
                    }

                    date = parts2[1].trim();
                } else {
                    // If neither category nor date is provided, treat the entire string as the amount.
                    String amountString = afterAmountPart.trim();
                    if (amountString.matches("-?\\d+(\\.\\d+)?")) {
                        amount = Double.parseDouble(amountString);
                    } else {
                        throw new NumberFormatException("Invalid amount format: " + amountString);
                    }
                }

                DecimalFormat df = new DecimalFormat("#.00");
                amount = Double.valueOf(df.format(amount));

                logger.logInfo("Amount after formatting: " + amount);
            } else {
                throw new MTException("Invalid format. Use: addExp" +
                        " <description> $/<amount> [c/<category>] [d/<date>]");
            }

            // amount cannot be negative
            if (amount <= 0) {
                throw new MTException("Amount must be greater than zero.");
            }

            Expense newExpense = new Expense(description, amount, category, date);

            moneyList.add(newExpense.toString());
            logger.logInfo("Added expense: " + newExpense);
            ui.print("Expense added: " + newExpense);
            storage.saveExpenses(moneyList);

        } catch (NumberFormatException error) {
            logger.logSevere("Invalid amount format: " + input, error);
            throw new MTException("Invalid amount format. Please ensure it is a numeric value.");
        } catch (Exception error) {
            logger.logSevere("Error adding expense: " + error.getMessage(), error);
            throw new MTException("Failed to add expense: " + error.getMessage());
        }
    }

    //@@author limleyhooi
    public void addIncome(String input) throws MTException {
        try {
            if (input == null) {
                throw new MTException("Input should not be null");
            }
            input = input.trim();

            //  input format: addIncome <description> $/<amount> [d/<date>]
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
    public void editExpense(int index, String newDesc, Double newAmount,
                            String newCat, String newDate) throws MTException {
        validateIndex(index);

        String oldEntry = moneyList.get(index);

        if (!oldEntry.startsWith("Expense: ")) {
            throw new MTException("Entry not in expected format!");
        }

        String stripped = oldEntry.substring("Expense: ".length()).trim();
        int dollarIndex = stripped.indexOf('$');

        if (dollarIndex < 1) {
            throw new MTException("Corrupted old entry: missing $ for amount.");
        }

        String oldDescription = stripped.substring(0, dollarIndex).trim();

        int openBrace = stripped.indexOf('{', dollarIndex);
        if (openBrace == -1) {
            throw new MTException("Corrupted old entry: missing { for category.");
        }

        String amountPart = stripped.substring(dollarIndex + 1, openBrace).trim();
        double oldAmount = Double.parseDouble(amountPart);

        int closeBrace = stripped.indexOf('}', openBrace);
        if (closeBrace == -1) {
            throw new MTException("Corrupted old entry: missing } for category.");
        }

        String oldCategory = stripped.substring(openBrace + 1, closeBrace).trim();

        int openBracket = stripped.indexOf('[', closeBrace);
        int closeBracket = stripped.indexOf(']', openBracket);
        if (openBracket == -1 || closeBracket == -1) {
            throw new MTException("Corrupted old entry: missing [ or ] for date.");
        }

        String oldDate = stripped.substring(openBracket + 1, closeBracket).trim();

        if (newDesc == null || newDesc.isEmpty()) {
            newDesc = oldDescription;
        }
        if (newAmount <= 0.00) {
            newAmount = oldAmount;
        }
        if (newCat == null || newCat.isEmpty()) {
            newCat = oldCategory;
        }
        if (newDate == null || newDate.isEmpty()) {
            newDate = oldDate;
        }

        String updatedStr = String.format("Expense: %s $%.2f {%s} [%s]",
                newDesc, newAmount, newCat, newDate);

        moneyList.set(index, updatedStr);
        ui.print("Entry updated. " + updatedStr);
        storage.saveExpenses(moneyList);
    }

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

    public void listBudgets() throws MTException {
        if (budgetList.isEmpty()) {
            throw new MTException("No category budgets have been set.");
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
            throw new MTException("Please enter a valid keyword to search.");
        }

        // Print matching entries
        ui.print("Found Matching entries for: " + input);
        for (int i = 0; i < results.size(); i++) {
            ui.print((i + INDEX_OFFSET) + ": " + results.get(i));
        }
    }

    public void setCategoryLimit(String category, double amount) throws MTException {
        if (amount < 0) {
            throw new MTException("Category budget cannot be negative.");
        }

        Budget budget = new Budget(category, amount);
        budgetList.put(category, budget);

        ui.print("Budget for category '" + category + "' set to $" + String.format("%.2f", amount));
        logger.logInfo("Set budget: " + category + " = " + amount);
        storage.saveBudgets(budgetList);
    }

    public void checkExpenses(String categoryInput) throws MTException {
        if (categoryInput == null || categoryInput.trim().isEmpty()) {
            throw new MTException("Please specify a category or use 'Total'.");
        }

        String input = categoryInput.trim();
        boolean isCategoryCheck = !input.equalsIgnoreCase("Overall");

        if (isCategoryCheck) {
            String targetCategory = input.toLowerCase();
            Budget budget = budgetList.get(targetCategory);

            if (budget == null) {
                throw new MTException("No budget set for category: " + targetCategory);
            }

            double categoryExpense = getTotalExpenseValue(targetCategory);
            printCategoryBudgetSummary(budget, categoryExpense);
        } else {
            Budget totalBudget = budgetList.get("Overall");

            if (totalBudget == null) {
                throw new MTException("No Overall budget set.");
            }

            double totalExpense = getTotalExpenseValue(null);
            printTotalBudgetSummary(totalBudget, totalExpense);
        }
    }


    public double getTotalExpenseValue(String category) throws MTException {
        double totalExpenses = 0.0;
        for (String entry : moneyList) {
            if (entry.startsWith("Expense: ")) {
                try {
                    String[] entryPart1 = entry.split("\\$");
                    String[] entryPart2 = entryPart1[1].split("\\{");
                    double expensesAmount = Double.parseDouble(entryPart2[0].trim());

                    if (category == null) {
                        totalExpenses += expensesAmount;
                    } else {
                        String[] categorySplit = entryPart2[1].split("}", 2);
                        String categoryName = categorySplit[0].trim().toLowerCase();

                        if (categoryName.equals(category.toLowerCase())) {
                            totalExpenses += expensesAmount;
                        }
                    }
                } catch (Exception e) {
                    logger.logWarning("Error parsing entry for expense: " + entry);
                }
            }
        }
        return totalExpenses;
    }

    private void printTotalBudgetSummary(Budget totalBudget, double totalExpenses) {
        ui.print("-------- OVERALL BUDGET EXPENSES SUMMARY --------");
        ui.print(String.format(totalBudget.toString()));
        ui.print(String.format("Overall Expenses: $%.2f", totalExpenses));
        ui.print(String.format("Remaining: $%.2f", totalBudget.getAmount() - totalExpenses));
    }

    private void printCategoryBudgetSummary(Budget budget, double spent) {
        ui.print("-------- CATEGORY EXPENSES BUDGET CHECK --------");
        ui.print(budget.toString());
        ui.print(String.format("Budget: $%.2f", budget.getAmount()));
        ui.print(String.format("Total Spent: $%.2f", spent));
        ui.print(String.format("Remaining: $%.2f", budget.getAmount() - spent));
    }
    //@@author

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

    //@@author Hansel-K
/*    public void setTotalBudget(String input) throws MTException {
        try {
            assert input != null : "Input should not be null";
            assert input.startsWith("setTotBgt") : "Input should start with 'setBgt'";

            // Remove the command and extract the budget value
            String budgetString = input.substring("setTotBgt".length()).trim();

            // Parse the budget value from the string
            Double budget = Double.parseDouble(budgetString);

            // Format the budget to 2 decimal places
            DecimalFormat df = new DecimalFormat("#.00");
            budget = Double.valueOf(df.format(budget));
            logger.logInfo("Total budget after df formatting: " + budget);

            // Validate that the budget is not negative
            if (budget < 0) {
                logger.logWarning("Attempted to set a negative budget: " + budget);
                ui.print("Budget cannot be negative.");
                return;
            }

            // Set the total budget
            this.totalBudget = budget;

            logger.logInfo(String.format("Total budget set to: $%.2f", totalBudget));
            ui.print(String.format("Total budget set to: $%.2f", totalBudget));
        } catch (NumberFormatException e) {
            logger.logSevere("Invalid budget format: " + input, e);
            throw new MTException("Invalid amount format. Please ensure it is a numeric value.");
        } catch (Exception e) {
            logger.logSevere("Error setting budget: " + e.getMessage(), e);
            ui.print("An error occurred while setting the budget.");
        }
    }*/

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
            Budget OverallBudgetSet = new Budget("Overall", budget);
            budgetList.put("Overall", OverallBudgetSet);

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

    public double getTotalBudget() {
        Budget total = budgetList.get("Overall");
        return (total == null) ? 0.0 : total.getAmount();
    }
    //@@author

    public void listCats() {
        try {
            if (moneyList.isEmpty()) {
                ui.print("No entries available to display categories.");
                logger.logWarning("The money list is empty. No categories to display.");
                return;
            }

            // Use a LinkedHashSet to store unique categories while preserving order
            LinkedHashSet<String> categories = new LinkedHashSet<>();

            for (String entry : moneyList) {
                try {
                    // Filter off the string value until after {
                    String beforeCat = entry.substring(entry.indexOf("{") + 1).trim();

                    // Extract the string value before }
                    String[] parts = beforeCat.split("}", 2);

                    String category = parts[0];

                    categories.add(category);

                } catch (Exception e) {
                    logger.logWarning("Error extracting category from entry: " + entry);
                }
            }

            if (categories.isEmpty()) {
                ui.print("No categories found.");
                logger.logWarning("No categories could be extracted from the money list.");
                return;
            }

            // Display the unique categories in the order they were added
            ui.print("Categories (in order of appearance):");
            for (String category : categories) {
                ui.print("- " + category);
            }

            logger.logInfo("Displayed " + categories.size() + " unique categories.");
        } catch (Exception e) {
            logger.logSevere("An error occurred while listing categories: " + e.getMessage(), e);
            ui.print("An error occurred while listing categories. Please try again.");
        }
    }

    public void clearEntries() throws MTException {
        if (moneyList.isEmpty()) {
            ui.print("No entries to clear");
            return;
        }

        moneyList.clear();
        storage.saveExpenses(moneyList);

        logger.logInfo("All entries have been cleared from the money list.");
        ui.print("All entries cleared");
    }
}
