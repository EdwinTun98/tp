package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final MTLogger logger;
    private final String filePath;

    public Storage(String path) {
        this.filePath = path;
        this.logger = new MTLogger(Storage.class.getName());
    }

    public void saveEntries(ArrayList<String> moneyList) throws MTException {
        logger.logInfo("Saving entries into " + filePath);

        try (FileWriter writer = new FileWriter(filePath)) {
            for (String entry: moneyList) {
                writer.write(entry + "\n");
            }
        } catch (IOException error) {
            logger.logSevere("Error saving entries into " + filePath, error);
            throw new MTException("Error saving entries: " + error.getMessage());
        }
    }

    public ArrayList<String> loadEntries() throws MTException {
        logger.logInfo("Loading previous entries from " + filePath);

        ArrayList<String> entries = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return entries;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                entries.add(scanner.nextLine());
            }
        } catch (FileNotFoundException error) {
            logger.logSevere("Failed to find file at " + filePath, error);
            throw new MTException("File not found. Starting with an empty list.");
        }

        return entries;
    }
}
