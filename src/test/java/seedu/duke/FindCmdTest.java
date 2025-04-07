package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.ui.TextUI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindCommandTest {
    private MoneyListStub moneyListStub;
    private FindCommand findCommand;

    @BeforeEach
    void setUp() {
        moneyListStub = new MoneyListStub(null,
                null, null);
    }

    // Test Stub Implementation
    private static class MoneyListStub extends MoneyList {
        public String lastSearchKeyword;
        public boolean shouldThrowException = false;

        public MoneyListStub(MTLogger logger, Storage storage,
                             TextUI ui) {
            super(logger, storage, ui);
        }

        @Override
        public void findEntry(String keyword) throws MTException {
            this.lastSearchKeyword = keyword; // Track the keyword

            if (shouldThrowException) {
                throw new MTException("Test exception");
            }

            // In a real test, you might add mock responses here
        }
    }

    @Test
    void execute_passesKeywordToMoneyList() throws MTException {
        // Setup
        String testKeyword = "lunch";
        findCommand = new FindCommand(testKeyword);

        // Execute
        findCommand.execute(moneyListStub);

        // Verify
        assertEquals(testKeyword, moneyListStub.lastSearchKeyword);
    }

    @Test
    void execute_propagatesExceptions() {
        // Setup
        moneyListStub.shouldThrowException = true;
        findCommand = new FindCommand("test");

        // Execute & Verify
        assertThrows(MTException.class,
                () -> findCommand.execute(moneyListStub));
    }
}
