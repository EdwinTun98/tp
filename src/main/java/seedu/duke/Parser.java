package seedu.duke;

/**
 * Handles the parsing of user input and converts it into commands to be executed by MoneyTrail.
 */
public class Parser {
    private final MTLogger logger;

    //@@author limleyhooi
    public Parser() {
        this.logger = new MTLogger(Parser.class.getName());
    }
    //@@author

    //@@author limleyhooi
    public Command parseCommand(String input) throws MTException {
        logger.logInfo("Parsing input: " + input);

        if (isNullOrEmpty(input)) {
            throw new MTException("Please enter a valid command.");
        }

        String trimmedInput = input.trim();

        return createCommandFromInput(trimmedInput);
    }
    //@@author

    //@@author limleyhooi
    private boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    //@@author

    //@@author limleyhooi
    private Command createCommandFromInput(String input) throws MTException {
        if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        }

        if (input.equalsIgnoreCase("exit")) {
            return new ExitCommand();
        }

        if (input.equalsIgnoreCase("listCat")) {
            return new ListCatsCommand();
        }
        if (input.equalsIgnoreCase("listBgt")) {
            return new ListBudgetCommand();
        }
        if (input.equalsIgnoreCase("help")) {
            return new HelpCommand();
        }

        if (input.equalsIgnoreCase("clear")) {
            return new ClearListCommand();
        }

        if (input.startsWith("totalExp")) {
            return new TotalExpenseCommand();
        }

        if (input.startsWith("find")) {
            return createFindCommand(input);
        }

        if (input.startsWith("check")) {
            return parserCheckExpenses(input);
        }

        if (input.startsWith("del")) {
            return createDeleteCommand(input);
        }

        if (input.startsWith("setTotBgt")) {
            return createBudgetCommand(input);
        }

        if (input.startsWith("setCatBgt")) {
            return parseSetCategoryBudgetCommand(input);
        }

        if (input.startsWith("addExp")) {
            return createAddExpenseCommand(input);
        }

        if (input.startsWith("edit")) {
            return createEditExpenseCommand(input);
        }

        if (input.startsWith("addIncome")) {
            return createAddIncomeCommand(input);
        }

