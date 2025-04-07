package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.entries.Budget;
import seedu.duke.exception.MTException;
import seedu.duke.logger.MTLogger;
import seedu.duke.moneylist.MoneyList;
import seedu.duke.storage.Storage;
import seedu.duke.ui.TextUI;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    // Test case 8: Throws exception for special symbols in input
    @Test
    public void testSetCategoryLimit_withSpecialSymbols_throwsException() {
        MTException exception = assertThrows(MTException.class, () ->
                moneyList.setCategoryLimit("gadgets", "$%#@")
        );
        assertEquals("Invalid amount. Please enter a valid number.", exception.getMessage());
    }
}
