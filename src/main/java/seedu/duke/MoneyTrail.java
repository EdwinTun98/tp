package seedu.duke;

import java.util.Scanner;

/**
 * Serves as the Main class for the MoneyTrail budget tracking application.
 * Handles program initialization and the main command loop.
 */
public class MoneyTrail {
    private final MoneyList moneyList;
    private final MTLogger logger;
    private final Scanner in;
    private final TextUI ui;
    private final Parser parser;

    /**
     * Initializes the core program components.
     */
    public MoneyTrail() {
        this.in = new Scanner(System.in);
        this.ui = new TextUI();
        this.logger = new MTLogger(MoneyTrail.class.getName());
        Storage storage = new Storage();
        this.moneyList = new MoneyList(logger, storage, ui);
        this.parser = new Parser();
    }

    /**
     * Runs the main program loop.
     * Loads saved data and processes user commands until exit is requested.
     */
    public void run() {
        logger.logInfo("Starting CLI program.");

        try {
            moneyList.loadEntriesFromFile();
        } catch (MTException error) {
            logger.logSevere("Error loading entries from file: "
                    + error.getMessage(), error);
            ui.printErrorMsg(error);
        }

        ui.printWelcomeMsg();

        boolean shouldExit = false;
        while (!shouldExit) {
            String input = in.nextLine().trim();

            try {
                Command command = parser.parseCommand(input);

                // Handle the HelpCommand specifically since it needs UI access
                if (command instanceof HelpCommand) {
                    ui.showAllAvailableCommands();
                } else {
                    command.execute(moneyList);
                }

                shouldExit = command.shouldExit();
            } catch (MTException error) {
                logger.logWarning("Error processing command: " +
                        error.getMessage());
                ui.printErrorMsg(error);
            } finally {
                if (!shouldExit) {
                    ui.addLineDivider();
                    ui.printPromptMsg();
                }
            }
        }

        ui.printExitMsg();
    }

    /**
     * Application entry point.
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
