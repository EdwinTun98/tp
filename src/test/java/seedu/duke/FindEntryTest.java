// @@author EdwinTun98
package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the findEntry functionality in MoneyList.
 * This test suite ensures:
 * - Keywords are properly matched against entries in the money list.
 * - Case-insensitive matching is supported.
 * - Proper handling of various edge cases (e.g., empty keyword, no matches, special characters).
 * - Partial keyword matches work as expected.
 */
public class FindEntryTest {
    private MoneyList moneyList; // Instance of MoneyList for testing
    private MTLogger logger; // Logger to track events and errors
    private Storage storage; // Storage instance for persisting data
    private TextUI ui; // User interface for interacting with MoneyList

    /**
     * Sets up the test environment before each test.
     * Initializes dependencies and pre-populates the money list with sample data.
     */
    @BeforeEach
    public void setUp() {
        // Initialize dependencies
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        // Create a new MoneyList instance and populate it with sample data
        moneyList = new MoneyList(logger, storage, ui);
        moneyList.getMoneyList().add("[Expense] Milk $10.00 |Food| (2025-03-28)");
        moneyList.getMoneyList().add("[Expense] Rent $500.00 |Housing| (2025-03-01)");
        moneyList.getMoneyList().add("[Expense] Coffee $5.00 |Food| (2025-03-29)");
    }

    /**
     * Test case for finding entries using a valid keyword.
     * Verifies that the method executes without throwing an exception.
     */
    @Test
    void testFindEntry_validKeyword() {
        assertDoesNotThrow(() -> moneyList.findEntry("Milk"));
    }

    /**
     * Test case for finding entries using a valid keyword that has no matches.
     * Verifies that an MTException is thrown with the correct message.
     */
    @Test
    void testFindEntry_noMatchesFound() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry("Gym"));
        assertTrue(exception.getMessage().contains("No matching entries found for keyword:"));
    }

    /**
     * Test case for keyword matching that is case-insensitive.
     * Verifies that entries are matched regardless of keyword case.
     */
    @Test
    void testFindEntry_caseInsensitiveKeyword() {
        assertDoesNotThrow(() -> moneyList.findEntry("milk"));
    }

    /**
     * Test case for keywords that match multiple entries.
     * Verifies that the method executes without exceptions when multiple matches are found.
     */
    @Test
    void testFindEntry_multipleMatches() {
        assertDoesNotThrow(() -> moneyList.findEntry("Food"));
    }

    /**
     * Test case for finding entries using an empty keyword.
     * Verifies that an MTException is thrown with the correct message.
     */
    @Test
    void testFindEntry_emptyKeyword() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry(""));
        assertTrue(exception.getMessage().contains("Please enter a keyword to search."));
    }

    /**
     * Test case for finding entries using a keyword with only whitespace.
     * Verifies that an MTException is thrown with the correct message.
     */
    @Test
    void testFindEntry_whitespaceKeyword() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry("   "));
        assertTrue(exception.getMessage().contains("Please enter a keyword to search."));
    }

    /**
     * Test case for finding entries using a keyword with special characters.
     * Verifies that an MTException is thrown with the correct message.
     */
    @Test
    void testFindEntry_specialCharacterKeyword() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry("$$$"));
        assertTrue(exception.getMessage().contains("No matching entries found for keyword:"));
    }

    /**
     * Test case for finding entries using a partial keyword.
     * Verifies that the method executes successfully when matching partially.
     */
    @Test
    void testFindEntry_partialKeyword() {
        assertDoesNotThrow(() -> moneyList.findEntry("Ren"));
    }
}
