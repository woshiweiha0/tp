package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.FindBulletCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindBulletCommandTest {
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

    private RecordList createRecordListWithBullets() throws ResumakeException {
        RecordList list = new RecordList();
        Record record = new Project(
                "Capo CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.addBullet("Implemented persistent storage with file IO");
        record.addBullet("Designed modular commands for extensibility");
        list.add(record);
        return list;
    }

    @Test
    public void execute_matchingBullet_printsRecordAndMatchingBulletsOnly() throws ResumakeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = createRecordListWithBullets();
        FindBulletCommand command = new FindBulletCommand("persistent");

        command.execute(list);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "1. [P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03"
                + lineSeparator
                + "Bullets:" + lineSeparator
                + "  1. Implemented persistent storage with file IO" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_caseInsensitiveMatch_printsMatchingBullet() throws ResumakeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = createRecordListWithBullets();
        FindBulletCommand command = new FindBulletCommand("PERSISTENT");

        command.execute(list);

        assertTrue(outputStream.toString().contains("Implemented persistent storage with file IO"));
    }

    @Test
    public void execute_multipleMatchingBullets_printsOriginalBulletIndices() throws ResumakeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = new RecordList();
        Record record = new Project(
                "Capo CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.addBullet("foo");
        record.addBullet("bar");
        record.addBullet("foo2");
        list.add(record);

        FindBulletCommand command = new FindBulletCommand("foo");
        command.execute(list);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "1. [P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03"
                + lineSeparator
                + "Bullets:" + lineSeparator
                + "  1. foo" + lineSeparator
                + "  3. foo2" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_noMatchingBullets_printsNoMatchMessage() throws ResumakeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = createRecordListWithBullets();
        FindBulletCommand command = new FindBulletCommand("golang");

        command.execute(list);

        String expected = "No matching bullet points found for keyword: golang" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void constructor_nullKeyword_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FindBulletCommand(null));
    }

    @Test
    public void constructor_blankKeyword_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FindBulletCommand("   "));
    }

    @Test
    public void constructor_nullUi_usesDefaultUiAndStoresKeyword() {
        FindBulletCommand command = new FindBulletCommand("java", null);
        assertEquals("java", command.getKeyword());
    }

    @Test
    public void execute_nullRecordList_printsErrorMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        FindBulletCommand command = new FindBulletCommand("persistent");
        command.execute(null);

        String expected = "Error: RecordList cannot be null." + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void execute_nullRecordInList_ignoredAndValidRecordsStillShown() throws ResumakeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = new RecordList();
        list.add(null);

        Record record = new Project(
                "Capo CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.getBullets().add(null);
        record.addBullet("Implemented persistent storage with file IO");
        list.add(record);

        FindBulletCommand command = new FindBulletCommand("persistent");
        command.execute(list);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "1. [P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03"
                + lineSeparator
                + "Bullets:" + lineSeparator
                + "  2. Implemented persistent storage with file IO" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_containsNullBullet_skipsNullBulletAndPrintsMatch() throws ResumakeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = new RecordList();
        Record record = new Project(
                "Capo CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.getBullets().add(null);
        record.addBullet("Implemented persistent storage with file IO");
        list.add(record);

        FindBulletCommand command = new FindBulletCommand("persistent");
        command.execute(list);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "1. [P] Capo CLI | role: Developer | tech: Java | from: 2026-01 | to: 2026-03"
                + lineSeparator
                + "Bullets:" + lineSeparator
                + "  2. Implemented persistent storage with file IO" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }
}
