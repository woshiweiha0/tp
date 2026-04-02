package seedu.duke;

import seedu.duke.recordtype.Record;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;
import seedu.duke.exceptions.ResumakeException;

/**
 * Handles persistent storage of records in a flat file.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private static final String filepath = "records.txt";
    private final Ui ui;

    public Storage() {
        this(new Ui());
    }

    public Storage(Ui ui) {
        this.ui = ui == null ? new Ui() : ui;
    }

    /**
     * Saves all records to the default storage file.
     *
     * @param list Current record list.
     * @throws ResumakeException If file creation or write fails.
     */
    public void saveToFile(RecordList list) throws ResumakeException {
        logger.info("Saving records to file: " + filepath);
        Path path = Paths.get(filepath);

        try {
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

            // Save User on the first line
            User user = User.getInstance();
            if (user != null) {
                fw.write("USER|" + user.getName() + "|" + user.getNumber() + "|" + user.getEmail() + "\n");
            }

            for (Record record : list) {
                if (record == null) {
                    logger.warning("Skipping null record.");
                    continue;
                }

                String keyword = getKeyword(record.getRecordType());
                if (keyword == null) {
                    logger.warning("Skipping unknown record type: " + record.getRecordType());
                    continue;
                }

                StringBuilder line = new StringBuilder();
                line.append(keyword).append(" ").append(record.getTitle())
                        .append(" /role ").append(record.getRole())
                        .append(" /tech ").append(record.getTech())
                        .append(" /from ").append(record.getFrom())
                        .append(" /to ").append(record.getTo());

                if (!record.getBullets().isEmpty()) {
                    line.append(" /bullets ");
                    for (int i = 0; i < record.getBullets().size(); i++) {
                        line.append(record.getBullets().get(i));
                        if (i < record.getBullets().size() - 1) {
                            line.append(" ;; ");
                        }
                    }
                }

                fw.write(line + "\n");
            }
            fw.close();
            logger.info("Records saved successfully");
            ui.showMessage("Records saved to file.");
        } catch (IOException e) {
            logger.severe("Failed to save records: " + e.getMessage());
            throw new ResumakeException("Failed to save records to file.");
        }
    }

    /**
     * Loads records from the provided storage file path.
     *
     * @param filepath Path to the storage file.
     * @return Loaded records as a {@link RecordList}.
     * @throws ResumakeException If file setup or read fails.
     */
    public RecordList loadFromFile(String filepath) throws ResumakeException {
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
                throw new ResumakeException("Error creating the directory.");
            }
        }

        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
                logger.fine("Created storage file: " + filepath);
            } catch (IOException e) {
                logger.severe("Error creating the file: " + e.getMessage());
                throw new ResumakeException("Error creating the file.");
            }
        }
        assert Files.exists(path) : "file should exist after path creation";

        try {
            Scanner sc = new Scanner(file);

            // Load user from first line
            if (sc.hasNextLine()) {
                String firstLine = sc.nextLine().strip();
                if(firstLine.startsWith("USER|")) {

                    String[] parts = firstLine.split("\\|");
                    if (parts.length == 4) {
                        try {
                            User.loadFrom(parts[1], Integer.parseInt(parts[2]), parts[3]);
                            logger.info("User loaded from file.");
                        } catch (NumberFormatException e) {
                            logger.warning("Invalid user data in file.");
                            User.getInstance();
                        }
                    } else {
                        logger.warning("No valid user data found in first line.");
                        User.getInstance();
                    }
                } else {
                    User.getInstance();
                    // first line is not a user line, so process it as a record
                    Record record = parseRecord(firstLine);
                    list.add(record);
                }
            } else {
                logger.warning("File is empty. No user loaded.");
                User.getInstance();
            }


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
        } catch (IOException e) {
            logger.severe("Failed to load records: " + e.getMessage());
            throw new ResumakeException("Failed to load records from file.");
        }
        logger.info("Records loaded successfully");
        ui.showMessage("Loaded records from file.");
        ui.showLine();

        return list;
    }

    /**
     * Returns the default storage file path.
     *
     * @return Default storage file path.
     */
    public static String getFilepath() {
        return filepath;
    }

    /**
     * Parses one line from storage into a record object.
     *
     * @param line Raw storage line.
     * @return Parsed record, or null if the line is invalid.
     */
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
        int bulletsIndex = args.indexOf("/bullets");

        if (roleIndex == -1 || techIndex == -1 || fromIndex == -1 || toIndex == -1) {
            logger.warning("Invalid record line (missing required field(s)): " + line);
            return null;
        }

        boolean hasBullets = bulletsIndex != -1;

        if (hasBullets) {
            if (!(roleIndex < techIndex && techIndex < fromIndex && fromIndex < toIndex && toIndex < bulletsIndex)) {
                logger.warning("Invalid record line (fields are in wrong order): " + line);
                return null;
            }
        } else {
            if (!(roleIndex < techIndex && techIndex < fromIndex && fromIndex < toIndex)) {
                logger.warning("Invalid record line (fields are in wrong order): " + line);
                return null;
            }
        }

        try {
            String title = args.substring(0, roleIndex).trim();
            String role = args.substring(roleIndex + 5, techIndex).trim();
            String tech = args.substring(techIndex + 5, fromIndex).trim();
            String fromPart = args.substring(fromIndex + 5, toIndex).trim();

            String toPart;
            String bulletsPart = "";

            if (hasBullets) {
                toPart = args.substring(toIndex + 3, bulletsIndex).trim();
                bulletsPart = args.substring(bulletsIndex + 8).trim();
            } else {
                toPart = args.substring(toIndex + 3).trim();
            }

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

            Record record;
            switch (keyword) {
            case "project":
                record = new Project(title, role, tech, from, to);
                break;
            case "experience":
                record = new Experience(title, role, tech, from, to);
                break;
            case "cca":
                record = new Cca(title, role, tech, from, to);
                break;
            default:
                logger.warning("Unknown record keyword while parsing: " + keyword);
                return null;
            }

            if (!bulletsPart.isEmpty()) {
                String[] bullets = bulletsPart.split("\\s*;;\\s*");
                for (String bullet : bullets) {
                    if (!bullet.isBlank()) {
                        record.addBullet(bullet.trim());
                    }
                }
            }

            return record;

        } catch (RuntimeException e) {
            logger.warning("Failed to parse record line: " + line + " | Reason: " + e.getMessage());
            return null;
        }
    }

    /**
     * Maps record type code to persistence keyword.
     *
     * @param recordType Record type code (for example, P, E, C).
     * @return Keyword used in file format, or null if unknown.
     */
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
