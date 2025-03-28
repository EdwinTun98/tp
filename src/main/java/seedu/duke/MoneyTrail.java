package seedu.duke;

import java.util.Scanner;

public class MoneyTrail {
    private final MoneyList moneyList;
    private final MTLogger logger;
    private final Scanner in;
    private final TextUI ui;
    private final Parser parser;

    public MoneyTrail() {
        this.in = new Scanner(System.in);
        this.ui = new TextUI();
        this.logger = new MTLogger(MoneyTrail.class.getName());
        Storage storage = new Storage();
        this.moneyList = new MoneyList(logger, storage, ui);
        this.parser = new Parser();
    }

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

        boolean isExit = false;
        while (!isExit) {
            String input = in.nextLine().trim();

            try {
                Command command = parser.parseCommand(input);

                // Handle the HelpCommand specifically since it needs UI access
                if (command instanceof HelpCommand) {
                    ui.showAllAvailableCommands();
                } else {
                    command.execute(moneyList);
                }

                isExit = command.isExit();
            } catch (MTException error) {
                logger.logWarning("Error processing command: " +
                        error.getMessage());
                ui.printErrorMsg(error);
            } finally {
                if (!isExit) {
                    ui.addLineDivider();
                    ui.printPromptMsg();
                }
            }
        }

        ui.printExitMsg();
    }

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
