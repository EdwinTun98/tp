package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

class AddIncomeCommandTest {
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
    void execute_validIncomeWithoutDate_addsCorrectly() throws MTException {
        AddIncomeCommand command =
                new AddIncomeCommand("Salary", 5000.00, "no date");
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCommandsReceived().size());

        assertEquals("addIncome Salary $/5000.0",
                moneyListStub.getCommandsReceived().get(0));
    }

    @Test
    void execute_validIncomeWithDate_addsCorrectly() throws MTException {
        AddIncomeCommand command =
                new AddIncomeCommand("Bonus", 1000.50, "2023-10-15");
        command.execute(moneyListStub);

        assertEquals(1,
                moneyListStub.getCommandsReceived().size());

        assertEquals("addIncome Bonus $/1000.5 " +
                "d/2023-10-15", moneyListStub.getCommandsReceived().get(0));
    }

    @Test
    void execute_zeroAmount_throwsException() {
        AddIncomeCommand command = new AddIncomeCommand(
                "Gift", 0.00, "no date");

        assertThrows(MTException.class,
                () -> command.execute(moneyListStub));
    }

    @Test
    void execute_negativeAmount_throwsException() {
        AddIncomeCommand command = new AddIncomeCommand(
                "Refund", -50.00, "2023-10-16");

        assertThrows(MTException.class,
                () -> command.execute(moneyListStub));
    }

    /*
    @Test
    void execute_emptyDescription_throwsException() {
        AddIncomeCommand command = new AddIncomeCommand("", 100.00, "no date");
        assertThrows(MTException.class, () -> command.execute(moneyListStub));
    }

    @Test
    void execute_nullDescription_throwsException() {
        AddIncomeCommand command = new AddIncomeCommand(null, 100.00, "no date");
        assertThrows(MTException.class, () -> command.execute(moneyListStub));
    }
    */

    @Test
    void shouldExit_alwaysReturnsFalse() {
        AddIncomeCommand command = new AddIncomeCommand(
                "Test", 1.00, "no date");

        assertFalse(command.shouldExit());
    }

    // Stub classes for testing isolation
    private static class MoneyListStub extends MoneyList {
        private final ArrayList<String> commandsReceived =
                new ArrayList<>();

        public MoneyListStub() {
            super(new MTLoggerStub(), new StorageStub(), new TextUIStub());
        }

        @Override
        public void addIncome(String input) throws MTException {
            commandsReceived.add(input);

            // Simulate validation that would happen in real MoneyList
            if (input.contains("$/0") || input.contains("$/-")) {
                throw new MTException("Amount must be greater than zero.");
            }
        }

        public List<String> getCommandsReceived() {
            return commandsReceived;
        }

        public void clear() {
            commandsReceived.clear();
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
        public void saveExpenses(ArrayList<String> moneyList) {
            // Do nothing for tests
        }
    }
}
