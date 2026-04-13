package seedu.duke.recordtype;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.duke.User;
import seedu.duke.exceptions.ResumakeException;

public class Record {
    private static final Logger logger = Logger.getLogger(Record.class.getName());

    protected String title;
    protected String role;
    protected String tech;
    protected YearMonth from;
    protected YearMonth to;
    protected String recordType;
    protected ArrayList<String> bullets;

    public Record(String title, String role, String tech, YearMonth from, YearMonth to) {
        assert title != null && !title.isBlank() : "Title must not be null or blank";
        assert role != null && !role.isBlank() : "Role must not be null or blank";
        assert tech != null && !tech.isBlank() : "Tech must not be null or blank";
        assert from != null : "From date must not be null";
        assert to != null : "To date must not be null";
        assert !to.isBefore(from) : "End date must not be before start date";

        this.title = title.trim();
        this.role = role.trim();
        this.tech = tech.trim();
        this.from = from;
        this.to = to;
        this.bullets = new ArrayList<>();
        this.recordType = "R";

        assert this.bullets != null : "Bullets list should be initialized";

        logger.info("Record created: " + this.title);
    }

    public boolean containsKeyword(String keyword) {
        assert keyword != null : "Keyword should not be null";
        assert title != null : "Title should not be null";
        assert role != null : "Role should not be null";
        assert tech != null : "Tech should not be null";
        assert from != null : "From date should not be null";
        assert to != null : "To date should not be null";

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
        logger.fine("Attempting to set description/title for record: " + title);

        if (description == null || description.isBlank()) {
            logger.warning("setDescription failed: description is blank");
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        this.title = description.trim();
        logger.info("Description/title updated successfully for record: " + this.title);
    }

    public void setTitle(String title) {
        logger.fine("Attempting to update title for record: " + this.title);

        if (title == null || title.isBlank()) {
            logger.warning("setTitle failed: title is blank");
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        this.title = title.trim();
        logger.info("Title updated successfully to: " + this.title);
    }

    public void setFrom(YearMonth from) {
        logger.fine("Attempting to update start date for record: " + title);

        if (from == null) {
            logger.warning("setFrom failed: start date is null");
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        if (this.to != null && from.isAfter(this.to)) {
            logger.warning("setFrom failed: start date is after end date");
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        this.from = from;
        logger.info("Start date updated successfully to: " + this.from);
    }

    public void setTo(YearMonth to) {
        logger.fine("Attempting to update end date for record: " + title);

        if (to == null) {
            logger.warning("setTo failed: end date is null");
            throw new IllegalArgumentException("End date cannot be null.");
        }
        if (this.from != null && to.isBefore(this.from)) {
            logger.warning("setTo failed: end date is before start date");
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        this.to = to;
        logger.info("End date updated successfully to: " + this.to);
    }

    public void setRole(String role) {
        logger.fine("Attempting to update role for record: " + title);

        if (role == null || role.isBlank()) {
            logger.warning("setRole failed: role is blank");
            throw new IllegalArgumentException("Role cannot be blank.");
        }

        this.role = role.trim();
        logger.info("Role updated successfully to: " + this.role);
    }

    public void setTech(String tech) {
        logger.fine("Attempting to update tech for record: " + title);

        if (tech == null || tech.isBlank()) {
            logger.warning("setTech failed: tech is blank");
            throw new IllegalArgumentException("Tech cannot be blank.");
        }
        User user = User.getInstance();
        logger.info("Removing Skill: " + this.tech);
        user.removeSkills(this.tech);
        this.tech = tech.trim();
        logger.info("Adding Skill: " + this.tech);
        user.addSkills(this.tech);
        logger.info("Tech updated successfully to: " + this.tech);
    }

    public String getRecordType() {
        return recordType;
    }

    public void addBullet(String bullet) throws ResumakeException {
        assert bullets != null : "Bullets list should not be null";

        logger.fine("Attempting to add bullet to record: " + title);

        if (bullet == null || bullet.isBlank()) {
            logger.warning("addBullet failed: bullet is null or blank");
            throw new ResumakeException("Bullet cannot be blank.");
        }

        String trimmedBullet = bullet.trim();
        if (bullets.contains(trimmedBullet)) {
            logger.warning("addBullet failed: duplicate bullet");
            throw new ResumakeException("Duplicate bullet: an identical bullet already exists.");
        }

        bullets.add(trimmedBullet);
        logger.info("Bullet added successfully to record: " + title);
    }

    /**
     * Deletes a bullet by 0-based index from this record.
     *
     * @param bulletIndex 0-based bullet index.
     * @return Removed bullet text.
     * @throws IndexOutOfBoundsException If bullet index is invalid.
     */
    public String deleteBullet(int bulletIndex) {
        assert bullets != null : "Bullets list should not be null";

        logger.fine("Attempting to delete bullet " + bulletIndex + " from record: " + title);

        if (bulletIndex < 0 || bulletIndex >= bullets.size()) {
            logger.warning("deleteBullet failed: bullet index out of range");
            throw new IndexOutOfBoundsException("Bullet index is out of range.");
        }

        String removedBullet = bullets.remove(bulletIndex);
        logger.info("Bullet deleted successfully from record: " + title);
        return removedBullet;
    }

    public void moveBullet(int fromIndex, int toIndex) {
        assert bullets != null : "Bullets list should not be null";

        logger.fine("Attempting to move bullet in record: " + title
                + " from " + fromIndex + " to " + toIndex);

        if (fromIndex < 0 || fromIndex >= bullets.size()) {
            logger.warning("moveBullet failed: source bullet index out of range");
            throw new IndexOutOfBoundsException("Source bullet index is out of range.");
        }

        if (toIndex < 0 || toIndex >= bullets.size()) {
            logger.warning("moveBullet failed: target bullet index out of range");
            throw new IndexOutOfBoundsException("Target bullet index is out of range.");
        }

        if (fromIndex == toIndex) {
            logger.fine("moveBullet skipped: source and target indices are the same");
            return;
        }

        String bulletToMove = bullets.remove(fromIndex);
        bullets.add(toIndex, bulletToMove);

        logger.info("Bullet moved successfully in record: " + title);
    }

    public void editBullet(int bulletIndex, String newBullet) {
        assert bullets != null : "Bullets list should not be null";

        logger.fine("Attempting to edit bullet " + bulletIndex + " in record: " + title);

        if (bulletIndex < 0 || bulletIndex >= bullets.size()) {
            logger.warning("editBullet failed: bullet index out of range");
            throw new IndexOutOfBoundsException("Bullet index is out of range.");
        }

        if (newBullet == null || newBullet.isBlank()) {
            logger.warning("editBullet failed: new bullet is blank");
            throw new IllegalArgumentException("Bullet cannot be blank.");
        }

        bullets.set(bulletIndex, newBullet.trim());
        logger.info("Bullet edited successfully in record: " + title);
    }

    public ArrayList<String> getBullets() {
        assert bullets != null : "Bullets list should not be null";
        return bullets;
    }

    @Override
    public String toString() {
        assert recordType != null : "Record type should not be null";
        assert title != null : "Title should not be null";
        assert role != null : "Role should not be null";
        assert tech != null : "Tech should not be null";
        assert from != null : "From date should not be null";
        assert to != null : "To date should not be null";

        return "[" + recordType + "] " + title
                + " | role: " + role
                + " | tech: " + tech
                + " | from: " + from
                + " | to: " + to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Record)) {
            return false;
        }

        Record other = (Record) obj;
        return Objects.equals(title, other.title)
                && Objects.equals(role, other.role)
                && Objects.equals(tech, other.tech)
                && Objects.equals(from, other.from)
                && Objects.equals(to, other.to)
                && Objects.equals(recordType, other.recordType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, role, tech, from, to, recordType);
    }
}
