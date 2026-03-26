package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;

public class ShowCommand extends Command {
    private static final Logger logger = Logger.getLogger(ShowCommand.class.getName());
    int index;
    private final Ui ui;
    public ShowCommand(int index){
        this.index = index - 1;
        this.ui = new Ui();
    }

    public static void printRecord(RecordList records, int index) {
        System.out.println(records.getRecord(index));
    }

    @Override
    public void execute(RecordList list) {
        logger.info("Executing ShowCommand with Index " + index);

        //Assert preconditions
        assert list != null : "RecordList should not be null";

        try {
            // Ensures index int is within bounds
            if (index < 0 || index >= list.getSize()) {
                throw new IndexOutOfBoundsException("Invalid record index: " + (index + 1)
                        + "\nRecord List Size: " + list.getSize());
            }
            System.out.println("Showing record " + (index + 1) + "\n" + list.getRecord(index));
        } catch (Exception e) {
            logger.warning("Error executing ShowCommand: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }

        ui.showLine();

    }
}
