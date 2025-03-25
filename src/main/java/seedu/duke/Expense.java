package seedu.duke;

public class Expense {
    protected String description;
    protected double amount;
    protected String date;
    protected String category;

    // Constructor with Category parameter and date parameter
    public Expense(String description, double amount, String category, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    // Constructor without category and date (optional parameters)
    public Expense(String description, double amount) {
        this(description, amount, "Uncategorized", "no date");
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

    public String getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return String.format("[Expense] %s Value = $%.2f |%s| (%s)",
                this.getDescription(), this.getAmount(), this.getCategory(), this.getDate());
    }
}
