package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.ui.TextUI;


/**
 * Unit tests for checking budget validation and handling in {@link MoneyList#checkExpenses(String)}.
 * Covers overall and category-specific expense checks, budget validity, and input error handling.
 */
public class CheckExpensesTest {

    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    /**
     * Sets up a new MoneyList with dummy data before each test.
     */
    @BeforeEach
    public void setUp() {
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        moneyList = new MoneyList(logger, storage, ui);

        // Add dummy expenses
        moneyList.getMoneyList().add("Expense: taxi $30.00 {transport} [2024-12-01]");
        moneyList.getMoneyList().add("Expense: movie $15.00 {entertainment} [2024-12-02]");
    }

    // Test case 1: Check for successful processing of "Overall" budget
    @Test
    public void testCheckOverallBudget_success() throws MTException {
        moneyList.setTotalBudget("setTotBgt 100.00");

        assertDoesNotThrow(() -> moneyList.checkExpenses("Overall"));
    }

    // Test case 2: Category budget check should succeed if set
    @Test
    public void testCheckCategoryBudget_success() throws MTException {
        // Set category budget
        moneyList.setCategoryLimit("entertainment", 50.00);

        // Should work fine for existing category
        assertDoesNotThrow(() -> moneyList.checkExpenses("entertainment"));
    }

    // Test case 3: Throws exception if budget for category does not exist
    @Test
    public void testCheckExpenses_missingBudget_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses("groceries");
        });

        assertEquals("No category budget set.", ex.getMessage());
    }

    // Test case 4: Throws exception for empty string input
    @Test
    public void testCheckExpenses_emptyInput_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses("");
        });

        assertEquals("Please specify a category or use 'Overall'.", ex.getMessage());
    }

    // Test case 5: Throws exception for null input
    @Test
    public void testCheckExpenses_nullInput_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses(null);
        });

        assertEquals("Please specify a category or use 'Overall'.", ex.getMessage());
    }

    // Test case 6: Throws exception if no overall budget was set
    @Test
    public void testCheckOverallBudget_missing_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses("Overall");
        });

        assertEquals("No Overall budget set.", ex.getMessage());
    }

    // Test case 7: A 0.00 overall budget is still valid and accepted
    @Test
    public void testCheckExpenses_zeroBudget_stillValid() throws MTException {
        moneyList.setTotalBudget("setTotBgt 0.00");

        assertDoesNotThrow(() -> moneyList.checkExpenses("Overall"));
    }

    // Test case 8: Large budgets should be handled correctly
    @Test
    public void testCheckExpenses_largeBudget() throws MTException {
        moneyList.setTotalBudget("setTotBgt 1000000.00");
        assertDoesNotThrow(() -> moneyList.checkExpenses("Overall"));
    }

    // Test case 9: Input with special symbols should throw parsing exception
    @Test
    public void testSetCategoryLimit_withSymbols_throwsException() {
        MTException exception = assertThrows(MTException.class, () -> {
            moneyList.setCategoryLimit("groceries", "$!@#");
        });

        assertEquals("Invalid amount. Please enter a valid number.", exception.getMessage());
    }

    // Test case 10: Rejects large negative numbers for budget
    @Test
    public void testSetCategoryLimit_negativeLargeValue() {
        Exception exception = assertThrows(MTException.class, () -> {
            moneyList.setCategoryLimit("groceries", -999999.99);
        });

        assertEquals("Category budget cannot be negative.", exception.getMessage());
    }

}
