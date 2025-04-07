package seedu.duke.entries;

import seedu.duke.exception.MTException;

/**
 * Represents a budget, either total or category-specific.
 */
public class Budget {
    public static final String OVERALL = "Overall";

    private final String category;
    private double amount;

    /**
     * Constructs a new Budget object.
     *
     * @param category The category for this budget (use "Total" for total budget)
     * @param amount   The budget amount (must be ≥ 0)
     * @throws MTException If the amount is negative
     */
    public Budget(String category, double amount) throws MTException {
        if (amount < 0) {
            throw new MTException("Budget amount cannot be negative.");
        }
        this.category = (category == null || category.trim().isEmpty())
                ? "Uncategorized" : category.trim();
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) throws MTException {
        if (amount < 0) {
            throw new MTException("Category amount cannot be negative.");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        if (category.equalsIgnoreCase("Overall")) {
            return String.format("Overall Budget: $%.2f", amount);
        }
        return String.format("Budget for %s: $%.2f", category, amount);
    }
}
