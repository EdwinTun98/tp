package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ClearListCommandTest {
    private MoneyList moneyList;
    private TextUI ui;
    private Storage storage;
    private final ByteArrayOutputStream outContent =
            new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        ui = new TextUI();
        storage = new Storage();
        moneyList = new MoneyList(new MTLogger(
                ClearListCommandTest.class.getName()), storage, ui);

        // Pre-populate test data
        moneyList.getMoneyList().add("Test expense 1");
        moneyList.getMoneyList().add("Test expense 2");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_clearNonEmptyList_listIsEmpty() throws Exception {
        Command command = new ClearListCommand();
        command.execute(moneyList);

        assertTrue(moneyList.getMoneyList().isEmpty(),
                "Money list should be empty after clear");
        assertTrue(outContent.toString().contains("All entries cleared"),
                "Should display confirmation message");
    }

    @Test
    void execute_clearEmptyList_showEmptyMessage() throws Exception {
        moneyList.getMoneyList().clear(); // Ensure empty
        Command command = new ClearListCommand();
        command.execute(moneyList);

        assertTrue(outContent.toString().contains("No entries to clear"),
                "Should handle empty list gracefully");
    }

    @Test
    void shouldExit_returnsFalse() {
        Command command = new ClearListCommand();
        assertFalse(command.shouldExit(),
                "Clear command should not trigger exit");
    }
}
