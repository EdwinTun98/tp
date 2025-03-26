package seedu.duke;

public class Expense {
    protected String description;
    protected double amount;
    protected String date;
    protected String category;

    // Constructor with optional parameters handling
    public Expense(String description, double amount, String category, String date) {
        this.description = description;
        this.amount = amount;
        this.category = (category == null || category.isEmpty()) ? "Uncategorized" : category;
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

    public String getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return String.format("[Expense] %s Value = $%.2f |%s| (%s)",
                this.getDescription(), this.getAmount(), this.getCategory(), this.getDate());
    }
}
