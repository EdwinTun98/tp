package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    private final ArrayList<String> moneyList;

    public MoneyTrail() {
        this.moneyList = new ArrayList<>();
    }

    public void run() {
        printWelcomeMsg();

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
    }

    public void print(String str) {
        System.out.println(str);
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

    /**
     * Main entry-point for the MoneyTrail budget tracker application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
