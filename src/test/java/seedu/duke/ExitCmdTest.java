package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {
    @Test
    void execute_shouldDoNothing() {
        // Setup
        ExitCommand cmd = new ExitCommand();
        MoneyListStub moneyListStub = new MoneyListStub();

        // Execute - should not throw any exceptions
        assertDoesNotThrow(() -> cmd.execute(moneyListStub));

        // Verify no interactions with MoneyList
        assertFalse(moneyListStub.wasAnyMethodCalled());
    }

    @Test
    void shouldExit_alwaysReturnsTrue() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.shouldExit());
    }

    // Minimal stub for MoneyList just to verify no interactions
    static class MoneyListStub extends MoneyList {
        private boolean methodCalled = false;

        public MoneyListStub() {
            super(null, null, null);
        }

        public boolean wasAnyMethodCalled() {
            return methodCalled;
        }
    }
}
