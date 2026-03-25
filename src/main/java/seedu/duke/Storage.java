package seedu.duke;

import seedu.duke.recordtype.Record;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private static final String filepath = "records.txt";

    public void saveToFile(RecordList list) throws IOException {
        logger.info("Saving records to file: " + filepath);
        Path path = Paths.get(filepath);

        Path directory = path.getParent();
        if (directory != null && !Files.exists(directory)) {
            Files.createDirectories(directory);
            logger.fine("Created storage directory: " + directory);
        }

        if (Files.notExists(path)) {
            Files.createFile(path);
            logger.fine("Created storage file: " + filepath);
        }
        assert Files.exists(path) : "file should exist after path creation";

        FileWriter fw = new FileWriter(path.toFile());

        for (Record record : list) {
            String keyword = getKeyword(record.getRecordType());
            if (keyword == null) {
                logger.warning("Skipping unknown record type: " + record.getRecordType());
                continue;
            }
            fw.write(keyword + " " + record.getTitle()
                    + " /role " + record.getRole()
                    + " /tech " + record.getTech()
                    + " /from " + record.getFrom()
                    + " /to " + record.getTo() + "\n");
        }
        logger.info("Records saved successfully");
        System.out.println("Records saved to file.");
        fw.close();
    }

    public RecordList loadFromFile(String filepath) throws FileNotFoundException {
        logger.info("Loading records from file: " + filepath);
        assert filepath != null && !filepath.isBlank() : "filepath should not be blank";
        File file = new File(filepath);
        Path path = Paths.get(filepath);
        Path directory = path.getParent();
        RecordList list = new RecordList();

        if (directory != null && !Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
                logger.fine("Created storage directory: " + directory);
            } catch (IOException e) {
                logger.severe("Error creating the directory: " + e.getMessage());
                System.out.println("Error creating the directory.");
                return list;
            }
        }

        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
                logger.fine("Created storage file: " + filepath);
            } catch (IOException e) {
                logger.severe("Error creating the file: " + e.getMessage());
                System.out.println("Error creating the file.");
                return list;
            }
        }
        assert Files.exists(path) : "file should exist after path creation";

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine().strip();

            if (line.isEmpty()) {
                continue;
            }

            try {
                Record record = parseRecord(line);
                if (record != null) {
                    list.add(record);
                } else {
                    logger.warning("Skipping invalid record line: " + line);
                }
            } catch (RuntimeException e) {
                logger.warning("Unexpected error while loading line: " + line
                        + " | Reason: " + e.getMessage());
            }
        }
        sc.close();
        logger.info("Records loaded successfully");
        System.out.println("Loaded records from file.");

        return list;
    }

    public static String getFilepath() {
        return filepath;
    }

    private Record parseRecord(String line) {
        assert line != null : "line should not be null";

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            logger.fine("Skipping empty record line");
            return null;
        }

        String[] split = trimmed.split("\\s+", 2);
        if (split.length < 2) {
            logger.warning("Invalid record line (missing arguments): " + line);
            return null;
        }

        String keyword = split[0].toLowerCase();
        String args = split[1].trim();

        int roleIndex = args.indexOf("/role");
        int techIndex = args.indexOf("/tech");
        int fromIndex = args.indexOf("/from");
        int toIndex = args.indexOf("/to");

        if (roleIndex == -1 || techIndex == -1 || fromIndex == -1 || toIndex == -1) {
            logger.warning("Invalid record line (missing required field(s)): " + line);
            return null;
        }

        if (!(roleIndex < techIndex && techIndex < fromIndex && fromIndex < toIndex)) {
            logger.warning("Invalid record line (fields are in wrong order): " + line);
            return null;
        }

        try {
            String title = args.substring(0, roleIndex).trim();
            String role = args.substring(roleIndex + 5, techIndex).trim();
            String tech = args.substring(techIndex + 5, fromIndex).trim();
            String fromPart = args.substring(fromIndex + 5, toIndex).trim();
            String toPart = args.substring(toIndex + 3).trim();

            if (title.isEmpty() || role.isEmpty() || tech.isEmpty()
                    || fromPart.isEmpty() || toPart.isEmpty()) {
                logger.warning("Invalid record line (field value is empty): " + line);
                return null;
            }

            YearMonth from = YearMonth.parse(fromPart);
            YearMonth to = YearMonth.parse(toPart);

            if (to.isBefore(from)) {
                logger.warning("Invalid record line (end date cannot be before start date): " + line);
                return null;
            }

            switch (keyword) {
            case "project":
                return new Project(title, role, tech, from, to);
            case "experience":
                return new Experience(title, role, tech, from, to);
            case "cca":
                return new Cca(title, role, tech, from, to);
            default:
                logger.warning("Unknown record keyword while parsing: " + keyword);
                return null;
            }
        } catch (RuntimeException e) {
            logger.warning("Failed to parse record line: " + line + " | Reason: " + e.getMessage());
            return null;
        }
    }

    private String getKeyword(String recordType) {
        switch (recordType) {
        case "P":
            return "project";
        case "E":
            return "experience";
        case "C":
            return "cca";
        default:
            return null;
        }
    }
}
