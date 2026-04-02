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
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        logger.info("Executing SortCommand");
        assert list != null : "RecordList should not be null";

        list.sort(Comparator.comparing(Record::getTitle, String.CASE_INSENSITIVE_ORDER));

        logger.info("Records sorted by title");

        ui.showLine();
        ui.showMessage("Records sorted alphabetically by title.");
        ui.showLine();

    }
}
