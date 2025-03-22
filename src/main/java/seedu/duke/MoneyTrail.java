package seedu.duke;

import java.util.Scanner;

public class MoneyTrail {
    private final MoneyList moneyList;
    private final MTLogger logger;
    private final Scanner in;
    private final TextUI ui;

    public MoneyTrail() {
        this.in = new Scanner(System.in);
        this.ui = new TextUI();
        this.logger = new MTLogger(MoneyTrail.class.getName());
        Storage storage = new Storage();
        this.moneyList = new MoneyList(logger, storage, ui);
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

        while (true) {
            String input = in.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("list")) {
                    moneyList.listSummary();
                    continue;
                }

                if (input.startsWith("find")) {
                    moneyList.findEntry(input.substring(5));
                    continue;
                }

                if (input.startsWith("delete")) {
                    moneyList.deleteEntry(input);
                    continue;
                }

                if (input.startsWith("totalExpense")) {
                    moneyList.getTotalExpense();
                    continue;
                }

                if (input.startsWith("setTotalBudget")) {
                    moneyList.setTotalBudget(input);
                    continue;
                }

                if (input.startsWith("addExpense")) {
                    moneyList.addExpense(input);
                    continue;
                }

                if (input.equalsIgnoreCase("help")) {
                    // Display all available commands and their descriptions
                    ui.showAllAvailableCommands();
                    continue;
                }

            } catch (MTException error) {
                logger.logWarning("Error processing command: " + 
                        error.getMessage());
                ui.printErrorMsg(error);
            } finally {
                ui.addLineDivider();
                ui.printPromptMsg();
            }

            if (input.equalsIgnoreCase("exit")) {
                logger.logInfo("Exiting CLI program.");
                break;
            }
        }

        ui.printExitMsg();
    }

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        // assert false : "dummy assertion set to fail";
        new MoneyTrail().run();
    }
}
