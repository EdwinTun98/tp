package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyTrail {
    private final ArrayList<String> moneyList;

    public MoneyTrail() {
        this.moneyList = new ArrayList<>();
    }

    public void run() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
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
