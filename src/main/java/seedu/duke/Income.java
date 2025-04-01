package seedu.duke;

/**
 * Represents an income with description, amount and date.
 */
//@@author limleyhooi
public class Income {
    protected String description;
    protected double amount;
    protected String date;

    /**
     * Creates an Income with mandatory description and amount.
     * Uses "no date" if date is empty/null.
     *
     * @param description Income description (cannot be empty)
     * @param amount Income amount (must be positive)
     * @param date Income date (optional)
     */
    public Income(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = (date == null || date.isEmpty()) ? "no date" : date;
    }

    public String getDescription() {
        return this.description;
    }

    public double getAmount() {
        return this.amount;
    }


    public String getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return String.format("Income: %s $%.2f [%s]",
                this.getDescription(), this.getAmount(),this.getDate());
    }
}
//@@author

