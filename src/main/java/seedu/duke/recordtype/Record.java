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
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }
        this.title = description.trim();
    }

    public void setFrom(YearMonth from) {
        if (from == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        if (this.to != null && from.isAfter(this.to)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.from = from;
    }

    public void setTo(YearMonth to) {
        if (to == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
        if (this.from != null && to.isBefore(this.from)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        this.to = to;
    }

    public String getRecordType() {
        return recordType;
    }

    public void addBullet(String bullet) {
        assert bullet != null && !bullet.isBlank()
                : "Bullet must not be null or blank";
        bullets.add(bullet);
    }

    public String deleteBullet(int bulletIndex) {
        if (bulletIndex < 0 || bulletIndex >= bullets.size()) {
            throw new IndexOutOfBoundsException("Bullet index is out of range.");
        }
        return bullets.remove(bulletIndex);
    }

    public void moveBullet(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= bullets.size()) {
            throw new IndexOutOfBoundsException("Source bullet index is out of range.");
        }

        if (toIndex < 0 || toIndex >= bullets.size()) {
            throw new IndexOutOfBoundsException("Target bullet index is out of range.");
        }

        if (fromIndex == toIndex) {
            return;
        }

        String bulletToMove = bullets.remove(fromIndex);
        bullets.add(toIndex, bulletToMove);
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
