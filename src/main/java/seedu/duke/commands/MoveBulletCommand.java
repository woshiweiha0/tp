package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

import java.util.logging.Logger;

/**
 * Moves a bullet from one position to another within the same record.
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
        this.recordIndex = recordIndex;
        this.fromBulletIndex = fromBulletIndex;
        this.toBulletIndex = toBulletIndex;
        this.ui = new Ui();

        assert this.ui != null : "Ui should be initialized";
    }

    @Override
    public void execute(RecordList list) {
        logger.info("Executing MoveBulletCommand for recordIndex=" + recordIndex
                + ", fromBulletIndex=" + fromBulletIndex
                + ", toBulletIndex=" + toBulletIndex);

        try {
            if (list == null) {
                throw new IllegalArgumentException("RecordList cannot be null.");
            }

            assert list != null : "RecordList should not be null";

            if (recordIndex < 0 || recordIndex >= list.getSize()) {
                throw new IndexOutOfBoundsException("Record index is out of range.");
            }

            Record record = list.getRecord(recordIndex);
            assert record != null : "Record at valid index should not be null";

            record.moveBullet(fromBulletIndex, toBulletIndex);

            ui.showLine();
            ui.showMessage("Bullet " + (fromBulletIndex + 1)
                    + " moved to position " + (toBulletIndex + 1)
                    + " in record " + (recordIndex + 1) + ".");
            ui.showLine();

            logger.info("MoveBulletCommand completed successfully");

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            logger.warning("MoveBulletCommand failed: " + e.getMessage());
            ui.showLine();
            ui.showError(e.getMessage());
            ui.showLine();
        }
    }
}
