package seedu.duke.commands;

import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.User;
import seedu.duke.exceptions.ResumakeException;

public class EditUserCommand extends Command {
    private static final Logger logger = Logger.getLogger(EditUserCommand.class.getName());
    private static final int MAX_ATTEMPTS = 4;
    private final String field;
    private final Ui ui;

    public EditUserCommand(String field) throws ResumakeException {
        this(field, new Ui());
    }

    public EditUserCommand(String field, Ui ui) throws ResumakeException {
        logger.fine("Constructing EditUserCommand for field: " + field);
        if (!isValidField(field)) {
            logger.warning("EditUserCommand construction failed: invalid field \"" + field + "\"");
            throw new ResumakeException("Invalid User Field. Must be name, number or email.");
        }
        this.field = field;
        this.ui = ui == null ? new Ui() : ui;
        logger.fine("EditUserCommand created successfully for field: " + this.field);
    }

    private boolean isValidField(String field) {
        return "name".equals(field) || "number".equals(field) || "email".equals(field);
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        logger.info("Executing EditUserCommand for field: " + field);

        User user = User.getInstance();
        assert user != null : "User instance should not be null";

        logger.fine("Current value of \"" + field + "\": " + getCurrentValue(user));

        ui.showMessage("Current " + field + ": " + getCurrentValue(user));

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            logger.fine("EditUserCommand attempt " + (attempt + 1) + " of " + MAX_ATTEMPTS
                    + " for field: " + field);

            ui.showMessage("Enter new " + field + ":");
            String newValue = ui.readCommand();

            logger.fine("User entered value for \"" + field + "\": " + newValue);

            try {
                user.editField(field, newValue);
                logger.info("EditUserCommand succeeded: field \"" + field
                        + "\" updated to: " + getCurrentValue(user));
                ui.showMessage("Updated " + field + " to: " + getCurrentValue(user));
                return;
            } catch (ResumakeException e) {
                int attemptsLeft = MAX_ATTEMPTS - attempt - 1;
                logger.warning("EditUserCommand attempt " + (attempt + 1) + " failed for field \""
                        + field + "\": " + e.getMessage() + ". Attempts remaining: " + attemptsLeft);
                if (attemptsLeft > 0) {
                    ui.showError(e.getMessage() + " You have " + attemptsLeft + " more chance"
                            + (attemptsLeft == 1 ? "" : "s") + ".");
                } else {
                    logger.warning("EditUserCommand: all " + MAX_ATTEMPTS
                            + " attempts exhausted for field \"" + field + "\". Exiting command.");
                    throw new ResumakeException(
                            "You have exhausted all your attempts. edituser exited. "
                                    + "If you would like to try editing the user profile, "
                                    + "enter \"edituser\" again.");
                }
            }
        }
    }

    private String getCurrentValue(User user) {
        switch (field.toLowerCase()) {
        case "name":
            return user.getName();
        case "number":
            return String.valueOf(user.getNumber());
        case "email":
            return user.getEmail();
        default:
            logger.warning("getCurrentValue: unexpected field \"" + field + "\"");
            return "unknown";
        }
    }

}
