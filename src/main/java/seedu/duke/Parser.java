package seedu.duke;

import java.time.DateTimeException;
import java.time.YearMonth;

import java.util.logging.Logger;

import seedu.duke.commands.EditBulletCommand;
import seedu.duke.commands.AddBulletCommand;
import seedu.duke.commands.GenerateCommand;
import seedu.duke.commands.MoveBulletCommand;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.FindCommand;
import seedu.duke.commands.FindBulletCommand;
import seedu.duke.commands.ListCommand;
import seedu.duke.commands.ShowCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.commands.SortCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

public class Parser {

    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * Parses an edit command string into an {@code EditCommand}
     *
     * @param args The argument string following the "edit" keyword.
     * @return An {@code EditCommand} if parsing is successful or {@code null} if invalid.
     */
    private static Command parseEditCommand(String args) throws ResumakeException{
        logger.info("Edit command detected");
        logger.fine(() -> "Parsing edit command args: " + args);

        assert args != null : "Edit command arguments should not be null";

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("Edit command failed: no arguments provided");
            throw new ResumakeException("Command cannot be empty.");
        }

        String[] editParts = trimmedArgs.split("\\s+", 2);
        if (editParts.length < 2) {
            logger.warning("Edit command failed: missing index or fields");
            throw new ResumakeException("missing index or fields");
        }

