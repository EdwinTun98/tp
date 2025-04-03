package seedu.duke;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class CheckExpensesTest {

    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        moneyList = new MoneyList(logger, storage, ui);

        // Add some dummy expenses
        moneyList.getMoneyList().add("Expense: taxi $30.00 {transport} [2024-12-01]");
        moneyList.getMoneyList().add("Expense: movie $15.00 {entertainment} [2024-12-02]");
    }

    @Test
    public void testCheckOverallBudget_success() throws MTException {
        // Set an overall budget
        moneyList.setTotalBudget("setTotBgt 100.00");

        // No exception should be thrown when checking "Overall"
        assertDoesNotThrow(() -> moneyList.checkExpenses("Overall"));
    }

    @Test
    public void testCheckCategoryBudget_success() throws MTException {
        // Set category budget
        moneyList.setCategoryLimit("entertainment", 50.00);

        // Should work fine for existing category
        assertDoesNotThrow(() -> moneyList.checkExpenses("entertainment"));
    }

    @Test
    public void testCheckExpenses_missingBudget_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses("groceries"); // not set
        });

        assertEquals("No category budget set.", ex.getMessage());
    }

    @Test
    public void testCheckExpenses_emptyInput_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses("");
        });

        assertEquals("Please specify a category or use 'Overall'.", ex.getMessage());
    }

    @Test
    public void testCheckExpenses_nullInput_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses(null);
        });

        assertEquals("Please specify a category or use 'Overall'.", ex.getMessage());
    }

    @Test
    public void testCheckOverallBudget_missing_throwsException() {
        MTException ex = assertThrows(MTException.class, () -> {
            moneyList.checkExpenses("Overall"); // No budget set yet
        });

        assertEquals("No Overall budget set.", ex.getMessage());
    }

    @Test
    public void testCheckExpenses_zeroBudget_stillValid() throws MTException {
        // Set a zero-value overall budget
        moneyList.setTotalBudget("setTotBgt 0.00");

        // Should not throw an exception, but remaining = -spent
        assertDoesNotThrow(() -> moneyList.checkExpenses("Overall"));
    }

    @Test
    public void testCheckExpenses_largeBudget() throws MTException {
        moneyList.setTotalBudget("setTotBgt 1000000.00");
        assertDoesNotThrow(() -> moneyList.checkExpenses("Overall"));
    }

    @Test
    public void testSetCategoryLimit_withSymbols_throwsException() {
        MTException exception = assertThrows(MTException.class, () -> {
            moneyList.setCategoryLimit("groceries", "$!@#");
        });

        assertEquals("Invalid amount. Please enter a valid number.", exception.getMessage());
    }

    @Test
    public void testSetCategoryLimit_negativeLargeValue() {
        Exception exception = assertThrows(MTException.class, () -> {
            moneyList.setCategoryLimit("groceries", -999999.99);
        });

        assertEquals("Category budget cannot be negative.", exception.getMessage());
    }

}

