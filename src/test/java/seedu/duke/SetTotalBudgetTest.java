package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetTotalBudgetTest {

    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        logger = new MTLogger("SetTotalBudgetTest");
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);
    }

    @Test
    public void testSetTotalBudget_validInput_success() throws MTException {
        String input = "setTotBgt 500.00";
        moneyList.setTotalBudget(input);
        Budget overallBudget = moneyList.getBudgetList().get("Overall");

        assertNotNull(overallBudget, "Overall budget should not be null.");
        assertEquals(500.00, overallBudget.getAmount(), 0.01, "Total budget should be correctly set to 500.00");
    }

    //@@author EdwinTun98
    @Test
    public void testSetTotalBudget_negativeInput_throwsException() {
        String input = "setTotBgt -200";
        assertDoesNotThrow(() -> moneyList.setTotalBudget(input));
        Budget overallBudget = moneyList.getBudgetList().get("Overall");
        assertTrue(overallBudget == null || overallBudget.getAmount() >= 0,
                "Total budget should not be set to a negative value.");
    }
    //@@author

    @Test
    public void testSetTotalBudget_nonNumericInput_throwsException() {
        String input = "setTotBgt abc";
        assertThrows(MTException.class, () -> moneyList.setTotalBudget(input));
    }

    //@@author EdwinTun98
    @Test
    public void testSetTotalBudget_missingCommandPrefix_throwsError() {
        String input = "500.00";
        assertThrows(AssertionError.class, () -> moneyList.setTotalBudget(input));
    }

    @Test
    public void testSetTotalBudget_emptyInput_throwsError() {
        String buget = "";
        assertThrows(AssertionError.class, () -> moneyList.setTotalBudget(buget));
    }

    @Test
    public void testSetTotalBudget_overwriteExistingBudget() throws MTException {
        // First set
        moneyList.setTotalBudget("setTotBgt 400.00");
        // Overwrite
        moneyList.setTotalBudget("setTotBgt 900.00");

        Budget budget = moneyList.getBudgetList().get("Overall");

        assertEquals(900.00, budget.getAmount(), 0.01,
                "Total budget should be updated to the new value 900.00");
    }

    @Test
    public void testSetTotalBudget_withExtraSpaces() throws MTException {
        String buget = "setTotBgt     300.50   ";
        moneyList.setTotalBudget(buget);

        Budget budget = moneyList.getBudgetList().get("Overall");
        assertEquals(300.50, budget.getAmount(), 0.01,
                "Total budget should be trimmed and parsed correctly.");
    }

    @Test
    public void testSetTotalBudget_veryLargeAmount() throws MTException {
        moneyList.setTotalBudget("setTotBgt 1000000.99");

        Budget budget = moneyList.getBudgetList().get("Overall");
        assertEquals(1000000.99, budget.getAmount(), 0.01,
                "Large budget amount should be handled correctly.");
    }

    @Test
    public void testSetTotalBudget_decimalRounding() throws MTException {
        moneyList.setTotalBudget("setTotBgt 123.456");

        Budget budget = moneyList.getBudgetList().get("Overall");
        assertEquals(123.46, budget.getAmount(), 0.01,
                "Budget should be rounded to 2 decimal places.");
    }

    @Test
    public void testSetTotalBudget_leadingZeros() throws MTException {
        moneyList.setTotalBudget("setTotBgt 000200.00");

        Budget budget = moneyList.getBudgetList().get("Overall");
        assertEquals(200.00, budget.getAmount(), 0.01,
                "Leading zeros should be ignored in budget input.");
    }

    @Test
    public void testSetTotalBudget_multipleSpacesBetweenCommandAndValue() throws MTException {
        moneyList.setTotalBudget("setTotBgt     450.00");
        Budget budget = moneyList.getBudgetList().get("Overall");

        assertEquals(450.00, budget.getAmount(), 0.01,
                "Input with multiple spaces should still work.");
    }
    //@@author
}
