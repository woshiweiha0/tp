package seedu.duke.Commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;

public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());
    private final int userIndex;

    public DeleteCommand(int userIndex) {
        assert userIndex > 0 : "index should be more than 0";
        this.userIndex = userIndex;
        logger.fine("DeleteCommand created with index=" + userIndex);
    }

    @Override
    public void execute(RecordList list) {
        logger.info("Executing delete for index=" + userIndex);
        try {
            list.removeIndex(userIndex - 1);
            logger.info("Delete succeeded for index=" + userIndex);
            System.out.println("Deleted record " + userIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("Delete failed for index=" + userIndex + " (out of bounds)");
            System.out.println("Nothing to delete.");
        }
    }
}
