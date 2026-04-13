package seedu.duke.commands;

import java.util.Comparator;
import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.recordtype.Record;
import seedu.duke.Ui;
import seedu.duke.exceptions.ResumakeException;

public class SortCommand extends Command{
    private static final Logger logger = Logger.getLogger(SortCommand.class.getName());
    private final Ui ui;

    public SortCommand(){
        this(new Ui());
    }

    public SortCommand(Ui ui) {
        this.ui = ui == null ? new Ui() : ui;
        logger.fine("SortCommand created");
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        logger.info("Executing SortCommand");
        assert list != null : "RecordList should not be null";

        if (list.getSize() == 0) {
            logger.info("SortCommand: record list is empty, nothing to sort");
            ui.showLine();
            ui.showMessage("No records to sort.");
            ui.showLine();
            return;
        }

        logger.fine("SortCommand: sorting " + list.getSize() + " record(s) by title (case-insensitive)");

        list.sort(Comparator.comparing(Record::getTitle, String.CASE_INSENSITIVE_ORDER));

        logger.info("SortCommand: records sorted successfully by title");

        ui.showLine();
        ui.showMessage("Records sorted alphabetically by title.");
        ui.showLine();

    }
}
