package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.entries.Budget;
import seedu.duke.exception.MTException;
import seedu.duke.logger.MTLogger;
import seedu.duke.moneylist.MoneyList;
import seedu.duke.storage.Storage;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

class ListCmdTest {
    private MoneyListStub moneyListStub;
    private ListCommand command;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub();
        command = new ListCommand();
    }

    // Test Stub for MoneyList
    private static class MoneyListStub extends MoneyList {
        public boolean listSummaryCalled = false;
        public boolean shouldThrowException = false;
        public MTException exceptionToThrow = new MTException(
                "Test exception");

        public MoneyListStub() {
            super(new MTLoggerStub(ListCmdTest.class.getName()),
                    new StorageStub(), new TextUIStub());
        }

        @Override
        public void listSummary() throws MTException {
            listSummaryCalled = true;
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
    void execute_callsListSummaryOnMoneyList() throws MTException {
        // When
        command.execute(moneyListStub);

        // Then
        assertTrue(moneyListStub.listSummaryCalled);
    }

    @Test
    void execute_propagatesMTException() {
        // Given
        moneyListStub.shouldThrowException = true;

        // When/Then
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
