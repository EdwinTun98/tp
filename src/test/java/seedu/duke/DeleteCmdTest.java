package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

class DeleteCmdTest {
    private MoneyListStub moneyListStub;

    @BeforeEach
    void setUp() {
        MTLoggerStub loggerStub = new MTLoggerStub();
        TextUIStub uiStub = new TextUIStub();
        StorageStub storageStub = new StorageStub();
        moneyListStub = new MoneyListStub(loggerStub,
                storageStub, uiStub);
    }

    // Test helper stubs
    static class MoneyListStub extends MoneyList {
        String lastDeleteCommand = "";
        boolean throwException = false;
        boolean shouldValidateIndex = true;

        public MoneyListStub(MTLogger logger, Storage storage,
                             TextUI ui) {
            super(logger, storage, ui);
        }

        @Override
        public void deleteEntry(String command) throws MTException {
            lastDeleteCommand = command;
            if (throwException) {
                throw new MTException("Test exception");
            }
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

    static class TextUIStub extends TextUI {
        public String lastOutput = "";

        @Override
        public void print(String message) {
            lastOutput = message;
        }

        @Override
        public void printNumItems(int count) {
            lastOutput = "Items: " + count;
        }
    }

    static class StorageStub extends Storage {
        @Override
        public void saveExpenses(ArrayList<String> expenses) {}

        @Override
        public ArrayList<String> loadEntries() {
            return null;
        }

        @Override
        public void saveBudgets(HashMap<String, Budget> budgets) {}

        @Override
        public HashMap<String, Budget> loadBudgets() {
            return null;
        }
    }

    @Test
    void execute_validIndex() throws MTException {
        DeleteCommand cmd = new DeleteCommand(3);

        cmd.execute(moneyListStub);

        assertEquals("del 4",
                moneyListStub.lastDeleteCommand);
    }

    @Test
    void execute_zeroIndex() throws MTException {
        DeleteCommand cmd = new DeleteCommand(0); // First item

        cmd.execute(moneyListStub);

        assertEquals("del 1",
                moneyListStub.lastDeleteCommand);
    }

    @Test
    void execute_shouldPropagateIndexException() {
        moneyListStub.throwException = true;
        moneyListStub.shouldValidateIndex = true;
        DeleteCommand cmd = new DeleteCommand(5);

        assertThrows(MTException.class, () -> cmd.execute(moneyListStub));
        assertEquals("del 6",
                moneyListStub.lastDeleteCommand);
    }

    @Test
    void execute_shouldPropagateDeleteException() {
        moneyListStub.throwException = true;
        moneyListStub.shouldValidateIndex = false;
        DeleteCommand cmd = new DeleteCommand(2);

        assertThrows(MTException.class, () -> cmd.execute(moneyListStub));
        assertEquals("del 3",
                moneyListStub.lastDeleteCommand);
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        DeleteCommand cmd = new DeleteCommand(0);
        assertFalse(cmd.shouldExit());
    }

    @Test
    void constructor_negativeIndex() {
        // Should this be allowed? Depends on business logic
        DeleteCommand cmd = new DeleteCommand(-1);
        assertDoesNotThrow(() -> cmd.execute(moneyListStub));
        assertEquals("del 0",
                moneyListStub.lastDeleteCommand);
    }

    @Test
    void execute_veryLargeIndex() {
        DeleteCommand cmd = new DeleteCommand(Integer.MAX_VALUE);
        assertDoesNotThrow(() -> cmd.execute(moneyListStub));
        assertEquals("del " + (Integer.MAX_VALUE + 1),
                moneyListStub.lastDeleteCommand);
    }
}
