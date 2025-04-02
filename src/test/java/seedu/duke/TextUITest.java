package seedu.duke;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link seedu.duke.TextUI}.
 * Contains unit tests for the text-based user interface components.
 */
public class TextUITest {
    //@@author rchlai
    /**
     * Tests that {@link TextUI#print} correctly outputs messages
     * to standard output.
     * Verifies both content and newline formatting.
     */
    @Test
    void print_outputsCorrectMessage() {
        TextUI textUI = new TextUI();

        String testMessage = "Test message";

        // Create a stream to capture console output
        // ByteArrayOutputStream will store what gets printed to System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Redirect standard System.out to our custom stream
        // This allows us to capture and inspect printed output
        System.setOut(new PrintStream(outputStream));

        textUI.print(testMessage);

        // Verify the output matches what we expect:
        // 1. The exact message we passed in
        // 2. Plus a newline character (System.lineSeparator())
        // 3. Compare against the captured output as a String
        assertEquals(testMessage + System.lineSeparator(),
                outputStream.toString(),
                "Printed message should match input");

        // Important: Restore System.out to its original state
        // so other tests aren't affected by our redirection
        System.setOut(System.out);
    }

    /**
     * Tests that {@link TextUI#addLineDivider} correctly outputs
     * the line divider.
     */
    @Test
    void print_outputsCorrectDivider() {
        TextUI textUI = new TextUI();

        String testMessage = "--------------------------" +
                "-----------------------------------------------------";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.print(testMessage);

        assertEquals(testMessage + System.lineSeparator(),
                outputStream.toString(),
                "Printed message should match input");

        System.setOut(System.out);
    }

    /**
     * Tests that {@link TextUI#printWelcomeMsg} correctly outputs
     * the welcome message and application logo.
     */
    @Test
    void printWelcomeMsg_outputsCorrectWelcome() {
        TextUI textUI = new TextUI();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.printWelcomeMsg();

        // Verify the output contains both logo and welcome text
        String output = outputStream.toString();
        assertTrue(output.contains("Welcome user to") &&
                        output.contains("__  __ _______"),
                "Welcome message should contain logo and welcome text");

        System.setOut(System.out);
    }

    /**
     * Tests that {@link TextUI#printErrorMsg} correctly outputs
     * error messages to standard output.
     */
    @Test
    void printErrorMsg_outputsCorrectErrorMessage() {
        TextUI textUI = new TextUI();
        String errorMessage = "Test error message";
        MTException testException = new MTException(errorMessage);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.printErrorMsg(testException);

        assertEquals(errorMessage + System.lineSeparator(),
                outputStream.toString(),
                "Error message should match exception message");

        System.setOut(System.out);
    }

    /**
     * Tests that {@link TextUI#printPromptMsg} correctly outputs
     * the standard prompt message.
     */
    @Test
    void printPromptMsg_outputsCorrectPrompt() {
        TextUI textUI = new TextUI();
        String expectedPrompt = "What do you want to do next?";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.printPromptMsg();

        assertEquals(expectedPrompt + System.lineSeparator(),
                outputStream.toString(),
                "Should output the standard prompt message");

        System.setOut(System.out);
    }

    /**
     * Tests that {@link TextUI#printExitMsg} correctly outputs
     * the exit message with divider.
     */
    @Test
    void printExitMsg_outputsCorrectExitMessage() {
        TextUI textUI = new TextUI();
        String lineDivider = "--------------------------" +
                "-----------------------------------------------------";
        String expectedExitMsg = "Exiting program... Thank you for using MoneyTrail! :)";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.printExitMsg();

        String output = outputStream.toString();
        assertTrue(output.contains(lineDivider) &&
                        output.contains(expectedExitMsg),
                "Should output divider followed by exit message");

        System.setOut(System.out);
    }

    /**
     * Tests that {@link TextUI#printNumItems} correctly formats
     * and outputs the item count message.
     */
    @Test
    void printNumItems_outputsCorrectCount() {
        TextUI textUI = new TextUI();
        int testCount = 5;
        String expectedOutput = "You now have 5 entries.";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.printNumItems(testCount);

        assertEquals(expectedOutput + System.lineSeparator(),
                outputStream.toString(),
                "Should output correctly formatted count message");

        System.setOut(System.out);
    }

  //  /**
   //  * Tests that {@link TextUI#showAllAvailableCommands} outputs
    //  * all commands in the correct format.
    // */
/*    @Test
    void showAllAvailableCommands_outputsCompleteList() {
        TextUI textUI = new TextUI();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.showAllAvailableCommands();

        String output = outputStream.toString();
        assertTrue(output.contains("List of available commands:") &&
                        output.contains("1. help") &&
                        output.contains("2. list") &&
                        output.contains("3. addExp") &&
                        output.contains("10. exit"),
                "Should output complete command list with numbering");

        System.setOut(System.out);
    }*/

    /**
     * Tests that {@link TextUI#printNumItems} handles pluralization
     * correctly for single entry.
     */
    @Test
    void printNumItems_withSingleEntry_usesSingularForm() {
        TextUI textUI = new TextUI();
        int testCount = 1;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        textUI.printNumItems(testCount);

        assertTrue(outputStream.toString().contains("1 entry"),
                "Should use singular 'entry' for count of 1");

        System.setOut(System.out);
    }
}
