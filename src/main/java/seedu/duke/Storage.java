package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String path) {
        this.filePath = path;
    }

    public void saveEntries(ArrayList<String> moneyList) throws MTException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String entry: moneyList) {
                writer.write(entry + "\n");
            }
        } catch (IOException e) {
            throw new MTException("Error saving entries: " + e.getMessage());
        }
    }

    public ArrayList<String> loadEntries() throws MTException {
        ArrayList<String> entries = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return entries;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                entries.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new MTException("File not found. Starting with an empty list.");
        }

        return entries;
    }
}
