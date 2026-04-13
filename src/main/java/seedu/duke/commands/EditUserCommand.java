package seedu.duke.commands;

import static seedu.duke.User.promptForEmail;
import static seedu.duke.User.promptForName;
import static seedu.duke.User.promptForNumber;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.User;
import seedu.duke.exceptions.ResumakeException;

public class EditUserCommand extends Command {
    private final String field;
    private final Ui ui;

    public EditUserCommand(String field) throws ResumakeException {
        this(field, new Ui());
    }

    public EditUserCommand(String field, Ui ui) throws ResumakeException {
        if (!isValidField(field)) {
            throw new ResumakeException("Invalid User Field. Must be name, number or email.");
        }
        this.field = field;
        this.ui = ui == null ? new Ui() : ui;
    }

    private boolean isValidField(String field) {
        return "name".equals(field) || "number".equals(field) || "email".equals(field);
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        User user = User.getInstance();

        ui.showMessage("Current " + field + ": " + getCurrentValue(user));
        ui.showMessage("Enter new " + field + ":");
        String newValue = promptUserField(user);

        user.editField(field, newValue);
        ui.showMessage("Updated " + field + " to: " + getCurrentValue(user));
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
            return "unknown";
        }
    }

    private String promptUserField(User user) {
        switch (field.toLowerCase()) {
        case "name":
            return promptForName();
        case "number":
            return Integer.toString(promptForNumber());
        case "email":
            return promptForEmail();
        default:
            return "unknown";
        }
    }

}
