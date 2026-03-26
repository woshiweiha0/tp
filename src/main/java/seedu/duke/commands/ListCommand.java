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
        this.type = "all";
        this.ui = new Ui();
    }

    public ListCommand(String type){
        this.ui = new Ui();
        this.type = type;
    }


    @Override
    public void execute(RecordList list) throws ResumakeException {
        int indexCount = 0;

        //Assert preconditions
        assert list != null : "RecordList should not be null";

        // Validate Type
        if (!type.equals("E") &&
            !type.equals("C") &&
            !type.equals("P") &&
            !type.equals("all")){

            throw new ResumakeException(
                    "Invalid type for list command.\n" +
                    "Valid types: E: Experience, C: Cca, P: Project\n" +
                    "Leave blank to list all."
            );
        }

        if (list.getSize() == 0) {
            ui.showMessage("No records found.");
            ui.showLine();
            return;
        }

        System.out.println("Here is a list of " + type + " records.");

        for (Record record : list){
            if (record == null) {
                logger.warning("Encountered null record in list");
                continue;
            }

            if (type.equals("all") || record.getRecordType().equals(type)) {

                indexCount += 1;
                ui.showMessage(indexCount + ". " + record);
            }
        }
        ui.showLine();
    }
}
