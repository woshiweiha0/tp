package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

public class ListCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());
    private final Ui ui;

    public ListCommand(){
        this.ui = new Ui();
    }


    @Override
    public void execute(RecordList list) {
        int indexCount = 0;

        if (list.getSize() == 0) {
            System.out.println("No records found.");
            ui.showLine();
            return;
        }

        //Assert preconditions
        assert list != null : "RecordList should not be null";

        System.out.println("Here is a list of all your records.");
        for (Record record : list){
            if (record == null) {
                logger.warning("Encountered null record in list");
                continue;
            }

            indexCount += 1;
            System.out.println(indexCount + ". " + record);
        }
        ui.showLine();
    }
}
