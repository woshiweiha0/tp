package seedu.duke;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.duke.commands.ExitCommand;

public class ExitCommandTest {
    @Test
    public void testExitCommand() {
        ExitCommand exitCommand = new ExitCommand();
        assertTrue(exitCommand.isExit());
    }
}
