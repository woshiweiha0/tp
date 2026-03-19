package seedu.duke;

import java.time.DateTimeException;
import java.time.YearMonth;

import java.util.logging.Logger;

import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

public class Parser {

    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    public static Command parse(String userInput) {
        logger.info("Parsing input: " + userInput);

        String trimmedInput = userInput.trim();
        Record r;

        if (trimmedInput.isEmpty()) {
            return null;
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
                return null;
            }
            return new FindCommand(split[1]);

        case "show":
            if (split.length < 2) {
                return null;
            }
            try {
                logger.info("Show command detected");
                return new ShowCommand(Integer.parseInt(split[1]));
            } catch (NumberFormatException e) {
                return null;
            }
        case "list":
            logger.info("List command detected");
            return new ListCommand();

        case "project":
            if (split.length < 2) {
                return null;
            }
            logger.info("Add project command detected");
            r = parseProject(split);
            return new AddCommand(r);

        case "experience":
            if (split.length < 2) {
                return null;
            }
            logger.info("Add experience command detected");
            r = parseExperience(split);
            return new AddCommand(r);

        case "cca":
            if (split.length < 2) {
                return null;
            }
            logger.info("Add CCA command detected");
            r = parseCca(split);
            return new AddCommand(r);
            
        case "delete":
            if (split.length < 2) {
                return null;
            }
            try {
                logger.info("Delete command detected");
                return new DeleteCommand(Integer.parseInt(split[1]));
            } catch (NumberFormatException e) {
                return null;
            }

        default:
            logger.warning("Unknown command: " + keyword);
            return null;
        }
    }

    private static Project parseProject(String[] split) {
        ParsedFields fields = parseTimedRecordFields(split);
        return new Project(fields.title,fields.role,fields.tech,fields.from,fields.to);
    }

    private static Experience parseExperience(String[] split) {
        ParsedFields fields = parseTimedRecordFields(split);
        return new Experience(fields.title,fields.role,fields.tech,fields.from,fields.to);
    }

    private static Cca parseCca(String[] split) {
        ParsedFields fields = parseTimedRecordFields(split);
        return new Cca(fields.title,fields.role,fields.tech,fields.from,fields.to);
    }

    private static ParsedFields parseTimedRecordFields(String[] split) {
        logger.fine("Parsing timed record fields");

        assert split != null : "split should not be null";
        assert split.length >= 2 : "Expected command arguments after command word";

        String args = split[1].trim();

        int roleIndex = args.indexOf("/role");
        int techIndex = args.indexOf("/tech");
        int fromIndex = args.indexOf("/from");
        int toIndex = args.indexOf("/to");

        if (roleIndex == -1 || techIndex == -1 || fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException(
                    "Invalid format. Expected: \"title\" /role \"role\" /tech \"tech\" /from yyyy-MM /to yyyy-MM"
            );
        }

        if (!(roleIndex < techIndex && techIndex < fromIndex && fromIndex < toIndex)) {
            throw new IllegalArgumentException("Fields are in the wrong order.");
        }

        String titlePart = args.substring(0,roleIndex).trim();
        String rolePart = args.substring(roleIndex+5,techIndex).trim();
        String techPart = args.substring(techIndex+5, fromIndex).trim();
        String fromPart = args.substring(fromIndex+5,toIndex).trim();
        String toPart = args.substring(toIndex + 3).trim();

        logger.fine("Parsed title: " + titlePart);

        YearMonth from = parseYearMonth(fromPart, "from");
        YearMonth to = parseYearMonth(toPart, "to");

        if (to.isBefore(from)){
            throw new IllegalArgumentException("End data cannot be before start date");
        }

        return new ParsedFields(titlePart,rolePart,techPart,from,to);
    }

    private static YearMonth parseYearMonth(String input, String fieldName) {
        try {
            return YearMonth.parse(input.trim());
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(fieldName + "date Must be in yyyy-MM format");
        }
    }
}
