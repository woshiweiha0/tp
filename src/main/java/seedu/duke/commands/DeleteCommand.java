package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

/**
 * Deletes either a full record or a bullet within a record.
 */
public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());
    private final int userRecordIndex;
    private final int userBulletIndex;
    private final boolean isDeleteBulletMode;

    /**
     * Creates a delete command that removes a record.
     *
     * @param userRecordIndex 1-based record index from user input.
     */
    public DeleteCommand(int userRecordIndex) {
        assert userRecordIndex > 0 : "index should be more than 0";
        this.userRecordIndex = userRecordIndex;
        this.userBulletIndex = -1;
        this.isDeleteBulletMode = false;
        logger.fine("DeleteCommand created for record index=" + userRecordIndex);
    }

    /**
     * Creates a delete command that removes a bullet from a record.
     *
     * @param userRecordIndex 1-based record index from user input.
     * @param userBulletIndex 1-based bullet index from user input.
     */
    public DeleteCommand(int userRecordIndex, int userBulletIndex) {
        assert userRecordIndex > 0 : "record index should be more than 0";
        assert userBulletIndex > 0 : "bullet index should be more than 0";
        this.userRecordIndex = userRecordIndex;
        this.userBulletIndex = userBulletIndex;
        this.isDeleteBulletMode = true;
        logger.fine("DeleteCommand created for record index=" + userRecordIndex
                + ", bullet index=" + userBulletIndex);
    }

    /**
     * Returns whether this command is configured for bullet deletion.
     *
     * @return true if deleting a bullet; false if deleting a record.
     */
    public boolean isDeleteBulletMode() {
        return isDeleteBulletMode;
    }

    /**
     * Returns the 1-based record index provided by the user.
     *
     * @return user record index.
     */
    public int getUserRecordIndex() {
        return userRecordIndex;
    }

    /**
     * Returns the 1-based bullet index provided by the user.
     *
     * @return user bullet index.
     */
    public int getUserBulletIndex() {
        return userBulletIndex;
    }

    /**
     * Executes record deletion or bullet deletion based on command mode.
     *
     * @param list Current record list.
     * @throws ResumakeException If the requested item cannot be deleted.
     */
    @Override
    public void execute(RecordList list) throws ResumakeException {
        assert list != null : "RecordList should not be null";

        if (isDeleteBulletMode) {
            deleteBullet(list);
            return;
        }

        deleteRecord(list);
    }

    /**
     * Deletes a record using the user-provided 1-based index.
     *
     * @param list Current record list.
     * @throws ResumakeException If the record index is out of range.
     */
    private void deleteRecord(RecordList list) throws ResumakeException {
        logger.info("Executing record delete for index=" + userRecordIndex);
        try {
            list.removeIndex(userRecordIndex - 1);
            logger.info("Record delete succeeded for index=" + userRecordIndex);
            System.out.println("Deleted record " + userRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Record delete failed for index=" + userRecordIndex + " (out of bounds)");
            throw new ResumakeException("Invalid record index");
        }
    }

    /**
     * Deletes a bullet using user-provided 1-based indices.
     *
     * @param list Current record list.
     * @throws ResumakeException If record or bullet indices are out of range.
     */
    private void deleteBullet(RecordList list) throws ResumakeException {
        logger.info("Executing bullet delete for record index=" + userRecordIndex
                + ", bullet index=" + userBulletIndex);
        try {
            Record record = list.getRecord(userRecordIndex - 1);
            try {
                record.deleteBullet(userBulletIndex - 1);
            } catch (IndexOutOfBoundsException e) {
                logger.warning("Bullet delete failed for record index=" + userRecordIndex
                        + ", bullet index=" + userBulletIndex + " (bullet out of bounds)");
                throw new ResumakeException("Invalid bullet index");
            }
            logger.info("Bullet delete succeeded for record index=" + userRecordIndex
                    + ", bullet index=" + userBulletIndex);
            System.out.println("Deleted bullet " + userBulletIndex + " from record " + userRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Bullet delete failed for record index=" + userRecordIndex
                    + ", bullet index=" + userBulletIndex + " (record out of bounds)");
            throw new ResumakeException("Invalid record index");
        }
    }
}
