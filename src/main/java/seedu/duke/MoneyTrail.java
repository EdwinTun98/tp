package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    private final ArrayList<String> moneyList;

    public MoneyTrail() {
        this.moneyList = new ArrayList<>();
    }

    public void run() {
        String logo = " __  __ _______ \n"
                + "|  \\/  |__  __|\n"
                + "| \\  / |  | |  \n"
                + "| |\\/| |  | |  \n"
                + "| |  | |  | |  \n"
                + "|_|  |_|  |_|  \n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new MoneyTrail().run();
    }
}
