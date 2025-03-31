package seedu.duke;

/**
 * Represents a command to be executed by the MoneyTrail application.
 */
public interface Command {

    void execute(MoneyList moneyList) throws MTException;


    boolean isExit();
}


class ListCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.listSummary();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


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
    public boolean isExit() {
        return false;
    }
}


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
    public boolean isExit() {
        return false;
    }
}


class TotalExpenseCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        moneyList.getTotalExpense();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


class BudgetCommand implements Command {
    private final double budget;

    public BudgetCommand(double budget) {
        this.budget = budget;
    }

    @Override
    public void execute(MoneyList moneyList) {
        String budgetCommand = "setTotalBudget " + budget;
        moneyList.setTotalBudget(budgetCommand);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}



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

        String expenseCommand = "addExpense " + description + " $/" + amount;
        if (!category.equals("Uncategorizned")) {
            expenseCommand += " c/" + category;
        }
        // Include the date if it's provided (i.e., not the default "no date").
        if (!date.equals("no date")) {
            expenseCommand += " d/" + date;
        }
        moneyList.addExpense(expenseCommand);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class EditExpenseCommand implements Command {
    private final int index;
    private final String newDescription;
    private final double newAmount;
    private final String newCategory;
    private final String newDate;

    public EditExpenseCommand(int index, String newDescription, double newAmount, String newCategory, String newDate) {
        this.index = index;
        this.newAmount = newAmount;
        this.newCategory = newCategory;
        this.newDate = newDate;
        this.newDescription = newDescription;
    }

    @Override
    public void execute(MoneyList moneyList) throws MTException {
        moneyList.editExpense(index, newDescription, newAmount, newCategory, newDate);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class ListCatsCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        moneyList.listCats();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


class HelpCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        //  handled in the MoneyTrail class
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


class ExitCommand implements Command {
    @Override
    public void execute(MoneyList moneyList) {
        // No action needed for exit
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
