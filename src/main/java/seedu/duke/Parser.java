package seedu.duke;

import java.time.DateTimeException;
import java.time.YearMonth;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.duke.commands.EditBulletCommand;
import seedu.duke.commands.AddBulletCommand;
import seedu.duke.commands.EditUserCommand;
import seedu.duke.commands.GenerateCommand;
import seedu.duke.commands.MoveBulletCommand;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.FindCommand;
import seedu.duke.commands.FindBulletCommand;
import seedu.duke.commands.HelpCommand;
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
    private static final Pattern FIELD_TOKEN_PATTERN = Pattern.compile("(?:^|\\s)(/\\S+)");

    // Field flag constants
    private static final String ROLE_FLAG = "/role";
    private static final String TECH_FLAG = "/tech";
    private static final String FROM_FLAG = "/from";
    private static final String TO_FLAG = "/to";

    private static final int ROLE_FLAG_LENGTH = ROLE_FLAG.length();
    private static final int TECH_FLAG_LENGTH = TECH_FLAG.length();
    private static final int FROM_FLAG_LENGTH = FROM_FLAG.length();
    private static final int TO_FLAG_LENGTH = TO_FLAG.length();

    private static final class FieldIndices {
        private final int roleIndex;
        private final int techIndex;
        private final int fromIndex;
        private final int toIndex;

        private FieldIndices(int roleIndex, int techIndex, int fromIndex, int toIndex) {
            this.roleIndex = roleIndex;
            this.techIndex = techIndex;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }
    }

    /**
     * Parses an edit command string into an {@code EditCommand}
     *
     * @param args The argument string following the "edit" keyword.
     * @return An {@code EditCommand} if parsing is successful or {@code null} if
     *         invalid.
     */
    private static Command parseEditCommand(String args, Ui ui) throws ResumakeException {
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
            throw new ResumakeException("Please follow the correct format.");
        }

        try {
            int index = Integer.parseInt(editParts[0]) - 1;
            String fields = editParts[1].trim();

            if (index < 0) {
                logger.warning("Edit command failed: record index must be positive");
                throw new ResumakeException("Invalid record index.");
            }

            if (fields.isEmpty()) {
                logger.warning("Edit command failed: no fields provided");
                throw new ResumakeException("Edit command failed: no fields provided.");
            }

            FieldIndices fieldIndices = findFieldIndices(fields, true);
            int roleIndex = fieldIndices.roleIndex;
            int techIndex = fieldIndices.techIndex;
            int fromIndex = fieldIndices.fromIndex;
            int toIndex = fieldIndices.toIndex;

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
                // Check if title is only forward slashes or other invalid patterns
                if (titlePart.matches("^/+$")) {
                    logger.warning("Edit command failed: title cannot be only forward slashes");
                    throw new ResumakeException(
                            "Please use the following format \"edit RECORD_INDEX [NEW_TITLE] "
                                    + "[/role NEW_ROLE] [/tech NEW_TECH] [/from YYYY-MM] [/to YYYY-MM]\". "
                                    + "Title provided is invalid.");
                }
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
                newRole = fields.substring(roleIndex + ROLE_FLAG_LENGTH, roleEnd).trim();
                if (newRole.isEmpty()) {
                    logger.warning("Edit command failed: /role provided but value is blank");
                    throw new ResumakeException("/role provided but value is blank.");
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
                newTech = fields.substring(techIndex + TECH_FLAG_LENGTH, techEnd).trim();
                if (newTech.isEmpty()) {
                    logger.warning("Edit command failed: /tech provided but value is blank");
                    throw new ResumakeException("/tech provided but value is blank.");
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
                String fromPart = fields.substring(fromIndex + FROM_FLAG_LENGTH, fromEnd).trim();
                if (fromPart.isEmpty()) {
                    logger.warning("Edit command failed: /from provided but value is blank");
                    throw new ResumakeException("/from provided but value is blank.");
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
                String toPart = fields.substring(toIndex + TO_FLAG_LENGTH, toEnd).trim();
                if (toPart.isEmpty()) {
                    logger.warning("Edit command failed: /to provided but value is blank");
                    throw new ResumakeException("/to provided but value is blank.");
                }
                newTo = parseYearMonth(toPart, "to");
            }

            if (newTitle == null && newRole == null && newTech == null
                    && newFrom == null && newTo == null) {
                logger.warning("Edit command failed: no valid fields found");
                throw new ResumakeException(
                        "Please use the following format \"edit RECORD_INDEX [NEW_TITLE] "
                                + "[/role NEW_ROLE] [/tech NEW_TECH] [/from YYYY-MM] [/to YYYY-MM]\". "
                                + "At least one field must be provided.");
            }

            YearMonth finalFrom = newFrom;
            YearMonth finalTo = newTo;
            if (finalFrom != null && finalTo != null && finalTo.isBefore(finalFrom)) {
                logger.warning("Edit command failed: end date is before start date");
                throw new ResumakeException("End date cannot be before start date.");
            }

            logger.fine("Parsed edit fields: index=" + index
                    + ", title=" + newTitle
                    + ", role=" + newRole
                    + ", tech=" + newTech
                    + ", from=" + newFrom
                    + ", to=" + newTo);

            return new EditCommand(index, newTitle, newRole, newTech, newFrom, newTo, ui);

        } catch (NumberFormatException e) {
            logger.warning("Edit command failed: invalid record index");
            throw new ResumakeException("Invalid record index.");
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
        return parse(userInput, new Ui());
    }

    public static Command parse(String userInput, Ui ui) throws ResumakeException {
        logger.info("Parsing input: " + userInput);

        String trimmedInput = userInput.trim();
        Ui effectiveUi = ui == null ? new Ui() : ui;
        Record r;

        if (trimmedInput.isEmpty()) {
            throw new ResumakeException("Command cannot be empty.");
        }

        String[] split = trimmedInput.split("\\s+", 2);
        String keyword = split[0].toLowerCase();

        logger.fine("Detected command keyword: " + keyword);

        switch (keyword) {
        case "bye":
            logger.info("Exit command detected");
            return new ExitCommand(effectiveUi);

        case "find":
            if (split.length < 2 || split[1].trim().isEmpty()) {
                throw new ResumakeException("Please follow the correct format.");
            }
            return new FindCommand(split[1], effectiveUi);

        case "findbullet":
            if (split.length < 2 || split[1].trim().isEmpty()) {
                throw new ResumakeException("Please follow the correct format.");
            }
            return new FindBulletCommand(split[1], effectiveUi);

        case "show":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            try {
                logger.info("Show command detected");
                int userIndex = Integer.parseInt(split[1]);
                if (userIndex <= 0) {
                    throw new ResumakeException("Record index must be positive.");
                }
                return new ShowCommand(userIndex, effectiveUi);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format.");
            }

        case "list":
            logger.info("List command detected");

            if (split.length == 1) {
                return new ListCommand(effectiveUi);
            } else {
                return new ListCommand(split[1], effectiveUi);
            }

        case "help":
            if (split.length > 1 && !split[1].trim().isEmpty()) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Help command detected");
            return new HelpCommand(effectiveUi);

        case "project":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Add project command detected");
            r = parseProject(split);
            return new AddCommand(r, effectiveUi);

        case "experience":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Add experience command detected");
            r = parseExperience(split);
            return new AddCommand(r, effectiveUi);

        case "cca":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Add CCA command detected");
            r = parseCca(split);
            return new AddCommand(r, effectiveUi);

        case "delete":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            try {
                logger.info("Delete command detected");
                return new DeleteCommand(Integer.parseInt(split[1]));
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format.");
            }
        case "deletebullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Delete bullet command detected");
            String[] deleteBulletParts = split[1].trim().split("\\s+");
            if (deleteBulletParts.length != 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            try {
                int recordIndex = Integer.parseInt(deleteBulletParts[0]);
                int bulletIndex = Integer.parseInt(deleteBulletParts[1]);
                return new DeleteCommand(recordIndex, bulletIndex);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format.");
            }

        case "addbullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Bullet command detected");
            String[] parts = split[1].split("\\s+", 2);
            if (parts.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            try {
                int recordIndex = Integer.parseInt(parts[0]);
                if (recordIndex <= 0) {
                    throw new ResumakeException("Record index must be positive (1-based).");
                }
                int index = recordIndex - 1;
                String bulletPart = parts[1].trim();
                if (!bulletPart.startsWith("/")) {
                    throw new ResumakeException("Bullet must start with /.");
                }
                String bullet = bulletPart.substring(1).trim();
                if (bullet.isEmpty()) {
                    throw new ResumakeException("Bullet text cannot be blank.");
                }
                return new AddBulletCommand(index, bullet, effectiveUi);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format.");
            } catch (ResumakeException e) {
                throw e;
            }

        case "edit":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            return parseEditCommand(split[1], effectiveUi);

        case "movebullet":
            if (split.length < 2) {
                throw new ResumakeException("Please follow the correct format.");
            }
            logger.info("Move bullet command detected");

            String[] moveParts = split[1].trim().split("\\s+");
            if (moveParts.length != 3) {
                throw new ResumakeException("Please follow the correct format.");
            }

            try {
                int recordIndex = Integer.parseInt(moveParts[0]) - 1;
                int fromBulletIndex = Integer.parseInt(moveParts[1]) - 1;
                int toBulletIndex = Integer.parseInt(moveParts[2]) - 1;

                return new MoveBulletCommand(recordIndex, fromBulletIndex, toBulletIndex, effectiveUi);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format.");
            }

        case "editbullet":
            if (split.length < 2) {
                throw new ResumakeException(
                        "Please use the following format "
                                + "\"editbullet RECORD_INDEX BULLET_INDEX / NEW_BULLET_TEXT\".");
            }

            String[] editBulletParts = split[1].trim().split("\\s+", 3);
            if (editBulletParts.length < 3) {
                throw new ResumakeException(
                        "Please use the following format "
                                + "\"editbullet RECORD_INDEX BULLET_INDEX / NEW_BULLET_TEXT\".");
            }

            try {
                int recordIndex = Integer.parseInt(editBulletParts[0]);
                int bulletIndex = Integer.parseInt(editBulletParts[1]);
                String bulletPart = editBulletParts[2].trim();

                if (!bulletPart.startsWith("/")) {
                    throw new ResumakeException(
                            "Please use the following format "
                                    + "\"editbullet RECORD_INDEX BULLET_INDEX / NEW_BULLET_TEXT\".");
                }

                String newBullet = bulletPart.substring(1).trim();
                return new EditBulletCommand(recordIndex, bulletIndex, newBullet, effectiveUi);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please follow the correct format.");
            } catch (IllegalArgumentException e) {
                throw new ResumakeException(e.getMessage());
            }

        case "sort":
            return new SortCommand(effectiveUi);

        case "generate":
            return new GenerateCommand(effectiveUi);

        case "edituser":
            if (split.length < 2 || split[1].trim().isEmpty()) {
                throw new ResumakeException("Please use the format: edituser FIELD.");
            }
            String field = split[1].trim(); // "name", "number", or "email"
            return new EditUserCommand(field, effectiveUi);

        default:
            logger.warning("Unknown command: " + keyword);
            throw new ResumakeException("Please use the correct command.");
        }
    }

    /**
     * Parses input arguments into a {@code Project} record
     *
     * @param split The split user input containing command and arguments
     * @return A {@code Project} object
     */
    private static Project parseProject(String[] split) throws ResumakeException {
        ParsedFields fields = parseTimedRecordFields(split);
        return new Project(fields.title, fields.role, fields.tech, fields.from, fields.to);
    }

    /**
     * Parses input arguments into a {@code Experience} record
     *
     * @param split The split user input containing command and arguments.
     * @return An {@code Experience} object.
     */
    private static Experience parseExperience(String[] split) throws ResumakeException {
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
     * Parses common fields for time-based records such as project, experience and
     * CCA
     *
     * Expected format:
     * {@code "title" /role "role" /tech "tech" /from yyyy-MM /to yyyy-MM}
     *
     * @param split The split user input containing command and arguments.
     * @return A {@code ParsedFields} object containing extracted values.
     * @throws IllegalArgumentException If the input format is invalid.
     */
    private static ParsedFields parseTimedRecordFields(String[] split) throws ResumakeException {
        logger.fine("Parsing timed record fields");

        assert split != null : "split should not be null";
        assert split.length >= 2 : "Expected command arguments after command word";

        String args = split[1].trim();
        FieldIndices fieldIndices = findFieldIndices(args, false);
        int roleIndex = fieldIndices.roleIndex;
        int techIndex = fieldIndices.techIndex;
        int fromIndex = fieldIndices.fromIndex;
        int toIndex = fieldIndices.toIndex;

        if (roleIndex == -1 || techIndex == -1 || fromIndex == -1 || toIndex == -1) {
            throw new ResumakeException(
                    "Invalid format. Expected: \"title\" /role \"role\" /tech \"tech\" "
                            + "/from yyyy-MM /to yyyy-MM.");
        }

        if (!(roleIndex < techIndex && techIndex < fromIndex && fromIndex < toIndex)) {
            throw new ResumakeException("Fields are in the wrong order.");
        }

        String titlePart = args.substring(0, roleIndex).trim();
        String rolePart = args.substring(roleIndex + ROLE_FLAG_LENGTH, techIndex).trim();
        String techPart = args.substring(techIndex + TECH_FLAG_LENGTH, fromIndex).trim();
        String fromPart = args.substring(fromIndex + FROM_FLAG_LENGTH, toIndex).trim();
        String toPart = args.substring(toIndex + TO_FLAG_LENGTH).trim();

        if (titlePart.isEmpty() || rolePart.isEmpty() || techPart.isEmpty()) {
            throw new ResumakeException("Title, role, and tech cannot be empty.");
        }

        logger.fine("Parsed title: " + titlePart);

        YearMonth from = parseYearMonth(fromPart, "from");
        YearMonth to = parseYearMonth(toPart, "to");

        if (to.isBefore(from)) {
            throw new ResumakeException("End date cannot be before start date.");
        }

        return new ParsedFields(titlePart, rolePart, techPart, from, to);
    }

    private static FieldIndices findFieldIndices(String args, boolean isEditCommand) throws ResumakeException {
        int roleIndex = -1;
        int techIndex = -1;
        int fromIndex = -1;
        int toIndex = -1;

        Matcher matcher = FIELD_TOKEN_PATTERN.matcher(args);
        boolean roleSeen = false;
        boolean techSeen = false;
        boolean fromSeen = false;
        boolean toSeen = false;

        while (matcher.find()) {
            String fieldToken = matcher.group(1);
            int tokenIndex = matcher.start(1);

            switch (fieldToken) {
            case ROLE_FLAG:
                if (roleIndex != -1) {
                    throw new ResumakeException("Duplicate field \"" + fieldToken + "\" is not allowed.");
                }
                roleIndex = tokenIndex;
                break;
            case TECH_FLAG:
                if (techIndex != -1) {
                    throw new ResumakeException("Duplicate field \"" + fieldToken + "\" is not allowed.");
                }
                techIndex = tokenIndex;
                break;
            case FROM_FLAG:
                if (fromIndex != -1) {
                    throw new ResumakeException("Duplicate field \"" + fieldToken + "\" is not allowed.");
                }
                fromIndex = tokenIndex;
                break;
            case TO_FLAG:
                if (toIndex != -1) {
                    throw new ResumakeException("Duplicate field \"" + fieldToken + "\" is not allowed.");
                }
                toIndex = tokenIndex;
                break;
            default:
                if (isEditCommand) {
                    throw new ResumakeException("\"" + fieldToken
                            + "\" is not a valid field. Please use the following format "
                            + "\"edit RECORD_INDEX [NEW_TITLE] [/role NEW_ROLE] [/tech NEW_TECH] "
                            + "[/from YYYY-MM] [/to YYYY-MM]\".");
                }
                throw new ResumakeException(
                        "\"" + fieldToken + "\" is not a valid field. "
                                + "Please use /role, /tech, /from, and /to only.");
            }
        }

        return new FieldIndices(roleIndex, techIndex, fromIndex, toIndex);
    }

    private static int findFieldIndex(String args, String field) {
        Matcher matcher = FIELD_TOKEN_PATTERN.matcher(args);
        while (matcher.find()) {
            if (matcher.group(1).equals(field)) {
                return matcher.start(1);
            }
        }
        return -1;
    }

    /**
     * Parses a string into a {@code YearMonth} object.
     *
     * @param input     The date string in yyyy-MM format.
     * @param fieldName The name of the field being parsed (for error messages)
     * @return A {@code YearMonth} object
     * @throws IllegalArgumentException If the input format in invalid.
     */
    private static YearMonth parseYearMonth(String input, String fieldName) throws ResumakeException {
        try {
            return YearMonth.parse(input.trim());
        } catch (DateTimeException e) {
            throw new ResumakeException(fieldName + " date must be in yyyy-MM format.");
        }
    }
}