        logger.logWarning("Unknown command: " + input);
        throw new MTException("Unknown command. Type 'help' for a " +
                "list of available commands.");
    }
    //@@author

    //@@author EdwinTun98
    private FindCommand createFindCommand(String input) throws MTException {
        String keyword = input.substring(5).trim();

        if (keyword.isEmpty()) {
            throw new MTException("Please enter a keyword to search.");
        }

        return new FindCommand(keyword);
    }
    //@@author

    //@@author limleyhooi
    private DeleteCommand createDeleteCommand(String input) throws MTException {
        try {
            int index = Integer.parseInt(input.replaceAll(
                    "[^0-9]", "")) - 1;

            return new DeleteCommand(index);
        } catch (NumberFormatException error) {
            throw new MTException("Use: delete <ENTRY_NUMBER>");
        }
    }
    //@@author

    //@@author limleyhooi
    private BudgetCommand createBudgetCommand(String input) throws MTException {
        try {
            String budgetString = input.substring("setTotBdt".length()).trim();
            double budget = Double.parseDouble(budgetString);

            if (budget < 0) {
                throw new MTException("Budget cannot be negative.");
            }

            return new BudgetCommand(budget);
        } catch (NumberFormatException error) {
            throw new MTException("Invalid budget format. " +
                    "Please enter a valid number.");
        }
    }
    //@@author

    //@@author EdwinTun98
    private AddExpenseCommand createAddExpenseCommand(String input) throws MTException {
        try {
            validateAddExpenseFormat(input);
            ExpenseData expenseData = parseExpenseData(input);

            return new AddExpenseCommand(
                    expenseData.description,
                    expenseData.amount,
                    expenseData.category,
                    expenseData.date
            );
        } catch (NumberFormatException error) {
            throw new MTException("Invalid amount format. " +
                    "Please ensure it is a numeric value.");
        } catch (IndexOutOfBoundsException error) {
            throw new MTException("Invalid format. " +
                    "Use: addExpense <description> $/<amount> [c/<category>] [d/<date>]");
        }
    }
    //@@author

    //@@author EdwinTun98
    private void validateAddExpenseFormat(String input) throws MTException {
        if (!input.startsWith("addExp") || !input.contains("$/")) {
            throw new MTException("Invalid format. " +
                    "Use: addExp <description> $/<amount> [c/<category>] [d/<date>]");
        }
    }
    //@@author

    //@@author EdwinTun98
    private ExpenseData parseExpenseData(String input) {
        String content = input.substring("addExp".length()).trim();
        String description = extractDescription(content);
        String remainder = content.substring(
                content.indexOf("$/") + 2).trim();

        return new ExpenseData(
                description,
                extractAmount(remainder),
                extractCategory(remainder),
                extractDate(remainder)
        );
    }

    private CheckExpensesCommand parserCheckExpenses(String input) throws MTException {
        String trimmed = input.substring("check".length()).trim();
        if (trimmed.isEmpty()) {
            throw new MTException("Missing category name. Usage: check c/<category or Total>");
        }

        return new CheckExpensesCommand(trimmed);
    }

    //@@author

    //@@author limleyhooi, EdwinTun98
    private String extractDescription(String content) {
        int amountIndex = content.indexOf("$/");

        return amountIndex > 0 ? content.substring(0, amountIndex)
                .trim() : "No description";
    }
    //@@author

    //@@author limleyhooi, EdwinTun98
    private double extractAmount(String remainder) {
        int categoryIndex = remainder.indexOf("c/");
        int dateIndex = remainder.indexOf("d/");

        String amountString = remainder.substring(
                0,
                Math.min(
                        categoryIndex != -1 ? categoryIndex : remainder.length(),
                        dateIndex != -1 ? dateIndex : remainder.length()
                )
        ).trim();

        return Double.parseDouble(amountString);
    }
    //@@author

    //@@author EdwinTun98, limleyhooi
    private String extractCategory(String remainder) {
        int categoryIndex = remainder.indexOf("c/");

        if (categoryIndex == -1) {
            return "Uncategorized";
        }

        int dateIndex = remainder.indexOf("d/");
        int endIndex = dateIndex != -1 && dateIndex > categoryIndex ?
                dateIndex : remainder.length();

        return remainder.substring(categoryIndex + 2, endIndex).trim();
    }
    //@@author

    //@@author EdwinTun98
    private String extractDate(String remainder) {
        int dateIndex = remainder.indexOf("d/");

        if (dateIndex == -1) {
            return "no date";
        }

        int categoryIndex = remainder.indexOf("c/");
        int endIndex = categoryIndex != -1 && categoryIndex >
                dateIndex ? categoryIndex : remainder.length();

        return remainder.substring(dateIndex + 2, endIndex).trim();
    }
    //@@author

    //@@author EdwinTun98
    private EditExpenseCommand createEditExpenseCommand(String input) throws MTException {
        try {
            EditCommandData editData = parseEditCommand(input);

            return new EditExpenseCommand(
                    editData.index,
                    editData.description,
                    editData.amount,
                    editData.category,
                    editData.date
            );
        } catch (NumberFormatException error) {
            throw new MTException("Invalid number format in edit command");
        } catch (MTException error) {
            throw error;
        } catch (Exception error) {
            throw new MTException("Invalid edit format");
        }
    }
    //@@author

    //@@author EdwinTun98
    private EditCommandData parseEditCommand(String input) throws MTException {
        String afterEdit = input.substring("edit".length()).trim();
        int firstSpace = afterEdit.indexOf(' ');

        if (firstSpace == -1) {
            throw new MTException("Invalid format. " +
                    "Use: edit <entry_number> [options]");
        }

        int index = Integer.parseInt(afterEdit.substring(0, firstSpace)) - 1;
        String rest = afterEdit.substring(firstSpace + 1).trim();

        return new EditCommandData(
                index,
                parseEditDescription(rest),
                parseEditAmount(rest),
                parseEditCategory(rest),
                parseEditDate(rest)
        );
    }
    //@@author

    //@@author EdwinTun98
    private String parseEditDescription(String input) {
        if (!input.contains("$/")) {
            int cIndex = input.indexOf("c/");
            int dIndex = input.indexOf("d/");

            if (cIndex == -1 && dIndex == -1) {
                return input.trim();
            }

            int end = Math.min(
                    cIndex != -1 ? cIndex : input.length(),
                    dIndex != -1 ? dIndex : input.length()
            );

            return input.substring(0, end).trim();
        }

        return null;
    }
    //@@author EdwinTun98

    //@@author EdwinTun98
    private double parseEditAmount(String input) {
        if (!input.contains("$/")) {
            return 0.00;
        }

        String afterDollar = input.substring(
                input.indexOf("$/") + 2).trim();
        int cIndex = afterDollar.indexOf("c/");
        int dIndex = afterDollar.indexOf("d/");

        String amountString = afterDollar.substring(
                0,
                Math.min(
                        cIndex != -1 ? cIndex : afterDollar.length(),
                        dIndex != -1 ? dIndex : afterDollar.length()
                )
        ).trim();

        return amountString.isEmpty() ? 0.00 : Double.parseDouble(amountString);
    }
    //@@author

    //@@author EdwinTun98
    private String parseEditCategory(String input) {
        if (!input.contains("c/")) {
            return null;
        }

        int cIndex = input.indexOf("c/");
        String afterCategory = input.substring(cIndex + 2).trim();

        int dIndex = afterCategory.indexOf("d/");

        return dIndex != -1 ? afterCategory.substring(0, dIndex)
                .trim() : afterCategory.trim();
    }
    //@@author

    //@@author EdwinTun98
    private String parseEditDate(String input) {
        if (!input.contains("d/")) {
            return null;
        }

        int dIndex = input.indexOf("d/");
        String afterDate = input.substring(dIndex + 2).trim();

        int cIndex = afterDate.indexOf("c/");

        return cIndex != -1 ? afterDate.substring(0, cIndex)
                .trim() : afterDate.trim();
    }
    //@@author

    //@@author EdwinTun98
    private SetCategoryBudgetCommand parseSetCategoryBudgetCommand(String input) throws MTException {
        String trimmed = input.substring("setCatBgt".length()).trim();

        if (!trimmed.startsWith("c/")) {
            throw new MTException("Invalid format. Use: setCatBgt c/<category> <amount>");
        }

        String[] parts = trimmed.split("\\s+", 2); // Expected: [ "c/food", "100" ]
        if (parts.length < 2) {
            throw new MTException("Missing budget value. Format: setCatBgt c/<category> <amount>");
        }

        String category = parts[0].substring(2).trim(); // removes "c/"
        String amountStr = parts[1].trim();

        if (category.isEmpty()) {
            throw new MTException("Category name cannot be empty.");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw new MTException("Invalid amount. Please enter a valid number.");
        }

        if (amount < 0) {
            throw new MTException("Budget cannot be negative.");
        }

        return new SetCategoryBudgetCommand(category, amount);
    }

    //add parserCheckExpense()

    //@@author

    //@@author limleyhooi
    private AddIncomeCommand createAddIncomeCommand(String input) throws MTException {
        try {
            IncomeData incomeData = parseIncomeData(input);

            return new AddIncomeCommand(
                    incomeData.description,
                    incomeData.amount,
                    incomeData.date
            );
        } catch (NumberFormatException error) {
            throw new MTException("Invalid amount format in " +
                    "addIncome command");
        }
    }
    //@@author

    //@@author limleyhooi
    private IncomeData parseIncomeData(String input) throws MTException {
        String content = input.substring("addIncome".length()).trim();
        String[] parts = content.split("\\$/", 2);

        if (parts.length < 2) {
            throw new MTException("Invalid format. " +
                    "Use: addIncome <description> $/<amount> [d/<date>]");
        }

        String description = parts[0].trim();
        String remainder = parts[1].trim();
        String date = "no date";
        double amount;

        if (remainder.contains("d/")) {
            String[] amountAndDate = remainder.split("d/", 2);
            amount = Double.parseDouble(amountAndDate[0].trim());
            date = amountAndDate[1].trim();
        } else {
            amount = Double.parseDouble(remainder.trim());
        }

        return new IncomeData(description, amount, date);
    }
    //@@author

    //@@author EdwinTun98, limleyhooi
    // Helper data classes for parsing results
    private record ExpenseData(String description, double amount, String category, String date) {
    }
    //@@author

    //@@author EdwinTun98
    private record EditCommandData(int index, String description, double amount, String category, String date) {
    }
    //@@author

    //@@author limleyhooi
    private record IncomeData(String description, double amount, String date) {
    }
    //@@author limleyhooi
}