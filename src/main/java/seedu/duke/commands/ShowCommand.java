package seedu.duke.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

public class ShowCommand extends Command {
    private static final Logger logger = Logger.getLogger(ShowCommand.class.getName());
    private final int index;
    private final Ui ui;

    public ShowCommand(int index) {
        this(index, new Ui());
    }

    public ShowCommand(int index, Ui ui) {
        this.index = index - 1;
        this.ui = ui == null ? new Ui() : ui;
    }

    public static void printRecord(RecordList records, int index) {
        Record record = records.getRecord(index);
        System.out.println(record);

        ArrayList<String> bullets = record.getBullets();
        if (bullets.isEmpty()) {
            System.out.println("  (no bullets)");
        } else {
            System.out.println("  Bullets:");
            for (int i = 0; i < bullets.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + bullets.get(i));
            }
        }
    }

    @Override
    public void execute(RecordList list) {
        logger.info("Executing ShowCommand with Index " + index);

        assert list != null : "RecordList should not be null";

        try {
            if (index < 0 || index >= list.getSize()) {
                throw new IndexOutOfBoundsException("Invalid record index: " + (index + 1)
                        + "\nRecord List Size: " + list.getSize());
            }

            Record record = list.getRecord(index);
            System.out.println("Showing record " + (index + 1));
            System.out.println(record);

            ArrayList<String> bullets = record.getBullets();
            if (bullets.isEmpty()) {
                System.out.println("  (no bullets)");
            } else {
                System.out.println("  Bullets:");
                for (int i = 0; i < bullets.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + bullets.get(i));
                }
            }

        } catch (Exception e) {
            logger.warning("Error executing ShowCommand: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }

        ui.showLine();
    }
}
