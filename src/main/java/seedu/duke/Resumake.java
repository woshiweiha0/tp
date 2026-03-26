package seedu.duke;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.Commands.Command;
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
        } catch (ResumakeException e) {
            ui.showLoadingError();
        }
    }

    public void run() {
        ui.greetings();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = ui.readCommand();
            try {
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
            } catch (IllegalArgumentException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("An unexpected error occurred while processing your command.");
            }
        }
    }

    public static void main(String[] args) {
        Logger rootLogger = Logger.getLogger("");
        boolean isDebugLoggingEnabled = Boolean.parseBoolean(System.getProperty("debugLogs", "false"));
        Level logLevel = isDebugLoggingEnabled ? Level.INFO : Level.OFF;
        rootLogger.setLevel(logLevel);
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(logLevel);
        }
        new Resumake().run();
    }
}
