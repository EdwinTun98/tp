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
    void testAddExpense_validInput() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExp Milk $/10 c/Food");
            assertTrue(moneyList.getMoneyList().contains("Expense: Milk $10.00 {Food} [no date]"),
                    "Expense should be added.");
        } catch (Exception e) {
            fail("Exception should not occur for valid input.");
        }
    }

    @Test
    void testAddExpense_missingDollarSign() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense("addExp " +
                "Milk 10 c/Food"));
        assertEquals("Failed to add expense: " +
                        "Invalid format. Use: addExp <description> $/<amount> [c/<category>] [d/<date>]",
                exception.getMessage());
    }

    @Test
    void testAddExpense_invalidAmountFormat() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addExpense("addExp Milk $/abc"));
        assertEquals("Invalid amount format. Please ensure it is a numeric value.", exception.getMessage());
    }

    @Test
    void testAddExpense_noCategoryProvided() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExpMilk$/20");
            assertTrue(moneyList.getMoneyList().contains("Expense: Milk $20.00 {Uncategorized} [no date]"));
        } catch (Exception e) {
            fail("Exception should not occur when category is missing.");
        }
    }

    @Test
    void testAddExpense_nullInput() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense(null));
        assertEquals("Failed to add expense: Input should not be null", exception.getMessage());
    }

    @Test
    void testAddExpense_emptyInput() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense(""));
        assertEquals("Failed to add expense: Invalid format. " +
                        "Use: addExp <description> $/<amount> [c/<category>] [d/<date>]",
                exception.getMessage());
    }

    @Test
    void testAddExpense_negativeAmount() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addExpense("addExp Milk $/-5 c/Food"));
        assertEquals("Failed to add expense: Amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testAddExpense_whitespaceOnlyInput() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class, () -> moneyList.addExpense("       "));
        assertEquals("Failed to add expense: Invalid format. " +
                        "Use: addExp <description> $/<amount> [c/<category>] [d/<date>]",
                exception.getMessage());
    }

    @Test
    void testAddExpense_noDescription() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExp $/50 c/Food d/2025-03-28");
            // Verify both the default behavior and proper parsing:
            assertTrue(moneyList.getMoneyList().stream()
                            .anyMatch(entry -> entry.contains("$50.00")
                                    && entry.contains("Food")
                                    && entry.contains("2025-03-28")),
                    "Should create expense with correct amount, category and date");
        } catch (Exception e) {
            fail("Should accept missing description: " + e.getMessage());
        }
    }

    @Test
    void testAddExpense_zeroAmount() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addExpense("addExp Milk $/0 c/Food"));
        assertEquals("Failed to add expense: Amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testAddExpense_largeAmount() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExp Milk $/999999999 c/Food");
            assertTrue(moneyList.getMoneyList().contains("Expense: Milk $999999999.00 {Food} [no date]"),
                    "Expense with large amount should be added.");
        } catch (Exception e) {
            fail("Exception should not occur for a large amount.");
        }
    }

    @Test
    void testAddExpense_multipleCategories() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addExpense("addExp Milk $/10 c/Food c/Extra d/2025-03-28"));
        assertEquals("Failed to add expense: Invalid format. Multiple category markers detected.",
                exception.getMessage());
    }

    @Test
    void testAddExpense_multipleDates() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addExpense("addExp Milk $/10 c/Food d/2025-03-28 d/2025-03-29"));
        assertEquals("Failed to add expense: Invalid format. Multiple date markers detected.",
                exception.getMessage());
    }


    @Test
    void testAddExpense_specialCharacters() {
        try {
            MoneyList moneyList = new MoneyList(logger, storage, ui);
            moneyList.addExpense("addExp Milky-Way!@# $/50 c/Gr#oc!ery");
            assertTrue(moneyList.getMoneyList().contains("Expense: Milky-Way!@# $50.00 {Gr#oc!ery} [no date]"),
                    "Expense with special characters should be added.");
        } catch (Exception e) {
            fail("Exception should not occur for special characters.");
        }
    }

    @Test
    void testAddExpense_onlyDate() {
        MoneyList moneyList = new MoneyList(logger, storage, ui);
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addExpense("addExp d/2025-03-28"));
        assertEquals("Failed to add expense: Invalid format. " +
                        "Use: addExp <description> $/<amount> [c/<category>] [d/<date>]",
                exception.getMessage());
    }
}
