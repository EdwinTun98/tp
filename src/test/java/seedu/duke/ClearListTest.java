package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//@@author rchlai
/**
 * Unit tests for the ClearListCommand class.
 * This test suite verifies the functionality and behavior of ClearListCommand:
 * - Ensures that the command clears entries from the money list.
 * - Verifies proper handling of edge cases (e.g., clearing an empty list).
 * - Validates that the command does not trigger application exit.
 */
class ClearListCommandTest {
    private MoneyList moneyList; // Instance of MoneyList for testing
    private TextUI ui; // Text-based user interface for interacting with MoneyList
    private Storage storage; // Storage object for persisting data
    private final ByteArrayOutputStream outContent
            = new ByteArrayOutputStream(); // Captures printed output for validation
    private final PrintStream originalOut = System.out; // Stores the original System.out stream for restoration

    /**
     * Sets up the test environment before each test.
     * Redirects System.out to a ByteArrayOutputStream to capture printed output,
     * and initializes MoneyList with pre-populated test data.
     */
    @BeforeEach
    void setUp() {
        // Redirect System.out to capture printed output for testing
        System.setOut(new PrintStream(outContent));

        // Initialize dependencies
        ui = new TextUI();
        storage = new Storage();
        moneyList = new MoneyList(new MTLogger(
                ClearListCommandTest.class.getName()), storage, ui);

        // Pre-populate test data
        moneyList.getMoneyList().add("Test expense 1");
        moneyList.getMoneyList().add("Test expense 2");
    }

    /**
     * Restores the original System.out stream after each test.
     * Ensures subsequent tests or application behavior are not affected by output redirection.
     */
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Test case for clearing a non-empty money list.
     * Verifies that the money list is emptied and the appropriate confirmation message is displayed.
     *
     * @throws Exception If the command execution fails.
     */
    @Test
    void execute_clearNonEmptyList_listIsEmpty() throws Exception {
        // Create a ClearListCommand instance and execute the command
        Command command = new ClearListCommand();
        command.execute(moneyList);

        // Verify that the money list is now empty
        assertTrue(moneyList.getMoneyList().isEmpty(),
                "Money list should be empty after clear");

        // Verify that the confirmation message is displayed in the output
        assertTrue(outContent.toString().contains("All entries cleared"),
                "Should display confirmation message");
    }

    /**
     * Test case for clearing an empty money list.
     * Verifies that the command handles an empty list gracefully and displays the appropriate message.
     *
     * @throws Exception If the command execution fails.
     */
    @Test
    void execute_clearEmptyList_showEmptyMessage() throws Exception {
        // Ensure the money list is empty
        moneyList.getMoneyList().clear();

        // Create a ClearListCommand instance and execute the command
        Command command = new ClearListCommand();
        command.execute(moneyList);

        // Verify that the empty list message is displayed in the output
        assertTrue(outContent.toString().contains("No entries to clear"),
                "Should handle empty list gracefully");
    }

    /**
     * Test case for verifying the shouldExit method.
     * Ensures that ClearListCommand does not trigger application exit.
     */
    @Test
    void shouldExit_returnsFalse() {
        // Create a ClearListCommand instance
        Command command = new ClearListCommand();

        // Verify that shouldExit() returns false
        assertFalse(command.shouldExit(),
                "Clear command should not trigger exit");
    }
}
