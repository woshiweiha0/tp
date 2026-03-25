package seedu.duke.recordtype;

import java.time.YearMonth;
import java.util.ArrayList;

public class Record {
    protected String title;
    protected String role;
    protected String tech;
    protected YearMonth from;
    protected YearMonth to;
    protected String recordType;
    protected ArrayList<String> bullets;

    public Record(String title, String role, String tech, YearMonth from, YearMonth to) {
        assert title != null && !title.isEmpty()
                : "Title must not be null or empty";
        assert from != null;
        assert to != null;
        assert !to.isBefore(from);

        this.title = title;
        this.role = role;
        this.tech = tech;
        this.from = from;
        this.to = to;
        this.bullets = new ArrayList<>();
        recordType = "R";
    }

    public boolean containsKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();

        return title.toLowerCase().contains(lowerKeyword)
                || role.toLowerCase().contains(lowerKeyword)
                || tech.toLowerCase().contains(lowerKeyword)
                || from.toString().toLowerCase().contains(lowerKeyword)
                || to.toString().toLowerCase().contains(lowerKeyword);
    }

    public String getTitle() {
        return title;
    }

    public String getRole() {
        return role;
    }

    public String getTech() {
        return tech;
    }

    public YearMonth getFrom() {
        return from;
    }

    public YearMonth getTo() {
        return to;
    }

    public void setDescription(String description) {
        this.title = description;
    }

    public String getRecordType() {
        return recordType;
    }

    public void addBullet(String bullet) {
        assert bullet != null && !bullet.isBlank()
                : "Bullet must not be null or blank";
        bullets.add(bullet);
    }

    public ArrayList<String> getBullets() {
        return bullets;
    }

    @Override
    public String toString() {
        return "[" + recordType + "] " + title
                + " | role: " + role
                + " | tech: " + tech
                + " | from: " + from
                + " | to: " + to;
    }
}
