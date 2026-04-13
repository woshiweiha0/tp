package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.recordtype.Record;
import seedu.duke.Ui;
import seedu.duke.exceptions.ResumakeException;

import java.util.logging.Logger;

/**
 * Represents a command that addds a bullet point to a specific record
 */
public class AddBulletCommand extends Command {

    private static final Logger logger = Logger.getLogger(AddBulletCommand.class.getName());

    private final int index;
    private final String bullet;
    private final Ui ui;

    /**
     * Creates an AddBulletCommand with the specified record index and bullet
     *
     * @param index Index of the record (must be non-negative)
     * @param bullet Bullet point content (must not be null or blank)
     * @throws IllegalArgumentException if index is negative or bullet is null/blank
     */
    public AddBulletCommand(int index, String bullet) throws ResumakeException {
        this(index, bullet, new Ui());
    }

    public AddBulletCommand(int index, String bullet, Ui ui) throws ResumakeException{
        if (index < 0) {
            throw new ResumakeException("Record index must be non-negative.");
        }
        if (bullet == null || bullet.trim().isEmpty()) {
            throw new ResumakeException("Bullet cannot be blank.");
        }

        this.index = index;
        this.bullet = bullet.trim();
        this.ui = ui == null ? new Ui() : ui;

        assert this.index >= 0 : "Record index should be 0-based and non-negative";
        assert !this.bullet.isBlank() : "Bullet should not be blank after trimming";
        assert this.ui != null : "Ui should be initialized";

        logger.info(() -> "AddBulletCommand created for record index=" + index
                + ", bullet=" + this.bullet);
    }

    /**
     * Returns the index of hte record associated with this command
     *
     * @return the record index (0-based).
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the bullet point to be added
     *
     * @return the bullet string
     */
    public String getBullet() {
        return bullet;
    }

    /**
     * Executes the command by adding the bullet to the specified record
     * Displays success or error messages via the UI.
     *
     * @param list The Record List containing all records.
     */
    @Override
    public void execute(RecordList list) throws ResumakeException {
        assert list != null : "RecordList should not be null";

        logger.info(() -> "Executing AddBulletCommand for record index=" + index);

        try {
            Record record = list.getRecord(index);
            assert record != null : "Record at valid index should not be null";
            assert record.getBullets() != null : "Bullets list should not be null";

            logger.fine(() -> "Adding bullet to record: " + record.getTitle());

            record.addBullet(bullet);

            logger.info(() -> "Bullet added successfully to record index=" + index);

            ui.showLine();
            ui.showMessage("Added bullet to: " + record.getTitle());
            ui.showLine();

        } catch (IndexOutOfBoundsException e) {
            logger.warning(() -> "AddBulletCommand failed: record index out of range: " + index);
            throw new ResumakeException("Invalid record index");

        } catch (IllegalArgumentException e) {
            logger.warning(() -> "AddBulletCommand failed: " + e.getMessage());
            throw new ResumakeException(e.getMessage());
        }
    }
}
