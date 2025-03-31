// @@author EdwinTun98
package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FindEntryTest {
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

        // Create a new MoneyList instance and populate it with sample data
        moneyList = new MoneyList(logger, storage, ui);
        moneyList.getMoneyList().add("[Expense] Milk $10.00 |Food| (2025-03-28)");
        moneyList.getMoneyList().add("[Expense] Rent $500.00 |Housing| (2025-03-01)");
        moneyList.getMoneyList().add("[Expense] Coffee $5.00 |Food| (2025-03-29)");
    }

    // Test case 1: Valid keyword that matches entries
    @Test
    void testFindEntry_validKeyword() {
        assertDoesNotThrow(() -> moneyList.findEntry("Milk"));
    }

    // Test case 2: Valid keyword but no matches found
    @Test
    void testFindEntry_noMatchesFound() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry("Gym"));
        assertTrue(exception.getMessage().contains("enter a valid keyword to search."));
    }

    // Test case 3: Keyword is case-insensitive
    @Test
    void testFindEntry_caseInsensitiveKeyword() {
        assertDoesNotThrow(() -> moneyList.findEntry("milk"));
    }

    // Test case 4: Keyword matches multiple entries
    @Test
    void testFindEntry_multipleMatches() {
        assertDoesNotThrow(() -> moneyList.findEntry("Food"));
    }

    // Test case 5: Empty keyword input
    @Test
    void testFindEntry_emptyKeyword() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry(""));
        assertTrue(exception.getMessage().contains("Please enter a keyword to search."));
    }

    // Test case 6: Whitespace-only keyword
    @Test
    void testFindEntry_whitespaceKeyword() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry("   "));
        assertTrue(exception.getMessage().contains("Please enter a keyword to search."));
    }

    // Test case 7: Special characters in keyword
    @Test
    void testFindEntry_specialCharacterKeyword() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.findEntry("$$$"));
        assertTrue(exception.getMessage().contains("Please enter a valid keyword to search."));
    }

    // Test case 8: Partial keyword match
    @Test
    void testFindEntry_partialKeyword() {
        assertDoesNotThrow(() -> moneyList.findEntry("Ren"));
    }
}
