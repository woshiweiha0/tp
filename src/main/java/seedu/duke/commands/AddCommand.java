package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;
import java.util.logging.Logger;

/**
 * Represents a command that adds a {@code Record} to the {@code RecordList}.
 */
public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final Record r;
    private final Ui ui;

    /**
     * Creates an AddCommnad with the specified record.
     *
     * @param r The record to be added (must not be null)
     */
    public AddCommand(Record r) throws ResumakeException {
        this(r, new Ui());
    }

    /**
     * Creates an AddCommand with the specified record and UI.
     *
     * @param r The record to be added.
     * @param ui The UI used to show messages and read bullet prompts.
     * @throws ResumakeException If the record is null or has an empty title.
     */
    public AddCommand(Record r, Ui ui) throws ResumakeException {
        if (r == null) {
            throw new ResumakeException("Record cannot be null.");
        }
        if (r.getTitle() == null || r.getTitle().trim().isEmpty()) {
            throw new ResumakeException("Record title cannot be empty.");
        }
        this.r = r;
        this.ui = ui == null ? new Ui() : ui;
    }

    /**
     * Executes the command by adding the given record to the list
     * Displays a confirmation message upon successful addition.
     *
     * @param list The RecordList to which the record will be added.
     */
    @Override
    public void execute(RecordList list) throws ResumakeException{
        logger.info("Executing AddCommand");

        if (list == null) {
            throw new ResumakeException("Record list cannot be null.");
        }

        //Assert preconditions
        assert list != null : "RecordList should not be null";
        assert r != null : "Record should not be null";
        assert r.getTitle() != null && !r.getTitle().trim().isEmpty()
                : "Record Title should not be empty";

        if (list.contains(r)) {
            logger.warning("Duplicate record detected: " + r.getTitle());
            throw new ResumakeException("Duplicate record: an identical record already exists.");
        }

        logger.fine("Adding record: " + r.getTitle());

        list.add(r);

        logger.info("Record added successfully: "+ r.getTitle());

        while(true) {
            ui.showMessage("Do you want to add bullet points? (y/n)");
            String answer = ui.readCommand().trim();

            if (answer.equalsIgnoreCase("y")) {
                ui.showMessage("Enter bullet points one by one. Type \"esc\" to stop");

                while (true) {
                    String bullet = ui.readCommand().trim();

                    if (bullet.equalsIgnoreCase("esc")) {
                        break;
                    }

                    if (bullet.isBlank()) {
                        ui.showMessage("Bullet cannot be blank.");
                        continue;
                    }
                    try {
                        r.addBullet(bullet);
                        ui.showMessage("Bullet added.");
                    } catch (ResumakeException e) {
                        ui.showMessage(e.getMessage());
                    }
                }
                break;
            }

            if (answer.equalsIgnoreCase("n")) {
                break;
            }

            ui.showMessage("Please enter y or n.");
        }


        ui.showLine();
        ui.showMessage("[" + r.getRecordType() + "] " + r.getTitle() + " added");
        ui.showLine();
    }
}
