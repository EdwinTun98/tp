package seedu.duke;

import java.text.ParseException;

/**
 * Represents an expense with description, amount, category, and date.
 */
public class Expense {
    protected String description;
    protected double amount;
    protected String date;
    protected String category;

    //@@author Hansel-K
    /**
     * Creates an Expense with mandatory description and amount.
     * Uses "Uncategorized" if category is empty/null, and "no date"
     * if date is empty/null.
     *
     * @param description Expense description (cannot be empty)
     * @param amount Expense amount (must be positive)
     * @param category Expense category (optional)
     * @param date Expense date (optional)
     */
    public Expense(String description, double amount, String category, String date) {
        this.description = description;
        this.amount = amount;
        this.category = (category == null || category.isEmpty()) ? "Uncategorized" : category;
        this.date = (date == null || date.isEmpty()) ? "no date" : date;
    }
    //@@author

    /**
     * @return Expense description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return Expense amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * @return Expense date (or "no date" if not specified)
     */
    public String getDate() {
        return this.date;
    }

    /**
     * @return Expense category (or "Uncategorized" if not specified)
     */
    public String getCategory() {
        return this.category;
    }

    //@@author Hansel-K
    /**
     * @return Formatted string representation of the expense
     */
    @Override
    public String toString() {
        return String.format("Expense: %s $%.2f {%s} [%s]",
                this.getDescription(), this.getAmount(), this.getCategory(), this.getDate());
    }
    //@@author

    //@@author EdwinTun98
    public static Expense parseString(String expense) throws MTException {
        if (!expense.startsWith("Expense: ")) {
            throw new MTException("Invalid expense format");
        }
        try {
            String stripped = expense.substring("Expense: ".length()).trim();
            int dollarIndex = stripped.indexOf('$');
            int openBrace = stripped.indexOf('{', dollarIndex);
            int closeBrace = stripped.indexOf('}', openBrace);
            int openBracket = stripped.indexOf('[', closeBrace);
            int closeBracket = stripped.indexOf(']', openBracket);

            String desc = stripped.substring(0, dollarIndex).trim();
            double amount = Double.parseDouble(stripped.substring(dollarIndex + 1, openBrace).trim());
            String cat = stripped.substring(openBrace + 1, closeBrace).trim();
            String date = stripped.substring(openBracket + 1, closeBracket).trim();

            return new Expense(desc, amount, cat, date);
        } catch (Exception e) {
            throw new MTException("Invalid expense format");
        }
    }
    //@@author
}
