package seedu.duke;

public class Expense {
    protected String description;
    protected double amount;
    //protected String date;
    protected String category;

    // Constructor with Category parameter
    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        //this.date = date;
        this.category = category;
    }

    // Constructor without category (optional parameter)
    public Expense(String description, double amount) {
        this(description, amount, "Uncategorized"); // Calls the other constructor with default category
    }

    public String getDescription() {
        return this.description;
    }

    public double getAmount() {
        return this.amount;
    }

    /*public String getDate() {
        return this.date;
    }*/

    public String getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return String.format("[Expense] %s Value=$%.2f  Category=%s", //rmb to add back in date
                this.getDescription(), this.getAmount(), this.getCategory());
    }
}
