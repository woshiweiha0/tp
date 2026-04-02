package seedu.duke;

import seedu.duke.commands.Command;
import seedu.duke.exceptions.ResumakeException;

/**
 * Main entry point of the Resumake application.
 * <p>
 * This class is responsible for initializing core components such as
 * {@code Ui}, {@code Storage}, and {@code RecordList}, and managing the
 * main execution loop of the application.
 */
public class Resumake {
    private RecordList list;
    private final Ui ui;
    private final Storage storage;

    /**
     * Constructs a Resumake instance and initializes core components.
     * Attempts to load existing records from storage.
     * If loading fails, an error message is displayed.
     */
    public Resumake() {
        ui = new Ui();
        storage = new Storage(ui);
        list = new RecordList();
    }

    /**
     * Runs the main application loop.
     * <p>
     * Continuously reads user input, parses it into commands,
     * executes commands, and saves updates to storage until an exit
     * command is issued.
     */
    public void run() {
        ui.greetings();
        try {
            list = storage.loadFromFile(Storage.getFilepath());
        } catch (Exception e) {
            ui.showLoadingError();
        }
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand, ui);
                if (c == null) {
                    ui.showError("Unknown command.");
                    continue;
                }
                c.execute(list);
                storage.saveToFile(list);
                isExit = c.isExit();
            } catch (java.util.NoSuchElementException e) {
                ui.showError(e.getMessage());
                break;
            } catch (ResumakeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Starts the Resumake application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Resumake().run();
    }
}
