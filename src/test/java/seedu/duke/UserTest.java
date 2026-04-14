package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.exceptions.ResumakeException;

/**
 * Contains unit tests for the {@code User} class.
 *
 * <p>These tests verify that:
 * <ul>
 *     <li>The singleton user can be loaded and accessed correctly.</li>
 *     <li>Valid edits to name, number, and email update the stored user data.</li>
 *     <li>Invalid edits throw {@code ResumakeException} with appropriate messages.</li>
 *     <li>Input trimming and validation logic behave as expected for supported fields.</li>
 * </ul>
 *
 * <p>Interactive prompt-based methods such as {@code userInit()} and the private prompt helpers
 * are not directly unit tested here because they depend on console input through {@code Ui}.
 */
public class UserTest {

    @BeforeEach
    public void setUp() {
        User.resetInstance();

        User.loadFrom("John", 91234567, "john@example.com");
    }

    @Test
    public void loadFrom_validData_getInstanceReturnsLoadedUser() {
        User user = User.getInstance();

        assertEquals("John", user.getName());
        assertEquals(91234567, user.getNumber());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    public void getInstance_calledTwice_returnsSameInstance() {
        User first = User.getInstance();
        User second = User.getInstance();

        assertSame(first, second);
    }

    @Test
    public void editField_validName_updatesName() throws Exception {
        User user = User.getInstance();

        user.editField("name", "Jane");

        assertEquals("Jane", user.getName());
    }

    @Test
    public void editField_validName_trimsWhitespace() throws Exception {
        User user = User.getInstance();

        user.editField("name", "  Jane Doe  ");

        assertEquals("Jane Doe", user.getName());
    }

    @Test
    public void editField_blankName_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("name", "   "));

        assertEquals("Please enter a valid name.", ex.getMessage());
    }

    @Test
    public void editField_nullName_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("name", null));

        assertEquals("Please enter a valid name.", ex.getMessage());
    }

    @Test
    public void editField_validNumber_updatesNumber() throws Exception {
        User user = User.getInstance();

        user.editField("number", "99999999");

        assertEquals(99999999, user.getNumber());
    }

    @Test
    public void editField_validNumber_trimsWhitespace() throws Exception {
        User user = User.getInstance();

        user.editField("number", "  88888888  ");

        assertEquals(88888888, user.getNumber());
    }

    @Test
    public void editField_invalidNumberText_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("number", "not-a-number"));

        assertEquals("Please enter a valid number.", ex.getMessage());
    }

    @Test
    public void editField_zeroNumber_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("number", "0"));

        assertEquals("Please enter a valid number.", ex.getMessage());
    }

    @Test
    public void editField_negativeNumber_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("number", "-5"));

        assertEquals("Please enter a valid number.", ex.getMessage());
    }

    @Test
    public void editField_validEmail_updatesEmail() throws Exception {
        User user = User.getInstance();

        user.editField("email", "jane@example.com");

        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void editField_validEmail_trimsWhitespace() throws Exception {
        User user = User.getInstance();

        user.editField("email", "  jane@example.com  ");

        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void editField_invalidEmail_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("email", "invalid-email"));

        assertEquals("Please enter a valid email.", ex.getMessage());
    }

    @Test
    public void editField_blankEmail_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("email", "   "));

        assertEquals("Please enter a valid email.", ex.getMessage());
    }

    @Test
    public void editField_nullEmail_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("email", null));

        assertEquals("Please enter a valid email.", ex.getMessage());
    }

    @Test
    public void editField_unknownField_throwsResumakeException() {
        User user = User.getInstance();

        ResumakeException ex = assertThrows(ResumakeException.class,
                () -> user.editField("github", "octocat"));

        assertTrue(ex.getMessage().contains("Unknown field: github"));
        assertTrue(ex.getMessage().contains("Use name, number, or email."));
    }

    @Test
    public void getSkillsAsString_noSkills_returnsNoSkillsMessage() {
        User user = User.getInstance();

        assertEquals("No skills added yet.", user.getSkillsAsString());
    }

    @Test
    public void addSkills_oneSkill_skillAppearsInString() {
        User user = User.getInstance();

        user.addSkills("Java");

        assertEquals("java", user.getSkillsAsString());
    }

    @Test
    public void addSkills_sameSkillTwice_skillOnlyAppearsOnceInString() {
        User user = User.getInstance();

        user.addSkills("Java");
        user.addSkills("Java");

        assertEquals("java", user.getSkillsAsString());
    }

    @Test
    public void addSkills_wrappedDoubleQuotes_quotesRemoved() {
        User user = User.getInstance();

        user.addSkills("\"JavaScript\"");

        assertEquals("javascript", user.getSkillsAsString());
    }

    @Test
    public void addSkills_wrappedSingleQuotes_quotesRemoved() {
        User user = User.getInstance();

        user.addSkills("'Python'");

        assertEquals("python", user.getSkillsAsString());
    }

    @Test
    public void addSkills_wrappedCurlyQuotes_quotesRemoved() {
        User user = User.getInstance();

        user.addSkills("\u201CC++\u201D");

        assertEquals("c++", user.getSkillsAsString());
    }

    @Test
    public void addSkills_extraWhitespaceAndQuotes_cleansCorrectly() {
        User user = User.getInstance();

        user.addSkills("   \"Go\"   ");

        assertEquals("go", user.getSkillsAsString());
    }

    @Test
    public void removeSkills_skillAddedOnce_skillRemovedCompletely() {
        User user = User.getInstance();

        user.addSkills("Java");
        user.removeSkills("Java");

        assertEquals("No skills added yet.", user.getSkillsAsString());
    }

    @Test
    public void removeSkills_duplicateSkillRemovedOnce_skillStillExists() {
        User user = User.getInstance();

        user.addSkills("Java");
        user.addSkills("Java");
        user.removeSkills("Java");

        assertEquals("java", user.getSkillsAsString());
    }

    @Test
    public void removeSkills_wrappedQuotedSkill_removesCorrectly() {
        User user = User.getInstance();

        user.addSkills("\"Rust\"");
        user.removeSkills("\"Rust\"");

        assertEquals("No skills added yet.", user.getSkillsAsString());
    }

    @Test
    public void getSkillsAsString_multipleSkills_containsAllSkills() {
        User user = User.getInstance();

        user.addSkills("Java");
        user.addSkills("Python");

        String result = user.getSkillsAsString();
        assertTrue(result.contains("java"));
        assertTrue(result.contains("python"));
    }
}
