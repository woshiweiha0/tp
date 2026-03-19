package seedu.duke;

public class DeleteCommand extends Command {
    private final int userIndex;

    public DeleteCommand(int userIndex) {
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
