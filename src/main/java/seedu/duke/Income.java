package seedu.duke;

//@@author limleyhooi
/**
 * Represents an income with description, amount and date.
 */
public class Income {
    protected String description;
    protected double amount;
    protected String date;

    //@@author limleyhooi
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
    //@@author


    //@@author limleyhooi
    /**
     * @return Income description
     */
    public String getDescription() {
        return this.description;
    }
    //@@author

    //@@author limleyhooi
    /**
     * @return Income amount
     */
    public double getAmount() {
        return this.amount;
    }
    //@@author

    //@@author limleyhooi
    /**
     * @return Income date of entry
     */
    public String getDate() {
        return this.date;
    }
    //@@author

    //@@author limleyhooi
    /**
     * @return Formatted string representation of income
     */
    @Override
    public String toString() {
        return String.format("Income: %s $%.2f [%s]",
                this.getDescription(), this.getAmount(),this.getDate());
    }
    //@@author
}
//@@author
