// @@author EdwinTun98
package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListSummaryTest {

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

    // Test case 1: List is empty
    @Test
    void testListSummary_emptyList() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.listSummary());
        assertThrows(MTException.class, () -> moneyList.listSummary());
    }

    // Test case 2L list has entries
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
