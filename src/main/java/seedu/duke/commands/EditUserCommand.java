package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.User;
import seedu.duke.exceptions.ResumakeException;

public class EditUserCommand extends Command {
    private final String field;
    private final Ui ui;

    public EditUserCommand(String field) {
        this(field, new Ui());
    }

    public EditUserCommand(String field, Ui ui) {
        this.field = field;
        this.ui = ui == null ? new Ui() : ui;
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        User user = User.getInstance();

        ui.showMessage("Current " + field + ": " + getCurrentValue(user));
        ui.showMessage("Enter new " + field + ":");
        String newValue = ui.readCommand();

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
}
