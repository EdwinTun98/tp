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

import java.util.ArrayList;
import java.util.HashMap;

class SetCatBgtCmdTest {
    private MoneyListStub moneyListStub;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub();
    }

    // Test Stub for MoneyList
    private static class MoneyListStub extends MoneyList {
        public String receivedCategory;
        public double receivedAmount;
        public boolean shouldThrowException = false;
        public MTException exceptionToThrow = new MTException(
                "Test exception");

        public MoneyListStub() {
            super(new MTLoggerStub(SetCatBgtCmdTest.class.getName()),
                    new StorageStub(), new TextUIStub());
        }

        @Override
        public void setCategoryLimit(String category,
                                     double amount) throws MTException {
            this.receivedCategory = category;
            this.receivedAmount = amount;

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
    void execute_passesCorrectParametersToMoneyList() throws MTException {
        String testCategory = "Food";
        double testAmount = 500.0;
        SetCategoryBudgetCommand command =
                new SetCategoryBudgetCommand(testCategory, testAmount);

        command.execute(moneyListStub);

        assertEquals(testCategory, moneyListStub.receivedCategory);
        assertEquals(testAmount, moneyListStub.receivedAmount,
                0.001);
    }

    @Test
    void execute_propagatesMTException() {
        moneyListStub.shouldThrowException = true;
        SetCategoryBudgetCommand command =
                new SetCategoryBudgetCommand("Test", 100.0);

        MTException thrown = assertThrows(MTException.class,
                () -> command.execute(moneyListStub));

        assertEquals(moneyListStub.exceptionToThrow, thrown);
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        SetCategoryBudgetCommand command =
                new SetCategoryBudgetCommand("Any", 0.0);

        assertFalse(command.shouldExit());
    }

    @Test
    void execute_withNullMoneyList_throwsNullPointerException() {
        SetCategoryBudgetCommand command =
                new SetCategoryBudgetCommand("Test", 200.0);

        assertThrows(NullPointerException.class,
                () -> command.execute(null));
    }

    @Test
    void execute_withEmptyCategory_passesEmptyString() throws MTException {
        SetCategoryBudgetCommand command =
                new SetCategoryBudgetCommand("", 100.0);
        command.execute(moneyListStub);

        assertEquals("", moneyListStub.receivedCategory);
    }

    @Test
    void execute_withNegativeAmount_passesNegativeValue() throws MTException {
        SetCategoryBudgetCommand command =
                new SetCategoryBudgetCommand("Bills", -50.0);
        command.execute(moneyListStub);

        assertEquals(-50.0, moneyListStub.receivedAmount,
                0.001);
    }
}
