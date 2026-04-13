package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

public class ListCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());
    private final Ui ui;
    private final String type;

    public ListCommand(){
        this("all", new Ui());
    }

    public ListCommand(String type){
        this(type, new Ui());
    }

    public ListCommand(Ui ui) {
        this("all", ui);
    }

    public ListCommand(String type, Ui ui) {
        this.ui = ui == null ? new Ui() : ui;
        this.type = type.toUpperCase();
        logger.fine("ListCommand created with type: " + this.type);
    }


    @Override
    public void execute(RecordList list) throws ResumakeException {
        int indexCount = 0;

        logger.info("Executing ListCommand with type filter: " + type);

        //Assert preconditions
        assert list != null : "RecordList should not be null";

        // Validate Type
        if (!type.equals("E") &&
            !type.equals("C") &&
            !type.equals("P") &&
            !type.equals("ALL")){

            logger.warning("ListCommand failed: invalid type \"" + type + "\"");
            throw new ResumakeException(
                    "Invalid type for list command.\n" +
                    "Valid types: E: Experience, C: Cca, P: Project\n" +
                    "Leave blank to list all."
            );
        }

        logger.fine("Type validation passed: " + type);

        if (list.getSize() == 0) {
            logger.info("ListCommand: record list is empty, nothing to display");
            ui.showMessage("No records found.");
            ui.showLine();
            return;
        }

        logger.fine("Record list size: " + list.getSize());

        String displayType = type.equals("ALL") ? "all" : type;
        ui.showMessage("Here is a list of " + displayType + " records.");

        int matchCount = 0;
        for (Record record : list){
            if (record == null) {
                logger.warning("Encountered null record in list at position " + (indexCount + 1));
                continue;
            }
            indexCount += 1;
            if (type.equals("ALL") || record.getRecordType().equals(type)) {
                logger.fine("Displaying record " + indexCount + ": " + record.getTitle());
                ui.showMessage(indexCount + ". " + record);
                matchCount++;
            } else {
                logger.fine("Skipping record " + indexCount + " (type " + record.getRecordType()
                        + " does not match filter " + type + ")");
            }
        }

        if (matchCount == 0) {
            logger.info("ListCommand: no records matched type filter \"" + type + "\"");
            ui.showMessage("No records found for type " + type + ".");
        } else {
            logger.info("ListCommand completed: displayed " + matchCount + " record(s) of type " + type);
        }
        ui.showLine();
    }
}
