package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.ui.TextUI;

/**
 * Unit tests for the {@link MoneyList#setCategoryLimit(String, String)} method.
 * These tests validate category budget creation, updates, and input validation.
 */
public class SetCategoryLimitTest {

    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    /**
     * Initializes the required objects run.
     */
    @BeforeEach
    public void setUp() {
        logger = new MTLogger("SetCategoryLimitTest");
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);
    }

    // Test case 1: Successfully sets a valid category budget
    @Test
    public void testSetCategoryLimit_validInput_success() throws MTException {
        moneyList.setCategoryLimit("food", "100.00");
        Budget budget = moneyList.getBudgetList().get("food");
        assertNotNull(budget);
        assertEquals(100.00, budget.getAmount(), 0.01);
    }

    // Test case 2: Throws exception for negative amount
    @Test
    public void testSetCategoryLimit_negativeAmount_throwsException() {
        MTException exception = assertThrows(MTException.class, () ->
                moneyList.setCategoryLimit("groceries", "-25.00")
        );
        assertEquals("Budget cannot be negative.", exception.getMessage());
    }

    // Test case 3: Throws exception for non-numeric string input
    @Test
    public void testSetCategoryLimit_nonNumericAmount_throwsException() {
        MTException exception = assertThrows(MTException.class, () ->
                moneyList.setCategoryLimit("shopping", "abc")
        );
        assertEquals("Invalid amount. Please enter a valid number.", exception.getMessage());
    }

    // Test case 4: Throws exception for empty string input
    @Test
    public void testSetCategoryLimit_emptyAmount_throwsException() {
        MTException exception = assertThrows(MTException.class, () ->
                moneyList.setCategoryLimit("bills", "")
        );
        assertEquals("Budget amount cannot be empty.", exception.getMessage());
    }

    // Test case 5: Throws exception when amount string is null
    @Test
    public void testSetCategoryLimit_nullAmount_throwsException() {
        MTException exception = assertThrows(MTException.class, () ->
                moneyList.setCategoryLimit("rent", null)
        );
        assertEquals("Budget amount cannot be empty.", exception.getMessage());
    }

    // Test case 6: Overwrites existing category budget with new value
    @Test
    public void testSetCategoryLimit_overwritesExistingBudget() throws MTException {
        moneyList.setCategoryLimit("utilities", "100");
        moneyList.setCategoryLimit("utilities", "150");
        Budget budget = moneyList.getBudgetList().get("utilities");
        assertEquals(150.00, budget.getAmount(), 0.01);
    }

    // Test case 7: Handles input with leading zeros
    @Test
    public void testSetCategoryLimit_withLeadingZeros_success() throws MTException {
        moneyList.setCategoryLimit("tech", "0000123.45");
        Budget budget = moneyList.getBudgetList().get("tech");
        assertEquals(123.45, budget.getAmount(), 0.01);
    }

    // Test case 8: Throws exception for special symbols in input
    @Test
    public void testSetCategoryLimit_withSpecialSymbols_throwsException() {
        MTException exception = assertThrows(MTException.class, () ->
                moneyList.setCategoryLimit("gadgets", "$%#@")
        );
        assertEquals("Invalid amount. Please enter a valid number.", exception.getMessage());
    }
}
