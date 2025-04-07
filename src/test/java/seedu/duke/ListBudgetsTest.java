package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.MTException;
import seedu.duke.logger.MTLogger;
import seedu.duke.moneylist.MoneyList;
import seedu.duke.storage.Storage;
import seedu.duke.ui.TextUI;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * Unit tests for category and overall budget listing and setting functionalities
 * in the {@link MoneyList} class.
 */
public class ListBudgetsTest {
    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    /**
     * Sets up the environment.
     */
    @BeforeEach
    public void setUp() {
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);
    }

    // Test case 2: Listing when only overall budget is set
    @Test
    public void testListBudgets_withOverallBudget() throws MTException {
        moneyList.setTotalBudget("setTotBgt 500.00");
        assertDoesNotThrow(() -> moneyList.listBudgets());
    }

    // Test case 3: Listing when no budgets are set should throw exception
    @Test
    public void testListBudgets_withNoBudgets() {
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.listBudgets();
        });
        assertEquals("No budgets have been set.", thrown.getMessage());
    }

    // Test case 4: Negative category budget should throw exception
    @Test
    public void testSetCategoryLimit_negativeBudget() {
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.setCategoryLimit("food", -159.00);
        });
        assertEquals("Budget cannot be negative.", thrown.getMessage());
    }

    // Test case 5: Invalid total budget input (non-numeric)
    @Test
    public void testSetTotalBudget_invalidInput() {
        assertThrows(MTException.class, () -> moneyList.setTotalBudget("setTotBgt car"));
    }

    // Test case 6: Empty total budget input should throw assertion
    @Test
    public void testSetTotalBudget_emptyInput() {
        assertThrows(AssertionError.class, () -> moneyList.setTotalBudget(""));
    }
}
