package seedu.duke;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetTotalBudgetTest {
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

        // Simulate loading entries into moneyList for testing
        moneyList.getMoneyList().add("Entry 1: $100");
        moneyList.getMoneyList().add("Entry 2: $50");
        moneyList.getMoneyList().add("Entry 3: $75");
    }

    /*
    @Test
    public void testSetTotalBudget_valid() throws MTException {
        // Simulate valid input for setting the total budget
        String input = "setTotBgt 500.00";

        // Invoke the setTotalBudget method
        moneyList.setTotalBudget(input);

        // Assert that the totalBudget value was updated correctly
        assertEquals(500.00, moneyList.getTotalBudget(), 0.01,
                "Total budget should be correctly updated to 500.00");

    }*/

    @Test
    public void testParseDoubleWithInvalidInput() {
        assertThrows(NumberFormatException.class, () -> {
            Double.parseDouble("abc");
        });
    }

    @Test
    public void testSetTotalBudget_invalidFormat() {
        // Simulate invalid input with a non-numeric budget
        String input = "setTotBgt abc";

        // Assert that a NumberFormatException is thrown
        assertThrows(MTException.class, () -> {
            moneyList.setTotalBudget(input);
        });
    }

    @Test
    public void testSetTotalBudget_negativeBudget() throws MTException {
        // Simulate invalid input for setting a negative budget
        String input = "setTotBgt -100.00";

        // Invoke the setTotalBudget method
        moneyList.setTotalBudget(input);

        // Assert that the total budget was not updated
        assertFalse(moneyList.getTotalBudget() < 0,
                "Total budget should not be set to a negative value.");
    }

    @Test
    public void testSetTotalBudget_emptyInput() {
        // Simulate invalid input with an empty string
        String input = "";

        // Assert that the method handles empty input without crashing
        assertThrows(AssertionError.class, () -> {
            moneyList.setTotalBudget(input);
        });
    }

    @Test
    public void testSetTotalBudget_missingCommandPrefix() {
        // Simulate invalid input missing the command prefix
        String input = "500.00";

        // Assert that an AssertionError is thrown due to incorrect format
        assertThrows(AssertionError.class, () -> {
            moneyList.setTotalBudget(input);
        });
    }
}
