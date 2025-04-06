package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

class AddExpenseCommandTest {
    private MoneyListStub moneyListStub;

    @BeforeEach
    void setUp() {
        MTLoggerStub loggerStub = new MTLoggerStub(
                AddExpenseCommandTest.class.getName());
        TextUIStub uiStub = new TextUIStub();
        StorageStub storageStub = new StorageStub();
        moneyListStub = new MoneyListStub(loggerStub, storageStub, uiStub);
    }

    // Test helper stubs
    static class MoneyListStub extends MoneyList {
        String lastAddedExpense = "";
        boolean throwException = false;

        public MoneyListStub(MTLogger logger, Storage storage, TextUI ui) {
            super(logger, storage, ui);
        }

        @Override
        public void addExpense(String command) throws MTException {
            if (throwException) {
                throw new MTException("Test exception");
            }

            lastAddedExpense = command;
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
    void execute_basicExpense() throws MTException {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Lunch", 12.50, "Uncategorized", "no date");

        cmd.execute(moneyListStub);

        assertEquals("addExp Lunch $/12.5",
                moneyListStub.lastAddedExpense);
    }

    @Test
    void execute_expenseWithCategory() throws MTException {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Movie", 25.00, "Entertainment", "no date");

        cmd.execute(moneyListStub);

        assertEquals("addExp Movie $/25.0 " +
                "c/Entertainment", moneyListStub.lastAddedExpense);
    }

    @Test
    void execute_expenseWithDate() throws MTException {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Groceries", 45.30, "Uncategorized", "2023-10-15");

        cmd.execute(moneyListStub);

        assertEquals("addExp Groceries $/45.3 " +
                "d/2023-10-15", moneyListStub.lastAddedExpense);
    }

    @Test
    void execute_expenseWithCategoryAndDate() throws MTException {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Books", 19.99, "Education", "2023-10-16");

        cmd.execute(moneyListStub);

        assertEquals("addExp Books $/19.99 c/Education " +
                "d/2023-10-16", moneyListStub.lastAddedExpense);
    }

    @Test
    void execute_shouldHandleZeroAmount() throws MTException {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Freebie", 0.0, "Uncategorized", "no date");

        cmd.execute(moneyListStub);

        assertEquals("addExp Freebie $/0.0",
                moneyListStub.lastAddedExpense);
    }

    @Test
    void execute_shouldPropagateException() {
        moneyListStub.throwException = true;
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Error", 1.0, "Test", "no date");

        assertThrows(MTException.class, () -> cmd.execute(moneyListStub));
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Test", 1.0, "Test", "no date");

        assertFalse(cmd.shouldExit());
    }

    @Test
    void execute_shouldHandleSpecialCharacters() throws MTException {
        AddExpenseCommand cmd = new AddExpenseCommand(
                "Café & Co.", 15.99, "Food & Drinks", "2023-12-31");

        cmd.execute(moneyListStub);

        assertEquals("addExp Café & Co. $/15.99 c/Food & Drinks d/2023-12-31",
                moneyListStub.lastAddedExpense);
    }

    @Test
    void execute_shouldHandleLongDescriptions() throws MTException {
        String longDesc = "Very long description that exceeds typical length "
                + "limits and should still work properly without issues";
        AddExpenseCommand cmd = new AddExpenseCommand(
                longDesc, 100.0, "Misc", "no date");

        cmd.execute(moneyListStub);

        assertEquals("addExp " + longDesc + " $/100.0 " +
                "c/Misc", moneyListStub.lastAddedExpense);
    }
}
