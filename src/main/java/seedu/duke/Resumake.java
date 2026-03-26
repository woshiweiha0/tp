package seedu.duke;

import seedu.duke.commands.Command;
import seedu.duke.exceptions.ResumakeException;

public class Resumake {
    private RecordList list;
    private final Ui ui;
    private final Storage storage;

    public Resumake() {
        ui = new Ui();
        storage = new Storage();
        list = new RecordList();
        try {
            list = storage.loadFromFile(Storage.getFilepath());
        } catch (Exception e) {
            ui.showLoadingError();
        }
    }

    public void run() {
        ui.greetings();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                if (c == null) {
                    ui.showError("Unknown command.");
                    continue;
                }
                c.execute(list);
                storage.saveToFile(list);
                isExit = c.isExit();
            } catch (ResumakeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Resumake().run();
    }
}
