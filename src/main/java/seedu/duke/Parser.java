package seedu.duke;

/**
 * Handles the parsing of user input and converts it into commands to be executed by MoneyTrail.
 */
public class Parser {
    private final MTLogger logger;

    public Parser() {
        this.logger = new MTLogger(Parser.class.getName());
    }

    public Command parseCommand(String input) throws MTException {
        logger.logInfo("Parsing input: " + input);

        // Handle empty input
        if (input == null || input.trim().isEmpty()) {
            logger.logWarning("Empty input received.");
            throw new MTException("Please enter a valid command.");
        }

        String trimmedInput = input.trim();

        // Handle different commands based on input
        if (trimmedInput.equalsIgnoreCase("listAll")) {
            return new ListCommand();
        }

        if (trimmedInput.equalsIgnoreCase("exit")) {
            return new ExitCommand();
        }

        if (trimmedInput.startsWith("find")) {
            return parseFindCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("del")) {
            return parseDeleteCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("totalExp")) {
            return new TotalExpenseCommand();
        }

        if (trimmedInput.startsWith("setTotalBudget")) {
            return parseBudgetCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("addExp")) {
            return parseAddExpenseCommand(trimmedInput);
        }

        if (trimmedInput.equalsIgnoreCase("listCats")) {
            return new ListCatsCommand();
        }

        if (trimmedInput.equalsIgnoreCase("help")) {
            return new HelpCommand();
        }

        logger.logWarning("Unknown command: " + trimmedInput);
        throw new MTException("Unknown command. Type 'help' for a list of available commands.");
    }

    private FindCommand parseFindCommand(String input) throws MTException {
        String keyword = input.substring(5).trim();

        if (keyword.isEmpty()) {
            logger.logWarning("Empty keyword for find command.");
            throw new MTException("Please enter a keyword to search.");
        }

        return new FindCommand(keyword);
    }

    private DeleteCommand parseDeleteCommand(String input) throws MTException {
        try {
            int index = Integer.parseInt(input.replaceAll("[^0-9]", "")) - 1;
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            logger.logWarning("Invalid delete command format: " + input);
            throw new MTException("Use: delete <ENTRY_NUMBER>");
        }
    }

    private BudgetCommand parseBudgetCommand(String input) throws MTException {
        try {
            String budgetString = input.substring("setTotalBudget".length()).trim();
            double budget = Double.parseDouble(budgetString);

            if (budget < 0) {
                logger.logWarning("Attempted to set a negative budget: " + budget);
                throw new MTException("Budget cannot be negative.");
            }

            return new BudgetCommand(budget);
        } catch (NumberFormatException e) {
            logger.logWarning("Invalid budget format: " + input);
            throw new MTException("Invalid budget format. " +
                    "Please enter a valid number (e.g., setTotalBudget 500.00).");
        }
    }

    private AddExpenseCommand parseAddExpenseCommand(String input) throws MTException {
        try {
            if (input == null) {
                throw new MTException("Input should not be null");
            }

            String processedInput = input.trim();
            String description = "No description";  // Default description
            double amount = 0.00;
            String category = "Uncategorized";
            String date = "no date";

            if (!processedInput.startsWith("addExp") || !processedInput.contains("$/")) {
                throw new MTException("Invalid format. Use: addExp <description> " +
                        "$/<amount> [c/<category>] [d/<date>]");
            }

            String contentAfterCommand = processedInput.substring("addExp".length()).trim();

            // Modified parsing to handle missing description
            int dollarSlashIndex = contentAfterCommand.indexOf("$/");
            if (dollarSlashIndex == 0) {
                // Case where description is missing (immediately after addExpense comes $/)
                description = "No description";
            } else if (dollarSlashIndex > 0) {
                // Case where description exists
                description = contentAfterCommand.substring(0, dollarSlashIndex).trim();
            }

            String afterDescription = contentAfterCommand.substring(dollarSlashIndex + 2).trim();

            // Rest of the parsing logic remains the same
            int categoryIndex = afterDescription.indexOf("c/");
            int dateIndex = afterDescription.indexOf("d/");

            String amountString = "";
            if (categoryIndex != -1 && dateIndex != -1) {
                if (categoryIndex < dateIndex) {
                    amountString = afterDescription.substring(0, categoryIndex).trim();
                    category = afterDescription.substring(categoryIndex + 2, dateIndex).trim();
                    date = afterDescription.substring(dateIndex + 2).trim();
                } else {
                    amountString = afterDescription.substring(0, dateIndex).trim();
                    date = afterDescription.substring(dateIndex + 2, categoryIndex).trim();
                    category = afterDescription.substring(categoryIndex + 2).trim();
                }
            } else if (categoryIndex != -1) {
                amountString = afterDescription.substring(0, categoryIndex).trim();
                category = afterDescription.substring(categoryIndex + 2).trim();
            } else if (dateIndex != -1) {
                amountString = afterDescription.substring(0, dateIndex).trim();
                date = afterDescription.substring(dateIndex + 2).trim();
            } else {
                amountString = afterDescription.trim();
            }

            amount = Double.parseDouble(amountString);
            if (amount <= 0) {
                throw new MTException("Amount must be greater than zero.");
            }

            return new AddExpenseCommand(description, amount, category, date);

        } catch (NumberFormatException e) {
            logger.logWarning("Invalid amount format: " + input);
            throw new MTException("Invalid amount format. Please ensure it is a numeric value.");
        } catch (IndexOutOfBoundsException e) {
            logger.logWarning("Invalid command format: " + input);
            throw new MTException("Invalid format. Use: addExpense <description> " +
                    "$/<amount> [c/<category>] [d/<date>]");
        }
    }
}
