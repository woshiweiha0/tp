package seedu.duke.Commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;
import java.util.logging.Logger;

public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final Record r;
    private final Ui ui = new Ui();

    public AddCommand(Record r) {
        this.r = r;
    }

    @Override
    public void execute(RecordList list) {
        logger.info("Executing AddCommand");

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
