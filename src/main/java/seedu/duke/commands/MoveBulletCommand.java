package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

import java.util.logging.Logger;

/**
 * Represents a command that moves a bullet from one position to another
 * within the same record.
 */
public class MoveBulletCommand extends Command {
    private static final Logger logger = Logger.getLogger(MoveBulletCommand.class.getName());

    private final int recordIndex;
    private final int fromBulletIndex;
    private final int toBulletIndex;
    private final Ui ui;

    /**
     * Creates a MoveBulletCommand.
     *
     * @param recordIndex the 0-based index of the record
     * @param fromBulletIndex the 0-based source bullet index
     * @param toBulletIndex the 0-based target bullet index
     */
    public MoveBulletCommand(int recordIndex, int fromBulletIndex, int toBulletIndex) {
        this(recordIndex, fromBulletIndex, toBulletIndex, new Ui());
    }

    public MoveBulletCommand(int recordIndex, int fromBulletIndex, int toBulletIndex, Ui ui) {
        this.recordIndex = recordIndex;
        this.fromBulletIndex = fromBulletIndex;
        this.toBulletIndex = toBulletIndex;
        this.ui = ui == null ? new Ui() : ui;

        assert this.ui != null : "Ui should be initialized";
    }

    /**
     * Executes the bullet move operation for a specific record.
     * Validates the provided list and record index before delegating
     * bullet position checks to the underlying record logic.
     *
     * @param list the {@link RecordList} containing the target record.
     */
    @Override
    public void execute(RecordList list) throws ResumakeException {
        logger.info("Executing MoveBulletCommand for recordIndex=" + recordIndex
                + ", fromBulletIndex=" + fromBulletIndex
                + ", toBulletIndex=" + toBulletIndex);

        if (list == null) {
            throw new ResumakeException("RecordList cannot be null.");
        }

        assert list != null : "RecordList should not be null";

        if (recordIndex < 0 || recordIndex >= list.getSize()) {
            throw new ResumakeException("Invalid record index.");
        }

        Record record = list.getRecord(recordIndex);
        assert record != null : "Record at valid index should not be null";

        try {
            if (fromBulletIndex == toBulletIndex) {
                // Use record.moveBullet to validate bounds even for no-op moves.
                record.moveBullet(fromBulletIndex, toBulletIndex);
                ui.showLine();
                ui.showMessage("No changes made: bullet is already at position "
                        + (fromBulletIndex + 1) + " in record " + (recordIndex + 1) + ".");
                ui.showLine();
                logger.info("MoveBulletCommand skipped: source and target indices are the same");
                return;
            }

            record.moveBullet(fromBulletIndex, toBulletIndex);

            ui.showLine();
            ui.showMessage("Bullet " + (fromBulletIndex + 1)
                    + " moved to position " + (toBulletIndex + 1)
                    + " in record " + (recordIndex + 1) + ".");
            ui.showLine();

            logger.info("MoveBulletCommand completed successfully");
        } catch (IndexOutOfBoundsException e) {
            logger.warning("MoveBulletCommand failed: " + e.getMessage());
            throw new ResumakeException("Invalid bullet index.");
        } catch (IllegalArgumentException e) {
            logger.warning("MoveBulletCommand failed: " + e.getMessage());
            throw new ResumakeException(e.getMessage());
        }
    }
}
