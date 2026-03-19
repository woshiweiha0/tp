package seedu.duke.recordtype;

import java.time.YearMonth;

public class Project extends Record {

    public Project(String title, String role, String tech, YearMonth from, YearMonth to) {
        super(title,role,tech,from,to);
        recordType = "P";
    }
}
