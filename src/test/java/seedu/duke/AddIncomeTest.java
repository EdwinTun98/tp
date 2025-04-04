package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//@@author limleyhooi
public class AddIncomeTest {
    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);
    }

    @Test
    void testAddIncome_validInputWithDate() {
        try {
            moneyList.addIncome("addIncome Salary $/1000 d/15 march");
            ArrayList<String> list = moneyList.getMoneyList();
            assertTrue(list.contains("Income: Salary $1000.00 [15 march]"),
                    "Income with date should be added.");
        } catch (Exception e) {
            fail("Exception should not occur for valid input with date.");
        }
    }

    @Test
    void testAddIncome_validInputWithoutDate() {
        try {
            moneyList.addIncome("addIncome Bonus $/500");
            ArrayList<String> list = moneyList.getMoneyList();
            // Expected format: "Income: Bonus $500.00 [no date]"
            assertTrue(list.contains("Income: Bonus $500.00 [no date]"),
                    "Income without date should default to 'no date'.");
        } catch (Exception e) {
            fail("Exception should not occur for valid input without date.");
        }
    }

    @Test
    void testAddIncome_nullInput() {
        Exception exception = assertThrows(MTException.class, () -> moneyList.addIncome(null));
        assertEquals("Failed to add income: Input should not be null", exception.getMessage());
    }

    @Test
    void testAddIncome_invalidFormat() {
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addIncome("income Test $/1000"));
        assertEquals("Failed to add income: Invalid format. Use: addIncome <description> $/<amount> [d/<date>]",
                exception.getMessage());
    }

    @Test
    void testAddIncome_invalidAmountFormat() {
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addIncome("addIncome Test $/abc"));
        assertEquals("Invalid amount format. Please ensure it is a numeric value.", exception.getMessage());
    }

    @Test
    void testAddIncome_zeroAmount() {
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addIncome("addIncome Test $/0"));
        assertEquals("Failed to add income: Amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testAddIncome_negativeAmount() {
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addIncome("addIncome Test $/-5"));
        assertEquals("Failed to add income: Amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testAddIncome_missingAmountMarker() {
        Exception exception = assertThrows(MTException.class,
                () -> moneyList.addIncome("addIncome Test 1000"));
        assertEquals("Failed to add income: Invalid format. Use: addIncome <description> $/<amount> [d/<date>]",
                exception.getMessage());
    }

    @Test
    void testAddIncome_onlyAmountProvided() {
        try {
            moneyList.addIncome("addIncome $/750");
            ArrayList<String> list = moneyList.getMoneyList();
            boolean found = list.stream().anyMatch(entry ->
                    entry.contains("$750.00") && entry.contains("[no date]"));
            assertTrue(found, "Income with only amount should be added with an empty description.");
        } catch (Exception e) {
            fail("Exception should not occur when only amount is provided.");
        }
    }
}
//@@author
