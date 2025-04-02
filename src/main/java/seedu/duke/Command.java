package seedu.duke;

/**
 * Represents a command to be executed by the MoneyTrail application.
 * Commands can perform actions and indicate if they should terminate
 * the program.
 */
public interface Command {
    /**
     * Executes the command's primary operation.
     * @param moneyList The MoneyList to operate on
     * @throws MTException If command execution fails
     */
    void execute(MoneyList moneyList) throws MTException;

    /**
     * @return true if the application should exit after this command
     */
    boolean shouldExit();
}

//@@author EdwinTun98
/**
 * Lists all entries in the MoneyList.
 */
class ListCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.listSummary();
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

class ListBudgetCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.listBudgets();
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

/**
 * Finds entries containing the specified keyword.
 */
class FindCommand implements Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.findEntry(keyword);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
//@@author

/**
 * Deletes an entry at the specified index.
 */
class DeleteCommand implements Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        // Since MoneyList.deleteEntry expects the original "delete X" string,
        // need to recreate it based on the parsed index
        String deleteCommand = "delete " + (index + 1);
        moneyList.deleteEntry(deleteCommand);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

/**
 * Calculates and displays total expenses.
 */
class TotalExpenseCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        moneyList.getTotalExpense();
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

/**
 * Sets the total budget amount.
 */
class BudgetCommand implements Command {
    private final double budget;

    public BudgetCommand(double budget) {
        this.budget = budget;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        String budgetCommand = "setTotBgt " + budget;
        moneyList.setTotalBudget(budgetCommand);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

/**
 * Adds a new expense entry.
 */
class AddExpenseCommand implements Command {
    private final String description;
    private final double amount;
    private final String category;
    private final String date;

    public AddExpenseCommand(String description, double amount, String category, String date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        // Reconstruct the command string expected by MoneyList.addExpense.

        String expenseCommand = "addExp " + description + " $/" + amount;
        if (!category.equals("Uncategorized")) {
            expenseCommand += " c/" + category;
        }

        // Include the date if it's provided (i.e., not the default "no date").
        if (!date.equals("no date")) {
            expenseCommand += " d/" + date;
        }

        moneyList.addExpense(expenseCommand);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

//@@author limleyhooi
class AddIncomeCommand implements Command {
    private final String description;
    private final double amount;
    private final String date;

    public AddIncomeCommand(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        // Construct the command string as expected by MoneyList.addIncome
        String incomeCommand = "addIncome " + description + " $/" + amount;

        if (!date.equals("no date")) {
            incomeCommand += " d/" + date;
        }

        moneyList.addIncome(incomeCommand);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
//@@author

//@@author EdwinTun98
class EditExpenseCommand implements Command {
    private final int index;
    private final String newDescription;
    private final double newAmount;
    private final String newCategory;
    private final String newDate;

    public EditExpenseCommand(int index, String newDescription, double newAmount,
                              String newCategory, String newDate) {
        this.index = index;
        this.newDescription = newDescription;
        this.newAmount = newAmount;
        this.newCategory = newCategory;
        this.newDate = newDate;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.editExpense(index, newDescription, newAmount, newCategory, newDate);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

class SetCategoryBudgetCommand implements Command {
    private final String category;
    private final double amount;

    public SetCategoryBudgetCommand(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.setCategoryLimit(category, amount);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

// CheckBudgetCommand.java
class CheckExpensesCommand implements Command {
    private final String category;

    public CheckExpensesCommand(String category) {
        this.category = category;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.checkExpenses(category);
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}


//@@author

/**
 * Lists all available categories.
 */
class ListCatsCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        moneyList.listCats();
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

/**
 * Displays help information.
 */
class HelpCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        //  handled in the MoneyTrail class
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

/**
 * Terminates the application.
 */
class ExitCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        // No action needed for exit
    }

    @Override
    public boolean shouldExit() {
        return true;
    }
}
//@@author rchlai
/**
 * Clears the entry list.
 */
class ClearListCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.clearEntries();
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
//@@author
