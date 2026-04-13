package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.EditUserCommand;
import seedu.duke.exceptions.ResumakeException;

public class EditUserCommandTest {
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        User.loadFrom("John", 91234567, "john@example.com");
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    public void execute_editName_updatesName() throws Exception {
        System.setIn(new ByteArrayInputStream("Jane\n".getBytes()));

        EditUserCommand cmd = new EditUserCommand("name");
        cmd.execute(new RecordList());

        assertEquals("Jane", User.getInstance().getName());
    }

    @Test
    public void execute_editNumber_updatesNumber() throws Exception {
        System.setIn(new ByteArrayInputStream("99999999\n".getBytes()));

        EditUserCommand cmd = new EditUserCommand("number");
        cmd.execute(new RecordList());

        assertEquals(99999999, User.getInstance().getNumber());
    }

    @Test
    public void execute_editEmail_updatesEmail() throws Exception {
        System.setIn(new ByteArrayInputStream("jane@test.com\n".getBytes()));

        EditUserCommand cmd = new EditUserCommand("email");
        cmd.execute(new RecordList());

        assertEquals("jane@test.com", User.getInstance().getEmail());
    }

    @Test
    public void executeEditEmailRetryThenSuccessUpdatesEmail() throws Exception {
        System.setIn(new ByteArrayInputStream("bad\njane@test.com\n".getBytes()));

        EditUserCommand cmd = new EditUserCommand("email");
        cmd.execute(new RecordList());

        assertEquals("jane@test.com", User.getInstance().getEmail());
    }

    @Test
    public void executeEditEmailExhaustAttemptsThrowsResumakeException() throws Exception {
        System.setIn(new ByteArrayInputStream("bad\nbad\nbad\nbad\n".getBytes()));

        EditUserCommand cmd = new EditUserCommand("email");
        assertThrows(ResumakeException.class, () -> cmd.execute(new RecordList()));
        assertEquals("john@example.com", User.getInstance().getEmail());
    }
}
