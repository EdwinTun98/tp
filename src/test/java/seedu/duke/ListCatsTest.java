package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;


public class ListCatsTest {
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
    }

    @Test
    public void testListCats_emptyList () {
        // Ensure the money list is empty
        moneyList.clearEntries(); // Assuming there's a method to clear entries

        // Call listCats() method
        moneyList.listCats();

        // Verify the output
        List<String> messages = ui.getPrintedMessages();
        assertTrue(messages.contains("No entries available to display categories."));
    }

    @Test
    public void testListCats_nonEmptyList () {
        // Populate the money list with sample entries
        moneyList.getMoneyList().add("[Expense] Entry 1 $25.25 {Cat 1} (no date)");
        moneyList.getMoneyList().add("[Expense] Entry 2 $50.505 {Cat 2} (28-3-25)");
        moneyList.getMoneyList().add("[Expense] Entry 3 $75.075 {Uncategorized} (no date)");

        // Call listCats() method
        moneyList.listCats();

        // Verify the output
        List<String> messages = ui.getPrintedMessages();
        System.out.println("Printed messages: " + ui.getPrintedMessages());
        assertTrue(messages.contains("Categories (in order of appearance):"),
                "Output should display the list of categories.");
        assertTrue(messages.contains("- Cat 1"), "Output should include 'Cat 1' as a category.");
        assertTrue(messages.contains("- Cat 2"), "Output should include 'Cat 2' as a category.");
        assertTrue(messages.contains("- Uncategorized"), "Output should include 'Uncategorized' as a category.");
    }
}
