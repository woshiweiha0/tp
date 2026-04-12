package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @AfterEach
    public void restoreSystemStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void greetings_noInput_printsWelcomeMessageWithLines() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Ui ui = new Ui();

        ui.greetings();

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "--------------------" + lineSeparator
                + "Welcome to ResuMake" + lineSeparator
                + "--------------------" + lineSeparator;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void showError_messageProvided_printsErrorPrefix() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Ui ui = new Ui();

        ui.showError("Something went wrong");

        String expectedOutput = "Error: Something went wrong" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void showLine_noInput_printsSeparator() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Ui ui = new Ui();

        ui.showLine();

        String expectedOutput = "--------------------" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void showMessage_messageProvided_printsMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Ui ui = new Ui();

        ui.showMessage("Hello world");

        String expectedOutput = "Hello world" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void showPrompt_noInput_printsPrompt() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Ui ui = new Ui();

        ui.showPrompt();

        assertEquals("> ", outputStream.toString());
    }

    @Test
    public void showLoadingError_noInput_printsWrappedError() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Ui ui = new Ui();

        ui.showLoadingError();

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "--------------------" + lineSeparator
                + "Error: Failed to load records from file." + lineSeparator
                + "--------------------" + lineSeparator;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void readCommand_userInputsBye_returnsBye() {
        String userInput = "bye" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
        Ui ui = new Ui();

        String command = ui.readCommand();
        assertEquals("bye", command);
    }
}
