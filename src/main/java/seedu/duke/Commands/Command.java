package seedu.duke.Commands;

import seedu.duke.RecordList;

public abstract class Command {
    public abstract void execute(RecordList list);

    public boolean isExit() {
        return false;
    }
}
