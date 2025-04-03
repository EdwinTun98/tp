package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ListBudgetsTest {
    private MoneyList moneyList;
    private MTLogger logger;
    private Storage storage;
    private TextUI ui;

    @BeforeEach
    public void setUp() {
        logger = new MTLogger(MoneyTrail.class.getName());
        storage = new Storage();
        ui = new TextUI();
        moneyList = new MoneyList(logger, storage, ui);
    }

    @Test
    public void testListBudgets_withBudgets() throws MTException {
        moneyList.setCategoryLimit("food", 100.00);
        moneyList.setCategoryLimit("transport", 50.00);

        assertDoesNotThrow(() -> moneyList.listBudgets());
    }

    @Test
    public void testListBudgets_withOverallBudget() throws MTException {
        moneyList.setTotalBudget("setTotBgt 500.00");
        assertDoesNotThrow(() -> moneyList.listBudgets());
    }

    @Test
    public void testListBudgets_withNoBudgets() {
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.listBudgets();
        });
        assertEquals("No budgets have been set.", thrown.getMessage());
    }

    @Test
    public void testSetCategoryLimit_negativeBudget() {
        MTException thrown = assertThrows(MTException.class, () -> {
            moneyList.setCategoryLimit("food", -159.00);
        });
        assertEquals("Category budget cannot be negative.", thrown.getMessage());
    }

    @Test
    public void testSetTotalBudget_invalidInput() {
        assertThrows(MTException.class, () -> moneyList.setTotalBudget("setTotBgt car"));
    }

    @Test
    public void testSetTotalBudget_emptyInput() {
        assertThrows(AssertionError.class, () -> moneyList.setTotalBudget(""));
    }

    @Test
    public void testSetCategoryLimit_replaceExistingBudget() throws MTException {
        moneyList.setCategoryLimit("food", 100.00);
        moneyList.setCategoryLimit("food", 200.00);

        HashMap<String, Budget> budgets = moneyList.getBudgetList();
        Budget foodBudget = budgets.get("food");
        assertNotNull(foodBudget);
        assertEquals(200.00, foodBudget.getAmount(), 0.01);
    }

    @Test
    public void testSetCategoryLimit_categoryWithSymbols() throws MTException {
        // Given
        String category = "@food#678";
        double amount = 100.00;

        // When
        moneyList.setCategoryLimit(category, amount);

        // Then
        Budget result = moneyList.getBudgetList().get(category);
        assertEquals(category, result.getCategory(), "Category name should be stored as is.");
        assertEquals(amount, result.getAmount(), 0.01, "Amount should be stored correctly.");
    }

}
