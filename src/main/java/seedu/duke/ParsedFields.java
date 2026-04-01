package seedu.duke;

import java.time.YearMonth;

/**
 * Represents a container for parsed fields of a timed record.
 * <p>
 * This class is used as a temporary data structure during parsing
 * to store extracted values such as title, role, technology, and dates
 * before constructing specific record objects.
 */
public class ParsedFields {
    public final String title;
    public final String role;
    public final String tech;
    public final YearMonth from;
    public final YearMonth to;

    /**
     * Constructs a ParsedFields object with the specified values.
     *
     * @param title The title of the record.
     * @param role The role associated with the record.
     * @param tech The technologies used.
     * @param from The start date.
     * @param to The end date.
     */
    ParsedFields(String title, String role, String tech, YearMonth from, YearMonth to) {
        this.title = title;
        this.role = role;
        this.tech = tech;
        this.from = from;
        this.to = to;
    }
}
