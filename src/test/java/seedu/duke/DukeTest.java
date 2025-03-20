package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DukeTest {
//    private MoneyTrail moneyTrail;
//
//    @BeforeEach
//    public void setUp() {
//        moneyTrail = new MoneyTrail();
//        // Simulate loading entries into moneyList for testing
//        moneyTrail.moneyList.add("Entry 1: $100");
//        moneyTrail.moneyList.add("Entry 2: $50");
//        moneyTrail.moneyList.add("Entry 3: $75");
//    }
//
//    @Test
//    public void testDeleteEntry_validIndex() throws MTException {
//        // Initial size of moneyList
//        int initialSize = moneyTrail.moneyList.size();
//
//        // Delete the second entry (index 1)
//        moneyTrail.deleteEntry("delete 2");
//
//        // Verify the size has decreased by 1
//        assertEquals(moneyTrail.moneyList.size(), initialSize - 1,
//                "The size of moneyList should decrease by 1 after deletion.");
//
//        // Verify the correct entry was deleted
//        assertFalse(moneyTrail.moneyList.contains("Entry 2: $50"),
//                "Entry 2 should be deleted from moneyList.");
//    }
//
//    @Test
//    public void testDeleteEntry_invalidIndex() {
//        // Attempt to delete an entry with an invalid index
//        MTException thrown = assertThrows(MTException.class, () -> {
//            moneyTrail.deleteEntry("delete 5"); // Index 5 does not exist
//        });
//
//        // Verify the exception message
//        assertEquals("Invalid or unavailable entry number.", thrown.getMessage(),
//                "The exception message should indicate an invalid entry number.");
//    }
    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        // Initialize dependencies
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();

        // Create a new MoneyList instance
        moneyList = new MoneyList(logger, storage, ui);

        // Simulate loading entries into moneyList for testing
        moneyList.getMoneyList().add("Entry 1: $100");
        moneyList.getMoneyList().add("Entry 2: $50");
        moneyList.getMoneyList().add("Entry 3: $75");
    }

    @Test
    public void testDeleteEntry_validIndex() throws MTException {
        // Initial size of moneyList
        int initialSize = moneyList.getMoneyList().size();

        // Delete the second entry (index 1)
        moneyList.deleteEntry("delete 2");

        // Verify the size has decreased by 1
        assertEquals(moneyList.getMoneyList().size(), initialSize - 1,
                "The size of moneyList should decrease by 1 after deletion.");

        // Verify the correct entry was deleted
        assertFalse(moneyList.getMoneyList().contains("Entry 2: $50"),
                "Entry 2 should be deleted from moneyList.");
    }

    @Test
    public void testDeleteEntry_invalidIndex() {
        // Attempt to delete an entry with an invalid index
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.deleteEntry("delete 5"); // Index 5 does not exist
        });

        // Verify the exception message
        assertEquals("Invalid or unavailable entry number.", thrown.getMessage(),
                "The exception message should indicate an invalid entry number.");
    }
}
