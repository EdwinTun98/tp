package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

class TotalExpenseCommandTest {
    private MoneyList moneyList;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        moneyList = new MoneyList(
                new MTLogger(TotalExpenseCommandTest.class.getName()),
                new Storage(),
                new TextUI()
        );
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldExit_returnsFalse() {
        assertFalse(new TotalExpenseCommand().shouldExit(),
                "TotalExpenseCommand should not trigger exit");
    }
}
