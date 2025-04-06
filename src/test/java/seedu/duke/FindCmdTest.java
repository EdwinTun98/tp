package seedu.duke;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.*;

class FindCommandTest {

    private MoneyListSpy moneyListSpy;
    private MTLoggerStub loggerStub;
    private TextUIStub uiStub;

    @BeforeEach
    void setUp() {
        loggerStub = new MTLoggerStub(FindCommandTest.class.getName());
        uiStub = new TextUIStub();
        moneyListSpy = new MoneyListSpy(loggerStub, new StorageStub(), uiStub);
    }

    // Test helper spy class
    static class MoneyListSpy extends MoneyList {
        public String lastSearchKeyword;
        public boolean shouldThrow = false;
        public ArrayList<String> mockResults = new ArrayList<>();

        public MoneyListSpy(MTLogger logger, Storage storage, TextUI ui) {
            super(logger, storage, ui);
        }

        @Override
        public void findEntry(String keyword) throws MTException {
            lastSearchKeyword = keyword;
            if (shouldThrow) {
                throw new MTException("Test exception");
            }
            // Simulate finding mock results
            if (!mockResults.isEmpty()) {
                for (String result : mockResults) {
                    ui.print(result);
                }
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
        public ArrayList<String> outputs = new ArrayList<>();

        @Override
        public void print(String message) {
            outputs.add(message);
        }

        @Override
        public void printNumItems(int count) {
            outputs.add("Items: " + count);
        }
    }

    static class StorageStub extends Storage {
        @Override public void saveExpenses(ArrayList<String> expenses) {}
        @Override public ArrayList<String> loadEntries() { return null; }
        @Override public void saveBudgets(HashMap<String, Budget> budgets) {}
        @Override public HashMap<String, Budget> loadBudgets() { return null; }
    }

    @Test
    void execute_validKeyword() throws MTException {
        // Setup
        moneyListSpy.mockResults.add("Expense: Lunch $12.50 {Food}");
        moneyListSpy.mockResults.add("Income: Salary $2000.00");
        FindCommand cmd = new FindCommand("Lunch");

        // Execute
        cmd.execute(moneyListSpy);

        // Verify
        assertEquals("Lunch", moneyListSpy.lastSearchKeyword);
        assertEquals(2, uiStub.outputs.size());
        assertTrue(uiStub.outputs.get(0).contains("Lunch"));
    }

    @Test
    void execute_emptyKeyword_shouldThrow() {
        FindCommand cmd = new FindCommand("");

        assertThrows(MTException.class, () -> cmd.execute(moneyListSpy));
        assertEquals("SEVERE: ", loggerStub.lastLog.substring(0, 8));
    }

    @Test
    void execute_whitespaceKeyword_shouldThrow() {
        FindCommand cmd = new FindCommand("   ");

        assertThrows(MTException.class, () -> cmd.execute(moneyListSpy));
        assertTrue(loggerStub.lastLog.contains("Invalid entry"));
    }

    @Test
    void execute_specialCharacters() throws MTException {
        moneyListSpy.mockResults.add("Expense: Café $15.00 {Food}");
        FindCommand cmd = new FindCommand("Café");

        cmd.execute(moneyListSpy);

        assertEquals("Café", moneyListSpy.lastSearchKeyword);
        assertTrue(uiStub.outputs.get(0).contains("Café"));
    }

    @Test
    void execute_longKeyword() throws MTException {
        String longKeyword = "Very long search phrase that should work properly";
        moneyListSpy.mockResults.add("Expense: Very long search phrase $50.00 {Misc}");
        FindCommand cmd = new FindCommand(longKeyword);

        cmd.execute(moneyListSpy);

        assertEquals(longKeyword, moneyListSpy.lastSearchKeyword);
    }

    @Test
    void execute_propagatesMoneyListException() {
        moneyListSpy.shouldThrow = true;
        FindCommand cmd = new FindCommand("test");

        assertThrows(MTException.class, () -> cmd.execute(moneyListSpy));
        assertEquals("SEVERE: Test exception", loggerStub.lastLog);
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        FindCommand cmd = new FindCommand("any");
        assertFalse(cmd.shouldExit());
    }

    @Test
    void execute_caseInsensitiveSearch() throws MTException {
        moneyListSpy.mockResults.add("Expense: lunch $12.50 {Food}");
        FindCommand cmd = new FindCommand("LUNCH");

        cmd.execute(moneyListSpy);

        assertEquals("LUNCH", moneyListSpy.lastSearchKeyword);
        assertEquals(1, uiStub.outputs.size());
    }

    @Test
    void execute_noResultsFound() throws MTException {
        // No mock results added
        FindCommand cmd = new FindCommand("nonexistent");

        cmd.execute(moneyListSpy);

        assertEquals("nonexistent", moneyListSpy.lastSearchKeyword);
        assertEquals(0, uiStub.outputs.size());
    }
}
