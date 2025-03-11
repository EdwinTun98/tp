package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    private final ArrayList<String> moneyList;
    private final Scanner in;

    private static final String LINE_DIVIDER = "----------------------------" +
            "---------------------------------------------------------------";

    public MoneyTrail() {
        this.moneyList = new ArrayList<>();
        this.in = new Scanner(System.in);
    }

    public void run() {
        printWelcomeMsg();

        while (true) {
            String input = this.in.nextLine();
            if (input.startsWith("delete")) {
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

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
