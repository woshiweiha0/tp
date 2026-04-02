package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;
import java.util.logging.Logger;

/**
 * Represents a command that adds a {@code Record} to the {@code RecordList}.
 */
public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final Record r;
    private final Ui ui;

    /**
     * Creates an AddCommnad with the specified record.
     *
     * @param r The record to be added (must not be null)
     */
    public AddCommand(Record r) throws ResumakeException {
        this(r, new Ui());
    }

    public AddCommand(Record r, Ui ui) throws ResumakeException {
        if (r == null) {
            throw new ResumakeException("Record cannot be null");
        }
        if (r.getTitle() == null || r.getTitle().trim().isEmpty()) {
            throw new ResumakeException("Record title cannot be empty");
        }
        this.r = r;
        this.ui = ui == null ? new Ui() : ui;
    }

    /**
     * Executes the command by adding the given record to the list
     * Displays a confirmation message upon successful addition.
     *
     * @param list The RecordList to which the record will be added.
     */
    @Override
    public void execute(RecordList list) throws ResumakeException{
        logger.info("Executing AddCommand");

        if (list == null) {
            throw new ResumakeException("Record list cannot be null");
        }

        //Assert preconditions
        assert list != null : "RecordList should not be null";
        assert r != null : "Record should not be null";
        assert r.getTitle() != null && !r.getTitle().isEmpty()
                : "Record Title should not be empty";

        logger.fine("Adding record: " + r.getTitle());

        list.add(r);

        logger.info("Record added successfully: "+ r.getTitle());

        ui.showLine();
        System.out.println("[" + r.getRecordType() + "] "
                + r.getTitle() + " added");
        ui.showLine();
    }
}
