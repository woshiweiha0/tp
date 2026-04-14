package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.commands.HelpCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandTest {
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        User.resetInstance();

        User.loadFrom("John", 91234567, "john@example.com");
    }

    @AfterEach
    public void restoreSystemStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void execute_printsAvailableCommandsWithDescriptions() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = new RecordList();
        HelpCommand command = new HelpCommand();
        command.execute(list);

        String output = outputStream.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("find KEYWORD - Find records by keyword."));
        assertTrue(output.contains("edituser FIELD - Edit user details (name, number, email)."));
        assertTrue(output.contains("bye - Exit ResuMake."));
    }
}
