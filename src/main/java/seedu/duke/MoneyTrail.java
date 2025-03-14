package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    // relative path of 'F:\repos\tp\mt.txt' directory
    private static final String FILE_PATH = "mt.txt";
    private static final String LINE_DIVIDER = "----------------------" +
            "-----------------------------------";
    private static final int INDEX_OFFSET = 1;

    public ArrayList<String> moneyList;
    private final Scanner in;
    private final Storage storage;

    public MoneyTrail() {
        this.moneyList = new ArrayList<>();
        this.in = new Scanner(System.in);
        this.storage = new Storage(FILE_PATH);
    }

    public void run() {
        try {
            loadEntriesFromFile();
        } catch (MTException error) {
            printErrorMsg(error);
        }

        printWelcomeMsg();

        while (true) {
            String input = this.in.nextLine();
            if (input.startsWith("delete")) {
                try {
                    deleteEntry(input);
                } catch (MTException error) {
                    printErrorMsg(error);
                } finally {
                    addLineDivider();
                }
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                break;
            }
        }

        printExitMsg();
    }

    public void print(String str) {
        System.out.println(str);
    }

    public void addLineDivider() {
        print(LINE_DIVIDER);
    }

    public void printAppLogo() {
        String logo = " __  __ _______ \n"
                + "|  \\/  |__  __|\n"
                + "| \\  / |  | |  \n"
                + "| |\\/| |  | |  \n"
                + "| |  | |  | |  \n"
                + "|_|  |_|  |_|  \n";
        print("Welcome user to\n" + logo);
    }

    public void printWelcomeMsg() {
        printAppLogo();
        print("What do you want to do today?");
    }

    public void printExitMsg() {
        addLineDivider();
        print("Exiting program... Thank you for using MoneyTrail! :)");
    }

    public void printErrorMsg(MTException error) {
        print(error.getMessage());
    }

    public void printNumItems() {
        print("You now have " + moneyList.size() + " entries.");
    }

    private int extractIndex(String input) {
        return Integer.parseInt(input.replaceAll(
                "[^0-9]", "")) - INDEX_OFFSET;
    }

    private void validateIndex(int index) throws MTException {
        if (index < 0 || index >= moneyList.size()) {
            throw new MTException("Invalid or unavailable entry number.");
        }
    }

    public void deleteEntry(String input) throws MTException {
        try {
            // obtain the index to search for entry to be deleted
            int deleteIndex = extractIndex(input);
            validateIndex(deleteIndex);

            // display entry before deletion

            print("This entry will be permanently deleted:\n");
            print(moneyList.get(deleteIndex));

            // remove entry from moneyList
            moneyList.remove(deleteIndex);
            // save updated list
            storage.saveEntries(moneyList);
            // print out number of items left in moneyList
            printNumItems();
        } catch (NumberFormatException error) {
            throw new MTException("Use: delete <ENTRY_NUMBER>");
        }
    }

    private void loadEntriesFromFile() throws MTException {
        ArrayList<String> loadedEntries = storage.loadEntries();
        if (loadedEntries != null) {
            moneyList.addAll(loadedEntries);
        }
        print("Loaded " + moneyList.size() + " entries from file.");
    }

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
