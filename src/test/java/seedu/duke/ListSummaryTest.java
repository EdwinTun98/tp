// @@author EdwinTun98
package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.MTException;
import seedu.duke.logger.MTLogger;
import seedu.duke.moneylist.MoneyList;
import seedu.duke.storage.Storage;
import seedu.duke.ui.TextUI;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the listSummary functionality in MoneyList.
 * This test suite verifies the behavior of the method when the list is empty
 * and when it contains entries. It ensures proper handling of exceptions and
 * correct execution flow.
 */
public class ListSummaryTest {

    private MoneyList moneyList; // Instance of MoneyList for testing
    private MTLogger logger; // Logger used to track events and errors
    private Storage storage; // Storage object used by MoneyList
    private TextUI ui; // User interface object used by MoneyList

    /**
     * Sets up the dependencies and initializes the MoneyList before each test.
     */
    @BeforeEach
    public void setUp() {
        // Initialize dependencies
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        // Create a new MoneyList instance
        moneyList = new MoneyList(logger, storage, ui);
    }

    /**
     * Test case for the listSummary method when the list is empty.
     * Verifies that an exception is thrown indicating that no summary
     * can be generated for an empty list.
     */
    @Test
    void testListSummary_emptyList() {
        // Expect an MTException when attempting to generate a summary from an empty list
        Exception exception = assertThrows(MTException.class, () -> moneyList.listSummary());
        assertThrows(MTException.class, () -> moneyList.listSummary()); // Reconfirm exception handling

    }

    /**
     * Test case for the listSummary method when the list contains entries.
     * Verifies that no exception is thrown and the summary is successfully displayed.
     */
    @Test
    void testListSummary_nonEmptyList() {
        // Add sample expenses to the moneyList
        moneyList.getMoneyList().add("[Expense] Milk $10.00 |Food| (2025-03-28)");
        moneyList.getMoneyList().add("[Expense] Rent $500.00 |Housing| (2025-03-01)");
        moneyList.getMoneyList().add("[Expense] Coffee $5.00 |Food| (2025-03-29)");

        // Ensure no exception is thrown and the summary is displayed
        assertDoesNotThrow(() -> moneyList.listSummary());
    }
}
