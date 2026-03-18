package seedu.duke.recordtype;

import java.time.YearMonth;

public class Record {
    protected String title;
    protected String role;
    protected String tech;
    protected YearMonth from;
    protected YearMonth to;
    protected String recordType;

    public Record(String title, String role, String tech, YearMonth from, YearMonth to) {
        this.title = title;
        this.role = role;
        this.tech = tech;
        this.from = from;
        this.to = to;
        recordType = "R";
    }

    public boolean containsKeyword(String keyword) {
        return title.toLowerCase().contains(keyword.toLowerCase());
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.title = description;
    }

    public String getRecordType() { return recordType; }
}
