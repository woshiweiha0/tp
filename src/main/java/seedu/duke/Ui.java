package seedu.duke;

import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Handles user interaction by managing input and displaying output messages.
 * <p>
 * This class is responsible for reading user commands from standard input
 * and printing formatted messages to the console.
 */
public class Ui {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a Ui object and initializes the input scanner.
     */
    public Ui() {

    }


    /**
     * Displays a horizontal separator line.
     */
    public void showLine() {
        System.out.println("--------------------");
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the greeting message when the application starts.
     */
    public void greetings() {
        showLine();
        System.out.println("Welcome to Resumake");
        showLine();
    }

    /**
     * Reads and returns a command entered by the user.
     *
     * @return The user input as a string.
     */
    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }

        // Fallback for environments/tests that replace System.in after this Ui instance was created.
        Scanner fallbackScanner = new Scanner(System.in);
        if (fallbackScanner.hasNextLine()) {
            return fallbackScanner.nextLine();
        }

        throw new NoSuchElementException("No line found");
    }

    /**
     * Displays an error message indicating failure to load records from file.
     */
    public void showLoadingError() {
        showLine();
        showError("Failed to load records from file.");
        showLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }
}
