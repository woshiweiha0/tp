package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;

public class ExitCommand extends Command {
    private final Ui ui;

    public ExitCommand() {
        this.ui = new Ui();
    }

    @Override
    public void execute (RecordList list) {
        ui.showLine();
        System.out.println("bye");
        ui.showLine();
    }

    public boolean isExit() {
        return true;
    }
}
