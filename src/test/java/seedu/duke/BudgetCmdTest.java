package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class BudgetCommandTest {
    private MoneyListStub moneyListStub;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub();
    }

    @AfterEach
    void tearDown() {
        moneyListStub.clear();
    }

    @Test
    void execute_positiveBudget_setsCorrectly() throws MTException {
        BudgetCommand command = new BudgetCommand(1000.50);
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCommandsReceived().size());

        assertEquals("setTotBgt 1000.5",
                moneyListStub.getCommandsReceived().get(0));
    }

    @Test
    void execute_zeroBudget_setsCorrectly() throws MTException {
        BudgetCommand command = new BudgetCommand(0.00);
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCommandsReceived().size());

        assertEquals("setTotBgt 0.0",
                moneyListStub.getCommandsReceived().get(0));
    }

    @Test
    void execute_negativeBudget_throwsException() {
        BudgetCommand command = new BudgetCommand(-100.00);
        assertThrows(MTException.class,
                () -> command.execute(moneyListStub));
    }

    @Test
    void execute_largeBudget_setsCorrectly() throws MTException {
        BudgetCommand command = new BudgetCommand(999999.99);
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCommandsReceived().size());

        assertEquals("setTotBgt 999999.99",
                moneyListStub.getCommandsReceived().get(0));
    }

    @Test
    void execute_preciseDecimalBudget_setsCorrectly() throws MTException {
        BudgetCommand command = new BudgetCommand(123.456789);
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCommandsReceived().size());

        assertEquals("setTotBgt 123.456789",
                moneyListStub.getCommandsReceived().get(0));
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        BudgetCommand command = new BudgetCommand(100.00);

        assertFalse(command.shouldExit());
    }

    // Stub class for testing isolation
    private static class MoneyListStub extends MoneyList {
        private final ArrayList<String> commandsReceived =
                new ArrayList<>();

        public MoneyListStub() {
            super(new MTLoggerStub(), new StorageStub(),
                    new TextUIStub());
        }

        @Override
        public void setTotalBudget(String input) throws MTException {
            commandsReceived.add(input);

            // Simulate validation that would happen in real MoneyList
            if (input.contains("-")) {
                throw new MTException("Budget cannot be negative.");
            }
        }

        public List<String> getCommandsReceived() {
            return commandsReceived;
        }

        public void clear() {
            commandsReceived.clear();
        }
    }

    private static class MTLoggerStub extends MTLogger {
        public MTLoggerStub() {
            super("TestLogger");
        }

        @Override
        public void logInfo(String message) {
            // Do nothing for tests
        }

        @Override
        public void logWarning(String message) {
            // Do nothing for tests
        }

        @Override
        public void logSevere(String message, Throwable error) {
            // Do nothing for tests
        }
    }

    private static class StorageStub extends Storage {
        public StorageStub() {
            super();
        }

        @Override
        public void saveBudgets(HashMap<String, Budget> budgetList) {
            // Do nothing for tests
        }
    }

    private static class TextUIStub extends TextUI {
        // Simple stub that does nothing
    }
}
