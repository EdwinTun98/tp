package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Unit tests for the ListCats functionality in MoneyList.
 * This test suite verifies the behavior of both public and private methods.
 */
public class ListCatsTest {
    private MoneyList moneyList; // Instance of MoneyList for testing
    private MTLogger logger; // Logger used by MoneyList
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
     * Test case for the listCats method when the money list is empty.
     * Verifies that the appropriate message is displayed to the user.
     */
    @Test
    public void testListCats_emptyList () throws MTException {
        // Ensure the money list is empty
        moneyList.clearEntries();

        // Call listCats() method
        moneyList.listCats();

        // Verify the output
        List<String> messages = ui.getPrintedMessages();
        assertTrue(messages.contains("No entries available to display categories."));
    }

    /**
     * Test case for the listCats method when the money list contains entries.
     * Verifies that the categories are displayed correctly.
     */
    @Test
    public void testListCats_nonEmptyList () {
        // Populate the money list with sample entries
        moneyList.getMoneyList().add("[Expense] Entry 1 $25.25 {Cat 1} (no date)");
        moneyList.getMoneyList().add("[Expense] Entry 2 $50.505 {Cat 2} (28-3-25)");
        moneyList.getMoneyList().add("[Expense] Entry 3 $75.075 {Uncategorized} (no date)");

        // Call listCats() method
        moneyList.listCats();

        // Verify the output
        List<String> messages = ui.getPrintedMessages();
        System.out.println("Printed messages: " + ui.getPrintedMessages());
        assertTrue(messages.contains("Categories (in order of appearance):"),
                "Output should display the list of categories.");
        assertTrue(messages.contains("- Cat 1"), "Output should include 'Cat 1' as a category.");
        assertTrue(messages.contains("- Cat 2"), "Output should include 'Cat 2' as a category.");
        assertTrue(messages.contains("- Uncategorized"), "Output should include 'Uncategorized' as a category.");
    }

    /**
     * Test case for the private method extractCategoryFromEntry using reflection.
     * Verifies that a valid category is extracted correctly.
     */
    @Test
    public void testExtractCategoryFromEntry_validEntry() throws Exception {
        // Sample entry string
        String entry = "[Expense] Entry 1 $25.25 {Cat 1} (no date)";

        // Access the private method extractCategoryFromEntry using reflection
        Method method = MoneyList.class.getDeclaredMethod("extractCategoryFromEntry", String.class);
        method.setAccessible(true);

        // Invoke the method and verify the result
        String category = (String) method.invoke(moneyList, entry);
        assertEquals("Cat 1", category, "Expected category 'Cat 1' to be extracted.");
    }

    /**
     * Test case for the private method extractUniqueCategories using reflection.
     * Verifies that categories are correctly extracted when the money list contains entries.
     */
    @Test
    public void testExtractUniqueCategories_nonEmptyList() throws Exception {
        // Populate the money list with sample entries
        moneyList.getMoneyList().add("[Expense] Entry 1 $25.25 {Cat 1} (no date)");
        moneyList.getMoneyList().add("[Expense] Entry 2 $50.50 {Cat 2} (28-3-25)");
        moneyList.getMoneyList().add("[Expense] Entry 3 $75.07 {Uncategorized} (no date)");

        // Access the private method extractUniqueCategories using reflection
        Method method = MoneyList.class.getDeclaredMethod("extractUniqueCategories");
        method.setAccessible(true);

        // Invoke the method and verify the result
        @SuppressWarnings("unchecked")
        LinkedHashSet<String> categories = (LinkedHashSet<String>) method.invoke(moneyList);
        assertTrue(categories.contains("Cat 1"), "Expected 'Cat 1' in extracted categories.");
        assertTrue(categories.contains("Cat 2"), "Expected 'Cat 2' in extracted categories.");
        assertTrue(categories.contains("Uncategorized"), "Expected 'Uncategorized' in extracted categories.");
    }

    /**
     * Test case for the private method extractUniqueCategories using reflection.
     * Verifies that no categories are extracted when the money list is empty.
     */
    @Test
    public void testExtractUniqueCategories_emptyList() throws Exception {
        // Ensure the money list is empty
        moneyList.clearEntries();

        // Access the private method extractUniqueCategories using reflection
        Method method = MoneyList.class.getDeclaredMethod("extractUniqueCategories");
        method.setAccessible(true);

        // Invoke the method and verify the result
        @SuppressWarnings("unchecked")
        LinkedHashSet<String> categories = (LinkedHashSet<String>) method.invoke(moneyList);
        assertTrue(categories.isEmpty(), "Expected an empty set of categories for an empty money list.");
    }
}
