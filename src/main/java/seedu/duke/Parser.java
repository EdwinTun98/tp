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

        if (trimmedInput.startsWith("del")) {
            return parseDeleteCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("totalExp")) {
            return new TotalExpenseCommand();
        }

        if (trimmedInput.startsWith("setTotBgt")) {
            return parseBudgetCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("addExp")) {
            return parseAddExpenseCommand(trimmedInput);
        }

        if (trimmedInput.startsWith("edit")) {
            return parseEditExpenseCommand(trimmedInput);
        }

        if (trimmedInput.equalsIgnoreCase("listCat")) {
            return new ListCatsCommand();
        }

        if (trimmedInput.equalsIgnoreCase("help")) {
            return new HelpCommand();
        }

        if (trimmedInput.equalsIgnoreCase("clear")) {
            return new ClearListCommand();
        }

        logger.logWarning("Unknown command: " + trimmedInput);
        throw new MTException("Unknown command. Type 'help' for a " +
                "list of available commands.");
    }

    //@@author EdwinTun98
    private FindCommand parseFindCommand(String input) throws MTException {
        String keyword = input.substring(5).trim();

        if (keyword.isEmpty()) {
            logger.logWarning("Empty keyword for find command.");
            throw new MTException("Please enter a keyword to search.");
        }

        return new FindCommand(keyword);
    }
    //@@author

    //@@author limleyhooi
    private DeleteCommand parseDeleteCommand(String input) throws MTException {
        try {
            int index = Integer.parseInt(input.replaceAll("[^0-9]", "")) - 1;
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            logger.logWarning("Invalid delete command format: " + input);
            throw new MTException("Use: delete <ENTRY_NUMBER>");
        }
    }
    //@@author

    //@@author limleyhooi
    private BudgetCommand parseBudgetCommand(String input) throws MTException {
        try {
            String budgetString = input.substring("setTotBdt".length()).trim();
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
    //@@author

    //@@author EdwinTun98
    private AddExpenseCommand parseAddExpenseCommand(String input) throws MTException {
        try {
            validateInput(input);
            String processedInput = input.trim();

            validateCommandFormat(processedInput);

            String contentAfterCommand = getContentAfterCommand(processedInput);
            String description = extractDescription(contentAfterCommand);

            String afterDescription = getAfterDescription(contentAfterCommand);
            double amount = extractAmount(afterDescription);
            String category = extractCategory(afterDescription);
            String date = extractDate(afterDescription);

            validateAmount(amount);

            return new AddExpenseCommand(description, amount, category, date);
        } catch (NumberFormatException e) {
            logger.logWarning("Invalid amount format: " + input);
            throw new MTException("Invalid amount format. " +
                    "Please ensure it is a numeric value.");
        } catch (IndexOutOfBoundsException e) {
            logger.logWarning("Invalid command format: " + input);
            throw new MTException("Invalid format. Use: addExpense <description> " +
                    "$/<amount> [c/<category>] [d/<date>]");
        }
    }
    //@@author

    //@@author EdwinTun98
    private void validateInput(String input) throws MTException {
        if (input == null) {
            throw new MTException("Input should not be null");
        }
    }
    //@@author

    //@@author EdwinTun98
    private void validateCommandFormat(String input) throws MTException {
        if (!input.startsWith("addExp") || !input.contains("$/")) {
            throw new MTException("Invalid format. Use: addExp <description> " +
                    "$/<amount> [c/<category>] [d/<date>]");
        }
    }
    //@@author

    //@@author EdwinTun98
    private String getContentAfterCommand(String input) {
        return input.substring("addExp".length()).trim();
    }
    //@@author

    //@@author EdwinTun98
    private String extractDescription(String contentAfterCommand) {
        int dollarSlashIndex = contentAfterCommand.indexOf("$/");

        if (dollarSlashIndex == 0) {
            return "No description";
        } else if (dollarSlashIndex > 0) {
            return contentAfterCommand.substring(0, dollarSlashIndex).trim();
        }

        return "No description";
    }
    //@@author

    //@@author EdwinTun98
    private String getAfterDescription(String contentAfterCommand) {
        int dollarSlashIndex = contentAfterCommand.indexOf("$/");

        return contentAfterCommand.substring(dollarSlashIndex + 2).trim();
    }
    //@@author

    //@@author EdwinTun98
    private double extractAmount(String afterDescription) throws NumberFormatException {
        int categoryIndex = afterDescription.indexOf("c/");
        int dateIndex = afterDescription.indexOf("d/");

        String amountString = determineAmountString(afterDescription, categoryIndex, dateIndex);

        return Double.parseDouble(amountString);
    }
    //@@author

    //@@author EdwinTun98
    private String determineAmountString(String input, int categoryIndex, int dateIndex) {
        if (categoryIndex != -1 && dateIndex != -1) {
            return input.substring(0, Math.min(categoryIndex, dateIndex)).trim();
        } else if (categoryIndex != -1) {
            return input.substring(0, categoryIndex).trim();
        } else if (dateIndex != -1) {
            return input.substring(0, dateIndex).trim();
        }

        return input.trim();
    }
    //@@author

    //@@author EdwinTun98
    private String extractCategory(String afterDescription) {
        int categoryIndex = afterDescription.indexOf("c/");

        if (categoryIndex == -1) {
            return "Uncategorized";
        }

        int dateIndex = afterDescription.indexOf("d/");

        if (dateIndex == -1 || categoryIndex < dateIndex) {
            return afterDescription.substring(categoryIndex + 2,
                    dateIndex != -1 ? dateIndex : afterDescription.length()).trim();
        }

        return afterDescription.substring(categoryIndex + 2).trim();
    }
    //@@author

    //@@author EdwinTun98
    private String extractDate(String afterDescription) {
        int dateIndex = afterDescription.indexOf("d/");

        if (dateIndex == -1) {
            return "no date";
        }

        int categoryIndex = afterDescription.indexOf("c/");

        if (categoryIndex == -1 || dateIndex < categoryIndex) {
            return afterDescription.substring(dateIndex + 2,
                    categoryIndex != -1 ? categoryIndex : afterDescription.length()).trim();
        }

        return afterDescription.substring(dateIndex + 2).trim();
    }
    //@@author

    //@@author EdwinTun98
    private void validateAmount(double amount) throws MTException {
        if (amount <= 0) {
            throw new MTException("Amount must be greater than zero.");
        }
    }
    //@@author

    //@@author EdwinTun98
    private EditExpenseCommand parseEditExpenseCommand(String input) throws MTException {
        try {
            String afterEdit = input.substring("edit".length()).trim();
            int firstSpaceIndex = afterEdit.indexOf(' ');

            if (firstSpaceIndex == -1) {
                throw new MTException("Invalid format. Use: edit <entry_number> [options]");
            }

            int index = Integer.parseInt(afterEdit.substring(0, firstSpaceIndex)) - 1;
            String restOfString = afterEdit.substring(firstSpaceIndex + 1).trim();

            String desc = null;
            double amount = 0.00;
            String cat = null;
            String date = null;

            int dollarSlash = restOfString.indexOf("$/");

            String beforeDollar;
            String afterDollar;

            if (dollarSlash == -1) {
                beforeDollar = restOfString;
                afterDollar = "";
            } else {
                beforeDollar = restOfString.substring(0, dollarSlash).trim();
                afterDollar = restOfString.substring(dollarSlash + 2).trim();
            }

            if (!beforeDollar.isEmpty()) {
                desc = beforeDollar;
            }

            if (!afterDollar.isEmpty()) {
                int cIndex = afterDollar.indexOf("c/");
                int dIndex = afterDollar.indexOf("d/");

                double parsedAmount;
                if (cIndex != -1 && dIndex != -1) {
                    if (cIndex < dIndex) {
                        parsedAmount = Double.parseDouble(afterDollar.substring(0, cIndex).trim());
                        cat = afterDollar.substring(cIndex + 2, dIndex).trim();
                        date = afterDollar.substring(dIndex + 2).trim();
                    } else {
                        parsedAmount = Double.parseDouble(afterDollar.substring(0, dIndex).trim());
                        date = afterDollar.substring(dIndex + 2, cIndex).trim();
                        cat = afterDollar.substring(cIndex + 2).trim();
                    }
                    amount = parsedAmount;
                } else if (cIndex != -1) {
                    parsedAmount = Double.parseDouble(afterDollar.substring(0, cIndex).trim());
                    cat = afterDollar.substring(cIndex + 2).trim();
                    amount = parsedAmount;
                } else if (dIndex != -1) {
                    parsedAmount = Double.parseDouble(afterDollar.substring(0, dIndex).trim());
                    date = afterDollar.substring(dIndex + 2).trim();
                    amount = parsedAmount;
                } else {
                    parsedAmount = Double.parseDouble(afterDollar.trim());
                    amount = parsedAmount;
                }

                if (cat != null && cat.isEmpty()) {
                    cat = null;
                }

                if (date != null && date.isEmpty()) {
                    date = null;
                }
            }

            if (dollarSlash == -1) {
                int cIndex = beforeDollar.indexOf("c/");
                int dIndex = beforeDollar.indexOf("d/");

                if (cIndex != -1 || dIndex != -1) {
                    desc = null;

                    if (cIndex != -1 && (dIndex == -1 || cIndex < dIndex)) {
                        String possibleAmountPart = beforeDollar.substring(0, cIndex).trim();
                        if (!possibleAmountPart.isEmpty()) {
                            desc = possibleAmountPart;
                        }

                        int catEnd = (dIndex != -1) ? dIndex : beforeDollar.length();
                        cat = beforeDollar.substring(cIndex + 2, catEnd).trim();

                        if (dIndex != -1) {
                            date = beforeDollar.substring(dIndex + 2).trim();
                        }
                    } else if (dIndex != -1 && (cIndex == -1 || dIndex < cIndex)) {
                        String possibleAmountPart = beforeDollar.substring(0, dIndex).trim();
                        if (!possibleAmountPart.isEmpty()) {
                            desc = possibleAmountPart;
                        }
                        int dateEnd = (cIndex != -1) ? cIndex : beforeDollar.length();
                        date = beforeDollar.substring(dIndex + 2, dateEnd).trim();
                        if (cIndex != -1) {
                            cat = beforeDollar.substring(cIndex + 2).trim();
                        }
                    }
                }
            }

            return new EditExpenseCommand(index, desc, amount, cat, date);

        } catch (NumberFormatException error) {
            throw new MTException("Invalid number format in edit command: "
                    + error.getMessage());
        } catch (MTException error) {
            throw error;
        } catch (Exception error) {
            throw new MTException("Invalid edit format: "
                    + error.getMessage());
        }
    }
    //@@author
}
