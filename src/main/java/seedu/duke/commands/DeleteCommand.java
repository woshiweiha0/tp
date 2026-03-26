package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());
    private final int userRecordIndex;
    private final int userBulletIndex;
    private final boolean isDeleteBulletMode;

    public DeleteCommand(int userRecordIndex) {
        assert userRecordIndex > 0 : "index should be more than 0";
        this.userRecordIndex = userRecordIndex;
        this.userBulletIndex = -1;
        this.isDeleteBulletMode = false;
        logger.fine("DeleteCommand created for record index=" + userRecordIndex);
    }

    public DeleteCommand(int userRecordIndex, int userBulletIndex) {
        assert userRecordIndex > 0 : "record index should be more than 0";
        assert userBulletIndex > 0 : "bullet index should be more than 0";
        this.userRecordIndex = userRecordIndex;
        this.userBulletIndex = userBulletIndex;
        this.isDeleteBulletMode = true;
        logger.fine("DeleteCommand created for record index=" + userRecordIndex
                + ", bullet index=" + userBulletIndex);
    }

    public boolean isDeleteBulletMode() {
        return isDeleteBulletMode;
    }

    public int getUserRecordIndex() {
        return userRecordIndex;
    }

    public int getUserBulletIndex() {
        return userBulletIndex;
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        assert list != null : "RecordList should not be null";

        if (isDeleteBulletMode) {
            deleteBullet(list);
            return;
        }

        deleteRecord(list);
    }

    private void deleteRecord(RecordList list) throws ResumakeException {
        logger.info("Executing record delete for index=" + userRecordIndex);
        try {
            list.removeIndex(userRecordIndex - 1);
            logger.info("Record delete succeeded for index=" + userRecordIndex);
            System.out.println("Deleted record " + userRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Record delete failed for index=" + userRecordIndex + " (out of bounds)");
            throw new ResumakeException("Nothing to delete.");
        }
    }

    private void deleteBullet(RecordList list) throws ResumakeException {
        logger.info("Executing bullet delete for record index=" + userRecordIndex
                + ", bullet index=" + userBulletIndex);
        try {
            Record record = list.getRecord(userRecordIndex - 1);
            record.deleteBullet(userBulletIndex - 1);
            logger.info("Bullet delete succeeded for record index=" + userRecordIndex
                    + ", bullet index=" + userBulletIndex);
            System.out.println("Deleted bullet " + userBulletIndex + " from record " + userRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Bullet delete failed for record index=" + userRecordIndex
                    + ", bullet index=" + userBulletIndex);
            throw new ResumakeException("Nothing to delete.");
        }
    }
}
