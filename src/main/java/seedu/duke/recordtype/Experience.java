package seedu.duke.recordtype;

import java.time.YearMonth;

public class Experience extends Record {
    public Experience(String title, String role, String tech, YearMonth from, YearMonth to) {
        super(title,role,tech,from,to);
        recordType = "E";
    }
}
