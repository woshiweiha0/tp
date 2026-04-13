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

    /**
     * Displays a record and all its bullets using the given UI.
     * If the record has no bullets, a placeholder message is shown.
     *
     * @param record The record to display.
     * @param ui The UI used to show the record output.
     * @throws IllegalArgumentException If the record or UI is null.
     */

    public static void showRecordWithBullets(Record record, Ui ui) {
        assert record != null : "Record should not be null";
        assert ui != null : "Ui should not be null";

        ui.showMessage(record.toString());

        ArrayList<String> bullets = record.getBullets();
        assert bullets != null : "Bullets list should not be null";

        if (bullets.isEmpty()) {
            ui.showMessage("  (no bullets)");
        } else {
            ui.showMessage("  Bullets:");
            for (int i = 0; i < bullets.size(); i++) {
                ui.showMessage("  " + (i + 1) + ". " + bullets.get(i));
            }
        }
    }

    /**
     * Executes the show command by displaying the selected record
     * and its bullet points.
     *
     * @param list Record list containing the target record.
     */
    @Override
    public void execute(RecordList list) {
        assert list != null : "RecordList should not be null";

        logger.info("Executing ShowCommand with internal index: " + index);

        try {
            if (index < 0 || index >= list.getSize()) {
                logger.warning("Invalid record index requested: " + (index + 1));
                throw new IndexOutOfBoundsException("Invalid record index: " + (index + 1)
                        + "\nRecord List Size: " + list.getSize());
            }

            Record record = list.getRecord(index);
            assert record != null : "Record retrieved from list should not be null";

            ui.showMessage("Showing record " + (index + 1));
            showRecordWithBullets(record, ui);

            logger.info("Successfully executed ShowCommand for record index: " + (index + 1));
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            logger.warning("Error executing ShowCommand: " + e.getMessage());
            ui.showError("Invalid record index" + "\nRecord List Size: " + list.getSize());
        }

        ui.showLine();
    }
}
