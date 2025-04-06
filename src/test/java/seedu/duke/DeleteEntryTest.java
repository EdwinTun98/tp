package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.ui.TextUI;

//@@author rchlai

/**
 * Unit tests for the deleteEntry functionality in MoneyList.
 * This test suite verifies that:
 * - Valid indices result in the correct deletion of entries from the money list.
 * - Invalid indices throw the appropriate exception with clear messages.
 */
class DeleteEntryTest {
    private MoneyList moneyList; // Instance of MoneyList for testing
    private MTLogger logger; // Logger to track events and errors
    private Storage storage; // Storage for persisting data
    private TextUI ui; // Text-based user interface for interacting with MoneyList

    /**
     * Sets up the test environment before each test.
     * Initializes a MoneyList instance with dependencies and simulates loading test data.
     */
    @BeforeEach
    public void setUp() {
        // Initialize dependencies
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        // Create a new MoneyList instance
        moneyList = new MoneyList(logger, storage, ui);

        // Simulate loading entries into moneyList for testing
        moneyList.getMoneyList().add("Entry 1: $100");
        moneyList.getMoneyList().add("Entry 2: $50");
        moneyList.getMoneyList().add("Entry 3: $75");
    }

    /**
     * Test case for deleting an entry with a valid index.
     * Verifies that the entry is correctly removed from the money list.
     *
     * @throws MTException If the deleteEntry method throws an exception.
     */
    @Test
    public void testDeleteEntry_validIndex() throws MTException {
        // Capture the initial size of moneyList
        int initialSize = moneyList.getMoneyList().size();

        // Delete the second entry (index 1)
        moneyList.deleteEntry("delete 2");

        // Verify the size has decreased by 1
        assertEquals(moneyList.getMoneyList().size(), initialSize - 1,
                "The size of moneyList should decrease by 1 after deletion.");

        // Verify the correct entry was deleted
        assertFalse(moneyList.getMoneyList().contains("Entry 2: $50"),
                "Entry 2 should be deleted from moneyList.");
    }

    /**
     * Test case for deleting an entry with an invalid index.
     * Verifies that an MTException is thrown with the correct error message.
     */
    @Test
    public void testDeleteEntry_invalidIndex() {
        // Attempt to delete an entry with an invalid index
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.deleteEntry("delete 5"); // Index 5 does not exist
        });

        // Verify the exception message
        assertEquals("Invalid or unavailable entry number.", thrown.getMessage(),
                "The exception message should indicate an invalid entry number.");
    }
}
