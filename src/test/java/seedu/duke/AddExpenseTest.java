package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddExpenseTest {
    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        // Initialize dependencies
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        // Create a new MoneyList instance
        moneyList = new MoneyList(logger, storage, ui);
    }

    @Test
    void testAddExpenseValidInput() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExpense Milk $/10 c/Food");
            assertTrue(moneyList.getMoneyList().contains("[Expense] Milk Value=$10.00 (Food)"),
                    "Expense should be added.");
        } catch (Exception e) {
            fail("Exception should not occur for valid input.");
        }
    }

    @Test
    void testAddExpenseMissingDollarSign() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense("addExpense " +
                "Milk 10 c/Food"));
        assertEquals("Failed to add expense: " +
                        "Invalid format. Use: addExpense <description> $/<amount> c/<category>",
                exception.getMessage());
    }

    @Test
    void testAddExpenseInvalidAmountFormat() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense("addExpense Milk $/abc"));
        assertEquals("Invalid amount format. Please ensure it is a numeric value.", exception.getMessage());
    }

    @Test
    void testAddExpenseNoCategoryProvided() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExpenseMilk$/20");
            assertTrue(moneyList.getMoneyList().contains("[Expense] Milk Value=$20.00 (Uncategorised)"));
        } catch (Exception e) {
            fail("Exception should not occur when category is missing.");
        }
    }

    @Test
    void testAddExpenseNullInput() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense(null));
        assertEquals("Failed to add expense: Input should not be null", exception.getMessage());
    }
}
