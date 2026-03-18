package seedu.duke.recordtype;

import java.time.YearMonth;

public class Cca extends Record {
    public Cca(String title, String role, String tech, YearMonth from, YearMonth to) {
        super(title,role,tech,from,to);
        recordType = "C";
    }
}
