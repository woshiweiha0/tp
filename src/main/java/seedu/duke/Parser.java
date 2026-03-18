package seedu.duke;
import seedu.duke.RecordType.Record;

import java.time.DateTimeException;
import java.time.YearMonth;

import seedu.duke.RecordType.*;

public class Parser {

    public static Command parse(String userInput) {
        String trimmedInput = userInput.trim();
        Record r;

        if (trimmedInput.isEmpty()) {
            return null;
        }

        String[] split = trimmedInput.split("\\s+", 2);
        String keyword = split[0].toLowerCase();

        switch (keyword) {
        case "bye":
            return new ExitCommand();

        case "find":
            if (split.length < 2 || split[1].trim().isEmpty()) {
                return null;
            }
            return new FindCommand(split[1]);
        case "show":
            return new ShowCommand(Integer.parseInt(split[1]));
        case "list":
            return new ListCommand();

        case "project":
            r = parseProject(split);
            return new AddCommand(r);

        case "experience":
            r = parseExperience(split);
            return new AddCommand(r);

        case "cca":
            r = parseCca(split);
            return new AddCommand(r);

        default:
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