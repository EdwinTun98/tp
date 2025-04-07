package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.ui.TextUI;

/**
 * Unit tests for the {@link MoneyList#editExpense(int, String, Double, String, String)} method.
 * These tests validate editing functionality and edge case handling for expenses.
 */
public class EditExpenseTest {

    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        logger = new MTLogger("EditExpenseTest");
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);

        moneyList.getMoneyList().add("Expense: Coffee $5.00 {food} [2024-04-01]");
    }

    // Test case 1: Editing all fields successfully
    @Test
    public void testEditExpense_validFullUpdate_success() throws MTException {
        moneyList.editExpense(0, "Latte", 6.50, "beverages", "2024-04-02");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Latte"));
        assertTrue(entry.contains("$6.50"));
        assertTrue(entry.contains("{beverages}"));
        assertTrue(entry.contains("[2024-04-02]"));
    }

    // Test case 2: Leaving all fields as defaults retains original values
    @Test
    public void testEditExpense_negativeAmount_throwsException() {
        // Setup: Add an initial valid expense to the list
        moneyList.getMoneyList().add("Expense: Coffee $5.00 {food} [2024-04-01]");

        // Attempt to edit it with a negative amount, expect MTException
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.editExpense(0, "Latte", -10.0, "beverages", "2024-04-02");
        });

        assertEquals("Amount cannot be negative.", thrown.getMessage());
    }

    // Test case 3: Blank description should not override the original one
    @Test
    public void testEditExpense_blankDescription_revertsToOld() throws MTException {
        moneyList.editExpense(0, "", 4.50, "snack", "2024-04-03");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Coffee"));
        assertTrue(entry.contains("$4.50"));
        assertTrue(entry.contains("{snack}"));
    }

    // Test case 4: Zero amount input reverts to the original amount
    @Test
    public void testEditExpense_zeroAmount_revertsToOld() throws MTException {
        moneyList.editExpense(0, "Chips", 0.0, "snack", "2024-04-03");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Chips"));
        assertTrue(entry.contains("$5.00"));
    }

    // Test case 5: Null date input should keep the original date
    @Test
    public void testEditExpense_nullDate_revertsToOld() throws MTException {
        moneyList.editExpense(0, "Coke", 3.00, "drink", null);
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Coke"));
        assertTrue(entry.contains("[2024-04-01]"));
    }

    // Test case 6: Category can accept uppercase letters
    @Test
    public void testEditExpense_uppercaseCategory_stillWorks() throws MTException {
        moneyList.editExpense(0, "Cake", 8.00, "DESSERT", "2024-04-04");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("{DESSERT}"));
    }

    // Test case 7: Invalid index should throw exception
    @Test
    public void testEditExpense_invalidIndex_throwsException() {
        assertThrows(MTException.class, () -> moneyList.editExpense(10, "Burger", 7.50,
                "fastfood", "2024-04-05"));
    }

    // Test case 8: Negative index should throw exception
    @Test
    public void testEditExpense_negativeIndex_throwsException() {
        assertThrows(MTException.class, () -> moneyList.editExpense(-1, "Noodles", 6.00,
                "lunch", "2024-04-06"));
    }

    // Test case 9: Entry with random string format should throw exception
    @Test
    public void testEditExpense_corruptedFormatEntry_throwsException() {
        moneyList.getMoneyList().set(0, "Random Trash");
        assertThrows(MTException.class, () -> moneyList.editExpense(0, "Fix", 20.00,
                "repair", "2024-12-10"));
    }

    // Test case 10: Missing category braces should throw exception
    @Test
    public void testEditExpense_missingCategoryBraces_throwsException() {
        moneyList.getMoneyList().set(0, "Expense: Phone bill $50.00 [2024-04-10]");
        assertThrows(MTException.class, () -> moneyList.editExpense(0, "Phone Plan",
                55.00, "utilities", "2024-04-11"));
    }

    // Test case 11: Missing date brackets should throw exception
    @Test
    public void testEditExpense_missingDateBrackets_throwsException() {
        moneyList.getMoneyList().set(0, "Expense: Internet $40.00 {utilities}");
        assertThrows(MTException.class, () -> moneyList.editExpense(0, "Internet Plan",
                45.00, "utilities", "2024-04-12"));
    }
}
