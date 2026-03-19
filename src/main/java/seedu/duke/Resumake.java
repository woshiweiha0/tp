package seedu.duke;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } catch (FileNotFoundException e) {
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
