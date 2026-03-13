package seedu.duke;

public class EditCommand extends Command {
    private final int index;
    private final String newDescription;
    private final Ui ui;

    public EditCommand(int index, String newDescription) {
        this.index = index;
        this.newDescription = newDescription;
        this.ui = new Ui();
    }

    @Override
    public void execute(RecordList list) {
        if (index < 0 || index >= list.getSize()) {
            ui.showLine();
            ui.showError("Record index is out of range.");
            ui.showLine();
            return;
        }

        Record record = list.getRecord(index);
        record.setDescription(newDescription);

        ui.showLine();
        System.out.println("Record " + (index + 1) + " has been updated.");
        ui.showLine();
    }
}