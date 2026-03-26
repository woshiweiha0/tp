package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

import java.time.YearMonth;
import java.util.logging.Logger;

public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(EditCommand.class.getName());

    private final int index;
    private final String newTitle;
    private final String newRole;
    private final String newTech;
    private final YearMonth newFrom;
    private final YearMonth newTo;
    private final Ui ui;

    public EditCommand(int index, String newTitle, String newRole, String newTech,
                       YearMonth newFrom, YearMonth newTo) {
        if ((newTitle == null || newTitle.isBlank())
                && (newRole == null || newRole.isBlank())
                && (newTech == null || newTech.isBlank())
                && newFrom == null
                && newTo == null) {
            throw new IllegalArgumentException("At least one field must be provided for edit.");
        }

        this.index = index;
        this.newTitle = (newTitle == null || newTitle.isBlank()) ? null : newTitle.trim();
        this.newRole = (newRole == null || newRole.isBlank()) ? null : newRole.trim();
        this.newTech = (newTech == null || newTech.isBlank()) ? null : newTech.trim();
        this.newFrom = newFrom;
        this.newTo = newTo;
        this.ui = new Ui();

        logger.info("EditCommand created with index=" + index
                + ", newTitle=" + this.newTitle
                + ", newRole=" + this.newRole
                + ", newTech=" + this.newTech
                + ", newFrom=" + this.newFrom
                + ", newTo=" + this.newTo);
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

            if (newTitle != null) {
                record.setDescription(newTitle);
            }
            if (newRole != null) {
                record.setRole(newRole);
            }
            if (newTech != null) {
                record.setTech(newTech);
            }
            if (newFrom != null) {
                record.setFrom(newFrom);
            }
            if (newTo != null) {
                record.setTo(newTo);
            }

            ui.showLine();
            ui.showMessage("Record " + (index + 1) + " has been updated.");
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
