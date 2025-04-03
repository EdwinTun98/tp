package seedu.duke;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EditExpenseTest {

    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        logger = new MTLogger("EditExpenseTest");
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);

        // Add well-formed expense entry
        moneyList.getMoneyList().add("Expense: Coffee $5.00 {food} [2024-04-01]");
    }

    @Test
    public void testEditExpense_validFullUpdate_success() throws MTException {
        moneyList.editExpense(0, "Latte", 6.50, "beverages", "2024-04-02");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Latte"));
        assertTrue(entry.contains("$6.50"));
        assertTrue(entry.contains("{beverages}"));
        assertTrue(entry.contains("[2024-04-02]"));
    }

    @Test
    public void testEditExpense_partialUpdate_preservesOldValues() throws MTException {
        moneyList.editExpense(0, null, -1.0, "", "");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Coffee"));
        assertTrue(entry.contains("$5.00"));
        assertTrue(entry.contains("{food}"));
        assertTrue(entry.contains("[2024-04-01]"));
    }

    @Test
    public void testEditExpense_blankDescription_revertsToOld() throws MTException {
        moneyList.editExpense(0, "", 4.50, "snack", "2024-04-03");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Coffee"));
        assertTrue(entry.contains("$4.50"));
        assertTrue(entry.contains("{snack}"));
    }

    @Test
    public void testEditExpense_zeroAmount_revertsToOld() throws MTException {
        moneyList.editExpense(0, "Chips", 0.0, "snack", "2024-04-03");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Chips"));
        assertTrue(entry.contains("$5.00"));
    }

    @Test
    public void testEditExpense_nullDate_revertsToOld() throws MTException {
        moneyList.editExpense(0, "Coke", 3.00, "drink", null);
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("Coke"));
        assertTrue(entry.contains("[2024-04-01]")); // Old date
    }

    @Test
    public void testEditExpense_uppercaseCategory_stillWorks() throws MTException {
        moneyList.editExpense(0, "Cake", 8.00, "DESSERT", "2024-04-04");
        String entry = moneyList.getMoneyList().get(0);
        assertTrue(entry.contains("{DESSERT}"));
    }

    @Test
    public void testEditExpense_invalidIndex_throwsException() {
        assertThrows(MTException.class, () -> moneyList.editExpense(10, "Burger", 7.50, "fastfood", "2024-04-05"));
    }

    @Test
    public void testEditExpense_negativeIndex_throwsException() {
        assertThrows(MTException.class, () -> moneyList.editExpense(-1, "Noodles", 6.00, "lunch", "2024-04-06"));
    }

    @Test
    public void testEditExpense_corruptedFormatEntry_throwsException() {
        moneyList.getMoneyList().set(0, "Random Trash");
        assertThrows(MTException.class, () -> moneyList.editExpense(0, "Fix", 20.00, "repair", "2024-12-10"));
    }

    @Test
    public void testEditExpense_missingCategoryBraces_throwsException() {
        moneyList.getMoneyList().set(0, "Expense: Phone bill $50.00 [2024-04-10]");
        assertThrows(MTException.class, () -> moneyList.editExpense(0, "Phone Plan", 55.00, "utilities", "2024-04-11"));
    }

    @Test
    public void testEditExpense_missingDateBrackets_throwsException() {
        moneyList.getMoneyList().set(0, "Expense: Internet $40.00 {utilities}");
        assertThrows(MTException.class, () -> moneyList.editExpense(0, "Internet Plan", 45.00, "utilities", "2024-04-12"));
    }
}
