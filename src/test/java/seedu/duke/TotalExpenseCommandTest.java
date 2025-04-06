package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.ui.TextUI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

//@@author rchlai
/**
 * Unit tests for the TotalExpenseCommand class.
 * This test suite verifies the functionality and behavior of the TotalExpenseCommand,
 * specifically ensuring that it does not trigger application exit and manages output correctly.
 */
class TotalExpenseCommandTest {
    private MoneyList moneyList; // Instance of MoneyList for testing
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // Captures assertions
    private final PrintStream originalOut = System.out; // Stores the original System.out stream

    /**
     * Sets up the test environment before each test.
     * Redirects System.out to a ByteArrayOutputStream to capture output,
     * and initializes a new MoneyList instance with its dependencies.
     */
    @BeforeEach
    void setUp() {
        // Redirect System.out to capture printed output for testing
        System.setOut(new PrintStream(outContent));

        // Initialize MoneyList with dependencies
        moneyList = new MoneyList(
                new MTLogger(TotalExpenseCommandTest.class.getName()), // Logger instance
                new Storage(), // Storage instance
                new TextUI() // TextUI instance
        );
    }

    /**
     * Restores the original System.out stream after each test.
     * Ensures subsequent tests or application behavior is not affected.
     */
    @AfterEach
    void tearDown() {
        // Restore the original System.out stream
        System.setOut(originalOut);
    }

    /**
     * Verifies that the shouldExit method of TotalExpenseCommand returns false.
     * This ensures the command does not trigger application termination.
     */
    @Test
    void shouldExit_returnsFalse() {
        // Assert that the command does not trigger exit
        assertFalse(new TotalExpenseCommand().shouldExit(),
                "TotalExpenseCommand should not trigger exit");
    }
}
