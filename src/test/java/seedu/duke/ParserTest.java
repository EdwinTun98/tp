package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        MTLoggerStub loggerStub = new MTLoggerStub();
        parser = new Parser();
    }

    private static class MTLoggerStub extends MTLogger {
        public String lastLogMessage;
        public String lastLogLevel;

        public MTLoggerStub() {
            super("TestLogger");
        }

        @Override
        public void logInfo(String message) {
            lastLogLevel = "INFO";
            lastLogMessage = message;
        }

        @Override
        public void logWarning(String message) {
            lastLogLevel = "WARN";
            lastLogMessage = message;
        }

        @Override
        public void logSevere(String message, Throwable error) {
            lastLogLevel = "SEVERE";
            lastLogMessage = message + " - " + error.getMessage();
        }
    }

    @Test
    void parseCommand_emptyInput_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand(""));
    }

    @Test
    void parseCommand_whitespaceInput_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand("   "));
    }

    @Test
    void parseCommand_unknownCommand_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand("unknown"));
    }

    @Test
    void parseCommand_listCommand_returnsListCommand() throws MTException {
        Command command = parser.parseCommand("list");

        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parseCommand_listCaseInsensitive_returnsListCommand() throws MTException {
        Command command = parser.parseCommand("LIST");

        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parseCommand_exitCommand_returnsExitCommand() throws MTException {
        Command command = parser.parseCommand("exit");

        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.shouldExit());
    }

    @Test
    void parseCommand_findCommand_returnsFindCommand() throws MTException {
        Command command = parser.parseCommand("find lunch");

        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    void parseCommand_findMissingKeyword_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand("find "));
    }

    @Test
    void parseCommand_deleteCommand_returnsDeleteCommand() throws MTException {
        Command command = parser.parseCommand("del 1");

        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parseCommand_deleteInvalidNumber_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand("del abc"));
    }

    @Test
    void parseCommand_addExpenseCommand_returnsCommand() throws MTException {
        Command command = parser.parseCommand("addExp Lunch $/12.50");

        assertInstanceOf(AddExpenseCommand.class, command);
    }

    @Test
    void parseCommand_addExpenseMissingAmount_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand("addExp Lunch"));
    }

    @Test
    void parseCommand_setCategoryBudget_returnsCommand() throws MTException {
        Command command = parser.parseCommand("setCatBgt c/Food 500.0");

        assertInstanceOf(SetCategoryBudgetCommand.class, command);
    }

    @Test
    void parseCommand_setCategoryBudgetMissingAmount_throwsException() {
        assertThrows(MTException.class,
                () -> parser.parseCommand("setCatBgt c/Food"));
    }

    @Test
    void parseCommand_editExpense_returnsCommand() throws MTException {
        Command command = parser.parseCommand("edit 1 NewDesc $/15.00");

        assertInstanceOf(EditExpenseCommand.class, command);
    }

    @Test
    void parseCommand_addIncome_returnsCommand() throws MTException {
        Command command = parser.parseCommand("addIncome Salary $/2000.00");

        assertInstanceOf(AddIncomeCommand.class, command);
    }

    @Test
    void parseCommand_helpCommand_returnsHelpCommand() throws MTException {
        Command command = parser.parseCommand("help");

        assertInstanceOf(HelpCommand.class, command);
    }
}
