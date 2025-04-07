package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

class ListBudgetCmdTest {
    private MoneyListStub moneyListStub;
    private ListBudgetCommand command;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub();
        command = new ListBudgetCommand();
    }

    // Test Stub
    private static class MoneyListStub extends MoneyList {
        public boolean listBudgetsCalled = false;
        public boolean shouldThrowException = false;
        public MTException exceptionToThrow = new MTException(
                "Test exception");

        public MoneyListStub() {
            super(new MTLoggerStub(ListBudgetCmdTest.class.getName()),
                    new StorageStub(), new TextUIStub());
        }

        @Override
        public void listBudgets() throws MTException {
            listBudgetsCalled = true;

            if (shouldThrowException) {
                throw exceptionToThrow;
            }
        }
    }

    // Supporting Stubs
    private static class MTLoggerStub extends MTLogger {
        public MTLoggerStub(String className) {
            super(className);
        }

        @Override
        public void logInfo(String message) {

        }

        @Override
        public void logWarning(String message) {

        }

        @Override
        public void logSevere(String message, Throwable error) {

        }
    }

    private static class StorageStub extends Storage {
        @Override
        public void saveExpenses(ArrayList<String> moneyList) {

        }

        @Override
        public ArrayList<String> loadEntries() {
            return null;
        }

        @Override
        public void saveBudgets(HashMap<String, Budget> budgets) {

        }

        @Override
        public HashMap<String, Budget> loadBudgets() {
            return null;
        }
    }

    private static class TextUIStub extends TextUI {
        @Override
        public void print(String message) {

        }

        @Override
        public void printNumItems(int count) {

        }
    }

    @Test
    void execute_callsListBudgetsOnMoneyList() throws MTException {
        command.execute(moneyListStub);

        assertTrue(moneyListStub.listBudgetsCalled);
    }

    @Test
    void execute_propagatesMTException() {
        moneyListStub.shouldThrowException = true;
        MTException thrown = assertThrows(MTException.class,
                () -> command.execute(moneyListStub));

        assertEquals(moneyListStub.exceptionToThrow, thrown);
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        assertFalse(command.shouldExit());
    }

    @Test
    void execute_withNullMoneyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> command.execute(null));
    }
}
