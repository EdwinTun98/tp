package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;

class EditExpCmdTest {
    private MoneyListSpy moneyListSpy;
    private MTLoggerStub loggerStub;
    private TextUIStub uiStub;
    private StorageStub storageStub;

    @BeforeEach
    void setUp() {
        loggerStub = new MTLoggerStub(EditExpCmdTest.class.getName());
        uiStub = new TextUIStub();
        storageStub = new StorageStub();
        moneyListSpy = new MoneyListSpy(loggerStub, storageStub, uiStub);
    }

    // Test helper spy class that doesn't override MoneyList methods
    static class MoneyListSpy extends MoneyList {
        public int editExpenseCallCount = 0;
        public int lastEditIndex;
        public String lastEditDescription;
        public double lastEditAmount;
        public String lastEditCategory;
        public String lastEditDate;
        public boolean shouldThrow = false;

        public MoneyListSpy(MTLogger logger, Storage storage, TextUI ui) {
            super(logger, storage, ui);
        }

        // Instead of overriding, we'll track calls through a wrapper
        public void trackEditExpense(int index, String newDesc, double newAmount,
                                     String newCat, String newDate) throws MTException {
            editExpenseCallCount++;
            lastEditIndex = index;
            lastEditDescription = newDesc;
            lastEditAmount = newAmount;
            lastEditCategory = newCat;
            lastEditDate = newDate;

            if (shouldThrow) {
                throw new MTException("Test exception");
            }
        }
    }

    static class MTLoggerStub extends MTLogger {
        public String lastLog = "";

        public MTLoggerStub(String className) {
            super(className);
        }

        @Override
        public void logInfo(String message) {
            lastLog = "INFO: " + message;
        }

        @Override
        public void logWarning(String message) {
            lastLog = "WARN: " + message;
        }

        @Override
        public void logSevere(String message, Throwable error) {
            lastLog = "SEVERE: " + message;
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
    void execute_shouldPropagateException() {
        moneyListSpy.shouldThrow = true;
        EditExpenseCommand cmd = new EditExpenseCommand(
                0, "Test", 1.0,
                "Test", "no date");

        assertThrows(MTException.class, () -> cmd.execute(moneyListSpy));
    }
}