        try {
            int index = Integer.parseInt(editParts[0]) - 1;
            String fields = editParts[1].trim();

            if (index < 0) {
                logger.warning("Edit command failed: record index must be positive");
                throw new ResumakeException("record index must be positive");
            }

            if (fields.isEmpty()) {
                logger.warning("Edit command failed: no fields provided");
                throw new ResumakeException("Edit command failed: no fields provided");
            }

            int roleIndex = fields.indexOf("/role");
            int techIndex = fields.indexOf("/tech");
            int fromIndex = fields.indexOf("/from");
            int toIndex = fields.indexOf("/to");

            String newTitle = null;
            String newRole = null;
            String newTech = null;
            YearMonth newFrom = null;
            YearMonth newTo = null;

            int firstFlagIndex = fields.length();

            if (roleIndex != -1 && roleIndex < firstFlagIndex) {
                firstFlagIndex = roleIndex;
            }
            if (techIndex != -1 && techIndex < firstFlagIndex) {
                firstFlagIndex = techIndex;
            }
            if (fromIndex != -1 && fromIndex < firstFlagIndex) {
                firstFlagIndex = fromIndex;
            }
            if (toIndex != -1 && toIndex < firstFlagIndex) {
                firstFlagIndex = toIndex;
            }

            String titlePart = fields.substring(0, firstFlagIndex).trim();
            if (!titlePart.isEmpty()) {
                newTitle = titlePart;
            }

            if (roleIndex != -1) {
                int roleEnd = fields.length();
                if (techIndex != -1 && techIndex > roleIndex && techIndex < roleEnd) {
                    roleEnd = techIndex;
                }
                if (fromIndex != -1 && fromIndex > roleIndex && fromIndex < roleEnd) {
                    roleEnd = fromIndex;
                }
                if (toIndex != -1 && toIndex > roleIndex && toIndex < roleEnd) {
                    roleEnd = toIndex;
                }
                newRole = fields.substring(roleIndex + 5, roleEnd).trim();
                if (newRole.isEmpty()) {
                    logger.warning("Edit command failed: /role provided but value is blank");
                    throw new ResumakeException("/role provided but value is blank");
                }
            }

            if (techIndex != -1) {
                int techEnd = fields.length();
                if (roleIndex != -1 && roleIndex > techIndex && roleIndex < techEnd) {
                    techEnd = roleIndex;
                }
                if (fromIndex != -1 && fromIndex > techIndex && fromIndex < techEnd) {
                    techEnd = fromIndex;
                }
                if (toIndex != -1 && toIndex > techIndex && toIndex < techEnd) {
                    techEnd = toIndex;
                }
                newTech = fields.substring(techIndex + 5, techEnd).trim();
                if (newTech.isEmpty()) {
                    logger.warning("Edit command failed: /tech provided but value is blank");
                    throw new ResumakeException("/tech provided but value is blank");
                }
            }

            if (fromIndex != -1) {
                int fromEnd = fields.length();
                if (roleIndex != -1 && roleIndex > fromIndex && roleIndex < fromEnd) {
                    fromEnd = roleIndex;
                }
                if (techIndex != -1 && techIndex > fromIndex && techIndex < fromEnd) {
                    fromEnd = techIndex;
                }
                if (toIndex != -1 && toIndex > fromIndex && toIndex < fromEnd) {
                    fromEnd = toIndex;
                }
                String fromPart = fields.substring(fromIndex + 5, fromEnd).trim();
                if (fromPart.isEmpty()) {
                    logger.warning("Edit command failed: /from provided but value is blank");
                    throw new ResumakeException("/from provided but value is blank");
                }
                newFrom = parseYearMonth(fromPart, "from");
            }

            if (toIndex != -1) {
                int toEnd = fields.length();
                if (roleIndex != -1 && roleIndex > toIndex && roleIndex < toEnd) {
                    toEnd = roleIndex;
                }
                if (techIndex != -1 && techIndex > toIndex && techIndex < toEnd) {
                    toEnd = techIndex;
                }
                if (fromIndex != -1 && fromIndex > toIndex && fromIndex < toEnd) {
                    toEnd = fromIndex;
                }
                String toPart = fields.substring(toIndex + 3, toEnd).trim();
                if (toPart.isEmpty()) {
                    logger.warning("Edit command failed: /to provided but value is blank");
                    throw new ResumakeException("/to provided but value is blank");
                }
                newTo = parseYearMonth(toPart, "to");
            }

            if (newTitle == null && newRole == null && newTech == null
                    && newFrom == null && newTo == null) {
                logger.warning("Edit command failed: no valid fields found");
                throw new ResumakeException("no valid fields found");
            }

            YearMonth finalFrom = newFrom;
            YearMonth finalTo = newTo;
            if (finalFrom != null && finalTo != null && finalTo.isBefore(finalFrom)) {
                logger.warning("Edit command failed: end date is before start date");
                throw new ResumakeException("End date cannot be before start date");
            }

            logger.fine("Parsed edit fields: index=" + index
                    + ", title=" + newTitle
                    + ", role=" + newRole
                    + ", tech=" + newTech
                    + ", from=" + newFrom
                    + ", to=" + newTo);

            return new EditCommand(index, newTitle, newRole, newTech, newFrom, newTo);

        } catch (NumberFormatException e) {
            logger.warning("Edit command failed: invalid record index");
            throw new ResumakeException("invalid record index");
        } catch (IllegalArgumentException e) {
            logger.warning(() -> "Edit command failed: " + e.getMessage());
            throw new ResumakeException(e.getMessage());
        }
    }

    /**
     * Parses a user input string into a corresponding {@code Command}.
     *
     * @param userInput The raw input string entered by the user.
     * @return A {@code Command} object representing the user request,
     *         or {@code null} if the input is invalid.
     * @throws ResumakeException If a parsing-related error occurs.
     */
    public static Command parse(String userInput) throws ResumakeException {
        logger.info("Parsing input: " + userInput);

        String trimmedInput = userInput.trim();
        Record r;

        if (trimmedInput.isEmpty()) {
            throw new ResumakeException("Command cannot be empty");
        }

        String[] split = trimmedInput.split("\\s+", 2);
        String keyword = split[0].toLowerCase();

        logger.fine("Detected command keyword: " + keyword);

        switch (keyword) {
        case "bye":
            logger.info("Exit command detected");
            return new ExitCommand();

        case "find":
            if (split.length < 2 || split[1].trim().isEmpty()) {
                throw new ResumakeException("Please follow the correct format");
            }
            return new FindCommand(split[1]);

        case "findbullet":
            if (split.length < 2 || split[1].trim().isEmpty()) {
                throw new ResumakeException("Please follow the correct format");
            }
            return new FindBulletCommand(split[1]);

        case "show":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            try {
                logger.info("Show command detected");
                return new ShowCommand(Integer.parseInt(split[1]));
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format");
            }

        case "list":
            logger.info("List command detected");

            if (split.length == 1){
                return new ListCommand();
            } else {
                return new ListCommand(split[1]);
            }

        case "project":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            logger.info("Add project command detected");
            r = parseProject(split);
            return new AddCommand(r);

        case "experience":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            logger.info("Add experience command detected");
            r = parseExperience(split);
            return new AddCommand(r);

        case "cca":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            logger.info("Add CCA command detected");
            r = parseCca(split);
            return new AddCommand(r);

        case "delete":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            try {
                logger.info("Delete command detected");
                return new DeleteCommand(Integer.parseInt(split[1]));
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format");
            }
        case "deletebullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            logger.info("Delete bullet command detected");
            String[] deleteBulletParts = split[1].trim().split("\\s+");
            if (deleteBulletParts.length != 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            try {
                int recordIndex = Integer.parseInt(deleteBulletParts[0]);
                int bulletIndex = Integer.parseInt(deleteBulletParts[1]);
                return new DeleteCommand(recordIndex, bulletIndex);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format");
            }

        case "addbullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            logger.info("Bullet command detected");
            String[] parts = split[1].split("\\s+", 2);
            if (parts.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            try {
                int index = Integer.parseInt(parts[0]) - 1;
                String bulletPart = parts[1].trim();
                if (!bulletPart.startsWith("/")) {
                    throw new ResumakeException("Bullet must start with /");
                }
                String bullet = bulletPart.substring(1).trim();
                return new AddBulletCommand(index, bullet);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format");
            }

        case "edit":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            return parseEditCommand(split[1]);

        case "movebullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }
            logger.info("Move bullet command detected");

            String[] moveParts = split[1].trim().split("\\s+");
            if (moveParts.length != 3) {
                throw new ResumakeException("Please follow the correct format");
            }

            try {
                int recordIndex = Integer.parseInt(moveParts[0]) - 1;
                int fromBulletIndex = Integer.parseInt(moveParts[1]) - 1;
                int toBulletIndex = Integer.parseInt(moveParts[2]) - 1;

                return new MoveBulletCommand(recordIndex, fromBulletIndex, toBulletIndex);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format");
            }

        case "editbullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format");
            }

            String[] editBulletParts = split[1].trim().split("\\s+", 3);
            if (editBulletParts.length < 3) {
                throw new ResumakeException("Please follow the correct format");
            }

            try {
                int recordIndex = Integer.parseInt(editBulletParts[0]);
                int bulletIndex = Integer.parseInt(editBulletParts[1]);
                String bulletPart = editBulletParts[2].trim();

                if (!bulletPart.startsWith("/")) {
                    throw new ResumakeException("Bullet must start with /");
                }

                String newBullet = bulletPart.substring(1).trim();
                return new EditBulletCommand(recordIndex, bulletIndex, newBullet);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format");
            }

        case "sort":
            return new SortCommand();

        case "generate":
            return new GenerateCommand();

        default:
            logger.warning("Unknown command: " + keyword);
            throw new ResumakeException("Please use the correct command");
        }
    }

    /**
     * Parses input arguments into a {@code Project} record
     *
     * @param split The split user input containing command and arguments
     * @return A {@code Project} object
     */
    private static Project parseProject(String[] split) throws ResumakeException{
        ParsedFields fields = parseTimedRecordFields(split);
        return new Project(fields.title, fields.role, fields.tech, fields.from, fields.to);
    }

    /**
     * Parses input arguments into a {@code Experience} record
     *
     * @param split The split user input containing command and arguments.
     * @return An {@code Experience} object.
     */
    private static Experience parseExperience(String[] split) throws ResumakeException{
        ParsedFields fields = parseTimedRecordFields(split);
        return new Experience(fields.title, fields.role, fields.tech, fields.from, fields.to);
    }

    /**
     * Parses input arguments into a {@code Cca} record.
     *
     * @param split The split user input containing command and arguments
     * @return A {@code Cca} object.
     */
    private static Cca parseCca(String[] split) throws ResumakeException {
        ParsedFields fields = parseTimedRecordFields(split);
        return new Cca(fields.title, fields.role, fields.tech, fields.from, fields.to);
    }

    /**
     * Parses common fields for time-based records such as project, experience and CCA
     *
     * Expected format:
     * {@code "title" /role "role" /tech "tech" /from yyyy-MM /to yyyy-MM}
     *
     * @param split The split user input containing command and arguments.
     * @return A {@code ParsedFields} object containing extracted values.
     * @throws IllegalArgumentException If the input format is invalid.
     */
    private static ParsedFields parseTimedRecordFields(String[] split) throws ResumakeException{
        logger.fine("Parsing timed record fields");

        assert split != null : "split should not be null";
        assert split.length >= 2 : "Expected command arguments after command word";

        String args = split[1].trim();

        int roleIndex = args.indexOf("/role");
        int techIndex = args.indexOf("/tech");
        int fromIndex = args.indexOf("/from");
        int toIndex = args.indexOf("/to");

        if (roleIndex == -1 || techIndex == -1 || fromIndex == -1 || toIndex == -1) {
            throw new ResumakeException(
                    "Invalid format. Expected: \"title\" /role \"role\" /tech \"tech\" "
                            + "/from yyyy-MM /to yyyy-MM"
            );
        }

        if (!(roleIndex < techIndex && techIndex < fromIndex && fromIndex < toIndex)) {
            throw new ResumakeException("Fields are in the wrong order.");
        }

        String titlePart = args.substring(0, roleIndex).trim();
        String rolePart = args.substring(roleIndex + 5, techIndex).trim();
        String techPart = args.substring(techIndex + 5, fromIndex).trim();
        String fromPart = args.substring(fromIndex + 5, toIndex).trim();
        String toPart = args.substring(toIndex + 3).trim();

        logger.fine("Parsed title: " + titlePart);

        YearMonth from = parseYearMonth(fromPart, "from");
        YearMonth to = parseYearMonth(toPart, "to");

        if (to.isBefore(from)) {
            throw new ResumakeException("End data cannot be before start date");
        }

        return new ParsedFields(titlePart, rolePart, techPart, from, to);
    }

    /**
     * Parses a string into a {@code YearMonth} object.
     *
     * @param input The date string in yyyy-MM format.
     * @param fieldName The name of the field being parsed (for error messages)
     * @return A {@code YearMonth} object
     * @throws IllegalArgumentException If the input format in invalid.
     */
    private static YearMonth parseYearMonth(String input, String fieldName) throws ResumakeException {
        try {
            return YearMonth.parse(input.trim());
        } catch (DateTimeException e) {
            throw new ResumakeException(fieldName + "date must be in yyyy-MM format");
        }
    }
}
