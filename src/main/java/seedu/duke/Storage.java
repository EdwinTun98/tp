package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving of entries to a text file.
 */
public class Storage {
    // relative path of 'F:\repos\tp\mt.txt' directory
    private static final String FILE_PATH = "mt.txt";
    private final MTLogger logger;

    //@@author rchlai
    /**
     * Initializes a new Storage instance with a logger.
     */
    public Storage() {
        this.logger = new MTLogger(Storage.class.getName());
    }
    //@@author

    //@@author rchlai
    /**
     * Saves all entries to the storage file.
     *
     * @param moneyList List of entries to save
     * @throws MTException If there's an error writing to file
     */
    public void saveEntries(ArrayList<String> moneyList) throws MTException {
        logger.logInfo("Saving entries into " + FILE_PATH);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (String entry: moneyList) {
                writer.write(entry + "\n");
            }
        } catch (IOException error) {
            logger.logSevere("Error saving entries into " + FILE_PATH, error);
            throw new MTException("Error saving entries: " + error.getMessage());
        }
    }
    //@@author

    //@@author rchlai
    /**
     * Loads all entries from the storage file.
     *
     * @return List of loaded entries
     * @throws MTException If the file exists but cannot be read
     */
    public ArrayList<String> loadEntries() throws MTException {
        logger.logInfo("Loading previous entries from " + FILE_PATH);

        ArrayList<String> entries = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return entries;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                entries.add(scanner.nextLine());
            }
        } catch (FileNotFoundException error) {
            logger.logSevere("Failed to find file at " + FILE_PATH, error);
            throw new MTException("File not found. Starting with an empty list.");
        }

        return entries;
    }
    //@@author
}
