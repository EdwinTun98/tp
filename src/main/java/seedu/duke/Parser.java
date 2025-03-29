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
        if (trimmedInput.equalsIgnoreCase("list")) {
            return new ListCommand();
        }

        if (trimmedInput.equalsIgnoreCase("exit")) {
            return new ExitCommand();
        }

        if (trimmedInput.startsWith("find")) {
            return parseFindCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("delete")) {
            return parseDeleteCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("totalExpense")) {
            return new TotalExpenseCommand();
        }

        if (trimmedInput.startsWith("setTotalBudget")) {
            return parseBudgetCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("addExpense")) {
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

            if (!processedInput.startsWith("addExpense") || !processedInput.contains("$/")) {
                throw new MTException("Invalid format. Use: addExpense <description> " +
                        "$/<amount> [c/<category>] [d/<date>]");
            }

            String contentAfterCommand = processedInput.substring("addExpense".length()).trim();

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
    /*
    private AddExpenseCommand parseAddExpenseCommand(String input) throws MTException {
        try {
            // Check that the input is not null.
            if (input == null) {
                throw new MTException("Input should not be null");
            }
            // Remove trailing and leading spaces (but keep inner spaces).
            String processedInput = input.trim();

            // Default parameters.
            String description = "";
            double amount = 0.00;
            String category = "Uncategorized";
            String date = "no date";  // Default date if not provided.

            // Check for proper format with "addExpense" and "$/".
            if (!processedInput.startsWith("addExpense") || !processedInput.contains("$/")) {
                throw new MTException("Invalid format. Use: addExpense <description> " +
                        "$/<amount> [c/<category>] [d/<date>]");
            }

            // Extract the content after "addExpense".
            String contentAfterCommand = processedInput.substring("addExpense".length()).trim();

            // Split by "$/" to separate description from the rest.
            int dollarSlashIndex = contentAfterCommand.indexOf("$/");
            if (dollarSlashIndex <= 0) {
                throw new MTException("Invalid format. Missing or misplaced $/ separator.");
            }
            description = contentAfterCommand.substring(0, dollarSlashIndex).trim();
            String afterDescription = contentAfterCommand.substring(dollarSlashIndex + 2).trim();

            // Now, check if there is a category or date marker.
            int categoryIndex = afterDescription.indexOf("c/");
            int dateIndex = afterDescription.indexOf("d/");

            String amountString = "";
            // Case 1: Both category and date are provided.
            if (categoryIndex != -1 && dateIndex != -1) {
                if (categoryIndex < dateIndex) {
                    // Amount is before the category marker.
                    amountString = afterDescription.substring(0, categoryIndex).trim();
                    // Category is between "c/" and "d/".
                    category = afterDescription.substring(categoryIndex + 2, dateIndex).trim();
                    // Date is after "d/".
                    date = afterDescription.substring(dateIndex + 2).trim();
                } else {
                    // If date appears before category (less common), adjust accordingly.
                    amountString = afterDescription.substring(0, dateIndex).trim();
                    date = afterDescription.substring(dateIndex + 2, categoryIndex).trim();
                    category = afterDescription.substring(categoryIndex + 2).trim();
                }
            } else if (categoryIndex != -1) {
                // Only category is provided.
                amountString = afterDescription.substring(0, categoryIndex).trim();
                category = afterDescription.substring(categoryIndex + 2).trim();
            } else if (dateIndex != -1) {
                // Only date is provided.
                amountString = afterDescription.substring(0, dateIndex).trim();
                date = afterDescription.substring(dateIndex + 2).trim();
            } else {
                // Neither category nor date is provided; the entire string is the amount.
                amountString = afterDescription.trim();
            }

            // Parse the amount.
            amount = Double.parseDouble(amountString);
            if (amount <= 0) {
                throw new MTException("Amount must be greater than zero.");
            }

            // Create and return the AddExpenseCommand with the parsed parameters.
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
     */
}
