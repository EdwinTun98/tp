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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;

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

    // Test case 1: Listing when multiple category budgets are set
    @Test
    public void testListBudgets_withBudgets() throws MTException {
        moneyList.setCategoryLimit("food", 100.00);
        moneyList.setCategoryLimit("transport", 50.00);

        assertDoesNotThrow(() -> moneyList.listBudgets());
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
        assertEquals("Category budget cannot be negative.", thrown.getMessage());
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

    // Test case 7: Replacing existing category budget should update amount
    @Test
    public void testSetCategoryLimit_replaceExistingBudget() throws MTException {
        moneyList.setCategoryLimit("food", 100.00);
        moneyList.setCategoryLimit("food", 200.00);

        HashMap<String, Budget> budgets = moneyList.getBudgetList();
        Budget foodBudget = budgets.get("food");
        assertNotNull(foodBudget);
        assertEquals(200.00, foodBudget.getAmount(), 0.01);
    }


    // Test case 8: Category name with symbols should still be stored properly
    @Test
    public void testSetCategoryLimit_categoryWithSymbols() throws MTException {
        // Given
        String category = "@food#678";
        double amount = 100.00;

        // When
        moneyList.setCategoryLimit(category, amount);

        // Then
        Budget result = moneyList.getBudgetList().get(category);
        assertEquals(category, result.getCategory(), "Category name should be stored as is.");
        assertEquals(amount, result.getAmount(), 0.01, "Amount should be stored correctly.");
    }
}
