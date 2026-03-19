package seedu.duke;

public class DeleteCommand extends Command {
    private final int userIndex;

    public DeleteCommand(int userIndex) {
        assert userIndex > 0 : "index should be more than 0";
        this.userIndex = userIndex;
    }

    @Override
    public void execute(RecordList list) {
        try {
            list.removeIndex(userIndex - 1);
            System.out.println("Deleted record " + userIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Nothing to delete.");
        }
    }
}
