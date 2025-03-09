package seedu.duke;

public class Expense {
    protected String description;
    protected double amount;
    protected String date;

    public Expense(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
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
        return String.format("[Expense] %s Value=$%f Date=%s",
                this.getDescription(), this.getAmount(), this.getDate());
    }
}

