package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.exceptions.ResumakeException;

public class UserTest {

    @BeforeEach
    public void setUp() {
        User.loadFrom("John", 91234567, "john@example.com");
    }

    @Test
    public void editField_validName_updatesName() throws Exception {
        User user = User.getInstance();

        user.editField("name", "Jane");

        assertEquals("Jane", user.getName());
    }

    @Test
    public void editField_validNumber_updatesNumber() throws Exception {
        User user = User.getInstance();

        user.editField("number", "99999999");

        assertEquals(99999999, user.getNumber());
    }

    @Test
    public void editField_invalidNumber_throwsResumakeException() {
        User user = User.getInstance();

        assertThrows(ResumakeException.class, () -> user.editField("number", "not-a-number"));
    }

    @Test
    public void editField_unknownField_throwsResumakeException() {
        User user = User.getInstance();

        assertThrows(ResumakeException.class, () -> user.editField("github", "octocat"));
    }

    @Test
    public void editField_validEmail_updatesEmail() throws Exception {
        User user = User.getInstance();

        user.editField("email", "jane@example.com");

        assertEquals("jane@example.com", user.getEmail());
    }
}
