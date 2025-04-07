package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class MoneyTrailTest {
    private MoneyTrail moneyTrail;
    private MoneyListStub moneyListStub;
    private TextUIStub uiStub;
    private ParserStub parserStub;
    private MTLoggerStub loggerStub;

    // Test Stubs
    private static class MoneyListStub extends MoneyList {
        public boolean loadEntriesCalled = false;
        public boolean shouldThrowOnLoad = false;

        public MoneyListStub(MTLogger logger, Storage storage,
                             TextUI ui) {
            super(logger, storage, ui);
        }

        @Override
        public void loadEntriesFromFile() throws MTException {
            loadEntriesCalled = true;

            if (shouldThrowOnLoad) {
                throw new MTException("Load error");
            }
        }
    }

    private static class TextUIStub extends TextUI {
        public String lastOutput;
        public boolean welcomeShown = false;
        public boolean exitShown = false;
        public boolean helpShown = false;

        @Override
        public void print(String message) {
            lastOutput = message;
        }

        @Override
        public void printWelcomeMsg() {
            welcomeShown = true;
        }

        @Override
        public void printExitMsg() {
            exitShown = true;
        }

        @Override
        public void showAllAvailableCommands() {
            helpShown = true;
        }
    }

    private static class ParserStub extends Parser {
        public String lastInput;
        public boolean shouldThrow = false;

        public Command commandToReturn = new Command() {
            @Override
            public void execute(MoneyList moneyList) {

            }

            @Override
            public boolean shouldExit() {
                return false;
            }
        };

        @Override
        public Command parseCommand(String input) throws MTException {
            lastInput = input;

            if (shouldThrow) {
                throw new MTException("Parse error");
            }

            return commandToReturn;
        }
    }

    private static class MTLoggerStub extends MTLogger {
        public String lastLog;
        public String lastSevereMessage;
        public Throwable lastException;

        public MTLoggerStub() {
            super("TestLogger");
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
            lastSevereMessage = message;
            lastException = error;
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

    @BeforeEach
    void setUp() throws Exception {
        loggerStub = new MTLoggerStub();
        uiStub = new TextUIStub();
        moneyListStub = new MoneyListStub(loggerStub,
                new StorageStub(), uiStub);
        parserStub = new ParserStub();

        // Create standard instance
        moneyTrail = new MoneyTrail();

        // Inject test dependencies
        injectTestDependencies(moneyTrail,
                new ByteArrayInputStream("test\n".getBytes()),
                moneyListStub,
                loggerStub,
                uiStub,
                parserStub);
    }

    private void injectTestDependencies(MoneyTrail instance,
                                        InputStream inputStream,
                                        MoneyList moneyList,
                                        MTLogger logger,
                                        TextUI ui,
                                        Parser parser) throws Exception {
        // Only use reflection for the Scanner since it's private
        // and final
        Field inField = MoneyTrail.class.getDeclaredField("in");
        inField.setAccessible(true);
        inField.set(instance, new Scanner(inputStream));

        // For non-final fields, use package-private or protected setters
        // if available
        // If not, we'll use reflection as a last resort
        setField(instance, "moneyList", moneyList);
        setField(instance, "logger", logger);
        setField(instance, "ui", ui);
        setField(instance, "parser", parser);
    }

    private void setField(Object target, String fieldName,
                          Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void run_processesInputCommands() throws Exception {
        String testInput = "command1\ncommand2\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(
                testInput.getBytes());
        injectTestDependencies(moneyTrail, inputStream,
                moneyListStub, loggerStub, uiStub, parserStub);

        // Setup parser to count commands
        final int[] commandCount = {0};

        parserStub.commandToReturn = new Command() {
            @Override
            public void execute(MoneyList moneyList) {

            }

            @Override
            public boolean shouldExit() {
                return ++commandCount[0] > 2;
            }
        };

        moneyTrail.run();

        assertEquals(3, commandCount[0]);
    }
}
