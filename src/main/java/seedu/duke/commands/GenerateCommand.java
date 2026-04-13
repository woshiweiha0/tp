package seedu.duke.commands;

import static seedu.duke.commands.ShowCommand.showRecordWithBullets;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.User;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

/**
 * Generates and displays all records grouped by record type,
 * together with the current user's personal details.
 */
public class GenerateCommand extends Command {
    private static final Logger logger = Logger.getLogger(GenerateCommand.class.getName());

    private final Ui ui;

    /**
     * Creates a GenerateCommand with a default UI instance.
     */
    public GenerateCommand() {
        this(new Ui());
    }

    /**
     * Creates a GenerateCommand with the given UI.
     *
     * @param ui UI used to display generated output.
     */
    public GenerateCommand(Ui ui) {
        assert ui != null : "Ui should not be null";
        this.ui = ui;
    }

    /**
     * Executes the generate command by printing the user's details
     * followed by all records grouped under their record types.
     *
     * @param list Record list to be generated.
     * @throws ResumakeException If the user has not been initialized.
     * @throws IllegalArgumentException If the record list is null.
     * @throws IllegalStateException If a record in the list is null.
     */
    @Override
    public void execute(RecordList list) throws ResumakeException {
        assert list != null : "RecordList should not be null";
        assert ui != null : "Ui should not be null";


        User user = User.getInstance();
        assert user != null : "User should be initialized before generating output";

        logger.info("Executing GenerateCommand");

        List<String> recordTypes = new ArrayList<>(List.of("Cca", "Experience", "Project"));

        ui.showMessage(user.getName());
        ui.showMessage("Number: " + user.getNumber());
        ui.showMessage("Email: " + user.getEmail());
        ui.showLine();

        for (String type : recordTypes) {
            assert type != null && !type.isBlank() : "Record type should not be null or blank";

            ui.showMessage(type);
            ui.showLine();

            String charType = type.substring(0, 1);
            int index = 1;

            for (Record record : list) {
                if (record == null) {
                    logger.warning("Encountered null record during generation, skipping");
                    continue;
                }

                assert record != null : "Record in list should not be null";

                if (record.getRecordType().equals(charType)) {
                    logger.info("Printing record with index " + index + " under type " + type);

                    try {
                        showRecordWithBullets(record, ui);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        logger.warning("Error generating record output: " + e.getMessage());
                        ui.showError(e.getMessage());
                    }

                    ui.showLine();
                }
                index++;
            }
        }
        ui.showMessage("Skills");
        ui.showLine();
        ui.showMessage(user.getSkillsAsString());
        ui.showLine();
    }
}
