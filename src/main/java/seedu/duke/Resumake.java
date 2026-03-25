package seedu.duke;

import java.io.FileNotFoundException;
import java.io.IOException;

import seedu.duke.Commands.Command;

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
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            if (c == null) {
                ui.showError("Unknown command.");
                continue;
            }
            c.execute(list);
            try {
                storage.saveToFile(list);
            } catch (IOException e) {
                ui.showLoadingError();
            }
            isExit = c.isExit();
        }
    }

    public static void main(String[] args) {
        new Resumake().run();
    }
}
