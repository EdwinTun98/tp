package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

class CheckExpCmdTest {
    private MoneyListStub moneyListStub;
    private TextUIStub uiStub;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub();
        uiStub = new TextUIStub();
    }

    @AfterEach
    void tearDown() {
        moneyListStub.clear();
        uiStub.clear();
    }

    @Test
    void execute_overallBudget_checkExecuted() throws MTException {
        CheckExpensesCommand command =
                new CheckExpensesCommand("Overall");
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCategoriesChecked().size());

        assertEquals("Overall",
                moneyListStub.getCategoriesChecked().get(0));
    }

    @Test
    void execute_specificCategory_checkExecuted() throws MTException {
        CheckExpensesCommand command =
                new CheckExpensesCommand("Food");
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCategoriesChecked().size());

        assertEquals("Food",
                moneyListStub.getCategoriesChecked().get(0));
    }

    @Test
    void execute_emptyCategory_throwsException() {
        CheckExpensesCommand command =
                new CheckExpensesCommand("");

        assertThrows(MTException.class,
                () -> command.execute(moneyListStub));
    }

    @Test
    void execute_nullCategory_throwsException() {
        CheckExpensesCommand command =
                new CheckExpensesCommand(null);

        assertThrows(MTException.class,
                () -> command.execute(moneyListStub));
    }

    @Test
    void execute_nonExistingCategory_checkExecuted() throws MTException {
        CheckExpensesCommand command =
                new CheckExpensesCommand("NonExisting");
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCategoriesChecked().size());

        assertEquals("NonExisting",
                moneyListStub.getCategoriesChecked().get(0));
    }

    @Test
    void shouldExit_alwaysReturnsFalse() {
        CheckExpensesCommand command =
                new CheckExpensesCommand("Overall");

        assertFalse(command.shouldExit());
    }

    // Stub classes for testing isolation
    private static class MoneyListStub extends MoneyList {
        private final ArrayList<String> categoriesChecked =
                new ArrayList<>();

        public MoneyListStub() {
            super(new MTLoggerStub(), new StorageStub(), new TextUIStub());
        }

        @Override
        public void checkExpenses(String category) throws MTException {
            categoriesChecked.add(category);

            // Simulate validation that would happen in real MoneyList
            if (category == null || category.trim().isEmpty()) {
                throw new MTException("Please specify a category" +
                        " or use 'Overall'.");
            }
        }

        public List<String> getCategoriesChecked() {
            return categoriesChecked;
        }

        public void clear() {
            categoriesChecked.clear();
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
    }

    private static class TextUIStub extends TextUI {
        private final ArrayList<String> printedMessages =
                new ArrayList<>();

        @Override
        public void print(String str) {
            printedMessages.add(str);
        }

        public List<String> getPrintedMessages() {
            return printedMessages;
        }

        public void clear() {
            printedMessages.clear();
        }
    }
}
