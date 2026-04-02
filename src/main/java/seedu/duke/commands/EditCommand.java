/**
 * Represents a command that edits fields of a record in the RecordList.
 *
 * <p>The command supports partial updates. Only the fields provided by the user
 * will be modified, while unspecified fields remain unchanged.</p>
 *
 * <p>Editable fields include:
 * <ul>
 *     <li>title</li>
 *     <li>role</li>
 *     <li>tech</li>
 *     <li>from (start date)</li>
 *     <li>to (end date)</li>
 * </ul>
 *
 * <p>Validation ensures that:
 * <ul>
 *     <li>At least one field is provided for editing</li>
 *     <li>Date ranges remain valid (from ≤ to)</li>
 * </ul>
 * </p>
 */

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

    /**
     * Constructs an EditCommand to update specified fields of a record.
     *
     * @param index The 0-based index of the record in the RecordList.
     * @param newTitle The new title of the record, or null if unchanged.
     * @param newRole The new role of the record, or null if unchanged.
     * @param newTech The new tech of the record, or null if unchanged.
     * @param newFrom The new start date, or null if unchanged.
     * @param newTo The new end date, or null if unchanged.
     *
     * @throws IllegalArgumentException If all fields are null or blank.
     */

    public EditCommand(int index, String newTitle, String newRole, String newTech,
                       YearMonth newFrom, YearMonth newTo) {
        this(index, newTitle, newRole, newTech, newFrom, newTo, new Ui());
    }

    public EditCommand(int index, String newTitle, String newRole, String newTech,
                       YearMonth newFrom, YearMonth newTo, Ui ui) {

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
        this.ui = ui == null ? new Ui() : ui;

        assert index >= 0 : "EditCommand index should be 0-based and non-negative";
        assert this.ui != null : "Ui should be initialized";
        assert !(this.newTitle == null && this.newRole == null && this.newTech == null
                && this.newFrom == null && this.newTo == null)
                : "At least one edit field must remain after trimming";

        logger.info(() -> "EditCommand created: index=" + index
                + ", title=" + this.newTitle
                + ", role=" + this.newRole
                + ", tech=" + this.newTech
                + ", from=" + this.newFrom
                + ", to=" + this.newTo);
    }

    /**
     * Executes the edit operation on the given RecordList.
     *
     * <p>If the index is valid, the specified fields of the target record
     * will be updated. Fields that are null will not be modified.</p>
     *
     * <p>If the index is invalid or the updated date range is invalid,
     * no changes will be made.</p>
     *
     * @param list The RecordList containing records.
     */

    @Override
    public void execute(RecordList list) {
        assert list != null : "RecordList passed to EditCommand should not be null";

        logger.info(() -> "Executing EditCommand on record index " + index);

        try {
            if (index < 0 || index >= list.getSize()) {
                throw new IndexOutOfBoundsException("Record index is out of range.");
            }

            Record record = list.getRecord(index);
            assert record != null : "Record at valid index should not be null";

            logger.fine(() -> "Before edit: " + record);

            YearMonth finalFrom = (newFrom != null) ? newFrom : record.getFrom();
            YearMonth finalTo = (newTo != null) ? newTo : record.getTo();

            assert finalFrom != null : "Record from date should not be null";
            assert finalTo != null : "Record to date should not be null";

            if (finalTo.isBefore(finalFrom)) {
                throw new IllegalArgumentException("End date cannot be before start date.");
            }

            if (newTitle != null) {
                logger.fine(() -> "Updating title to: " + newTitle);
                record.setTitle(newTitle);
            }

            if (newRole != null) {
                logger.fine(() -> "Updating role to: " + newRole);
                record.setRole(newRole);
            }

            if (newTech != null) {
                logger.fine(() -> "Updating tech to: " + newTech);
                record.setTech(newTech);
            }

            if (newFrom != null) {
                logger.fine(() -> "Updating from date to: " + newFrom);
                record.setFrom(newFrom);
            }

            if (newTo != null) {
                logger.fine(() -> "Updating to date to: " + newTo);
                record.setTo(newTo);
            }

            logger.fine(() -> "After edit: " + record);

            ui.showLine();
            ui.showMessage("Record " + (index + 1) + " has been updated.");
            ui.showLine();

            logger.info(() -> "EditCommand completed successfully for record index " + index);

        } catch (IndexOutOfBoundsException e) {
            logger.warning(() -> "EditCommand failed: invalid record index " + index);
            ui.showLine();
            ui.showError("Record index is out of range.");
            ui.showLine();

        } catch (IllegalArgumentException e) {
            logger.warning(() -> "EditCommand failed: " + e.getMessage());
            ui.showLine();
            ui.showError(e.getMessage());
            ui.showLine();
        }
    }
}
