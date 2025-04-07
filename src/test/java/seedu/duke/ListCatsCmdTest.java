package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.moneylist.MoneyList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListCatsCommandTest {
    private MoneyListStub moneyListStub;
    private ListCatsCommand command;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub();
        command = new ListCatsCommand();
    }

    // Test Stub
    private static class MoneyListStub extends MoneyList {
        public boolean listCatsCalled = false;
        public boolean shouldThrowException = false;

        public MoneyListStub() {
            super(null, null, null);
        }

        @Override
        public void listCats() {
            listCatsCalled = true;
            if (shouldThrowException) {
                throw new RuntimeException("Test exception");
            }
        }
    }

    @Test
    void execute_callsListCatsOnMoneyList() {
        command.execute(moneyListStub);

        assertTrue(moneyListStub.listCatsCalled);
    }

    @Test
    void execute_propagatesRuntimeExceptions() {
        moneyListStub.shouldThrowException = true;

        assertThrows(RuntimeException.class,
                () -> command.execute(moneyListStub));
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
