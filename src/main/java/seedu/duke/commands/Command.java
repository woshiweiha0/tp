package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.exceptions.ResumakeException;

public abstract class Command {
    public abstract void execute(RecordList list) throws ResumakeException;

    public boolean isExit() {
        return false;
    }
}
