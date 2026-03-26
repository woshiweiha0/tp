package seedu.duke.Commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

import java.time.YearMonth;
import java.util.logging.Logger;

public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(EditCommand.class.getName());

    private final int index;
    private final String newDescription;
    private final YearMonth newFrom;
    private final YearMonth newTo;
    private final Ui ui;

    public EditCommand(int index, String newDescription, YearMonth newFrom, YearMonth newTo) {
        if ((newDescription == null || newDescription.isBlank()) && newFrom == null && newTo == null) {
            throw new IllegalArgumentException("At least one field must be provided for edit.");
        }

        this.index = index;
        this.newDescription = (newDescription == null || newDescription.isBlank())
                ? null
                : newDescription.trim();
        this.newFrom = newFrom;
        this.newTo = newTo;
        this.ui = new Ui();

        assert this.ui != null : "Ui should be initialized";

        logger.info("EditCommand created with index=" + index
                + ", newDescription=" + this.newDescription
                + ", newFrom=" + newFrom
                + ", newTo=" + newTo);
    }

    @Override
    public void execute(RecordList list) {
        assert list != null : "RecordList passed to EditCommand should not be null";

        logger.info("Executing EditCommand on index=" + index);

        try {
            if (index < 0 || index >= list.getSize()) {
                throw new IndexOutOfBoundsException("Record index is out of range.");
            }

            Record record = list.getRecord(index);
            assert record != null : "Record at valid index should not be null";

            YearMonth finalFrom = (newFrom != null) ? newFrom : record.getFrom();
            YearMonth finalTo = (newTo != null) ? newTo : record.getTo();

            if (finalTo.isBefore(finalFrom)) {
                throw new IllegalArgumentException("End date cannot be before start date.");
            }

            if (newDescription != null) {
                record.setDescription(newDescription);
            }
            if (newFrom != null) {
                record.setFrom(newFrom);
            }
            if (newTo != null) {
                record.setTo(newTo);
            }

            ui.showLine();
            System.out.println("Record " + (index + 1) + " has been updated.");
            ui.showLine();

            logger.info("EditCommand completed successfully for index=" + index);

        } catch (IndexOutOfBoundsException e) {
            logger.warning(e.getMessage());
            ui.showLine();
            ui.showError("Record index is out of range.");
            ui.showLine();
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
            ui.showLine();
            ui.showError(e.getMessage());
            ui.showLine();
        }
    }
}
