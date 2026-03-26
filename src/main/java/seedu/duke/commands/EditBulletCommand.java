package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

public class EditBulletCommand extends Command {
    private static final Logger logger = Logger.getLogger(EditBulletCommand.class.getName());

    private final int userRecordIndex;
    private final int userBulletIndex;
    private final String newBullet;

    public EditBulletCommand(int userRecordIndex, int userBulletIndex, String newBullet) {
        assert userRecordIndex > 0 : "record index should be more than 0";
        assert userBulletIndex > 0 : "bullet index should be more than 0";

        this.userRecordIndex = userRecordIndex;
        this.userBulletIndex = userBulletIndex;
        this.newBullet = newBullet;

        logger.fine("EditBulletCommand created for record index=" + userRecordIndex
                + ", bullet index=" + userBulletIndex);
    }

    public int getUserRecordIndex() {
        return userRecordIndex;
    }

    public int getUserBulletIndex() {
        return userBulletIndex;
    }

    public String getNewBullet() {
        return newBullet;
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        assert list != null : "RecordList should not be null";

        logger.info("Executing bullet edit for record index=" + userRecordIndex
                + ", bullet index=" + userBulletIndex);

        try {
            Record record = list.getRecord(userRecordIndex - 1);
            record.editBullet(userBulletIndex - 1, newBullet);

            logger.info("Bullet edit succeeded for record index=" + userRecordIndex
                    + ", bullet index=" + userBulletIndex);

            System.out.println("Edited bullet " + userBulletIndex
                    + " in record " + userRecordIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Bullet edit failed for record index=" + userRecordIndex
                    + ", bullet index=" + userBulletIndex);
            throw new ResumakeException("Bullet index is out of range.");
        } catch (IllegalArgumentException e) {
            logger.warning("Bullet edit failed: " + e.getMessage());
            throw new ResumakeException(e.getMessage());
        }
    }
}
