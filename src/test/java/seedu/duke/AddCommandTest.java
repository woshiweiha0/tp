package seedu.duke;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.duke.commands.AddCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

public class AddCommandTest {
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalIn);
    }

    @BeforeEach
    public void setUp() {
        User.loadFrom("John", 91234567, "john@example.com");
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void addedToList() throws ResumakeException {
        provideInput("n");
        RecordList list = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );

        AddCommand command = new AddCommand(record);
        command.execute(list);

        assertEquals(1,list.getSize());
        assertEquals(record, list.getRecord(0));
    }

    @Test
    public void constructor_nullRecord_throwsException() {
        ResumakeException e = assertThrows(ResumakeException.class, () -> new AddCommand(null));
        assertEquals("Record cannot be null.", e.getMessage());
    }

    @Test
    public void constructor_emptyTitle_throwsException() {
        Record record = new EmptyTitleRecord();

        ResumakeException e = assertThrows(ResumakeException.class, () -> new AddCommand(record));
        assertEquals("Record title cannot be empty.", e.getMessage());
    }

    @Test
    public void execute_nullList_throwsException() throws ResumakeException {
        provideInput("n");
        Record record = new Record(
                "Internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        AddCommand cmd = new AddCommand(record);

        ResumakeException e = assertThrows(ResumakeException.class, () -> cmd.execute(null));
        assertEquals("Record list cannot be null.", e.getMessage());
    }

    @Test
    public void execute_declinesBullets_noBulletsAdded() throws ResumakeException {
        provideInput("n");
        RecordList list = new RecordList();
        Record record = new Record(
                "Internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        AddCommand cmd = new AddCommand(record);

        int before = list.getSize();
        cmd.execute(list);

        assertEquals(before + 1, list.getSize());
        assertEquals(0, record.getBullets().size());
    }

    @Test
    public void execute_validRecord_printsSuccessMessage() throws ResumakeException {
        provideInput("n");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            RecordList list = new RecordList();
            Record record = new Record(
                    "Internship",
                    "Developer",
                    "Java",
                    YearMonth.parse("2026-01"),
                    YearMonth.parse("2026-03")
            );
            AddCommand cmd = new AddCommand(record);

            cmd.execute(list);

            String output = out.toString();
            assertTrue(output.contains("Internship added"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void execute_duplicateRecord_throwsResumakeException() throws ResumakeException {
        provideInput("n");
        RecordList list = new RecordList();
        Record record = new Record(
                "Internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );

        AddCommand firstCommand = new AddCommand(record, new Ui());
        firstCommand.execute(list);

        Record duplicate = new Record(
                "Internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        AddCommand secondCommand = new AddCommand(duplicate, new Ui());

        assertThrows(ResumakeException.class, () -> secondCommand.execute(list));
        assertEquals(1, list.getSize());
        assertEquals(0, record.getBullets().size());
    }

    @Test
    public void execute_userAddsBullets_bulletsAddedToRecord() throws ResumakeException {
        provideInput("y" + System.lineSeparator()
                + "organized hacknroll" + System.lineSeparator()
                + "esc" + System.lineSeparator()
                + "esc" + System.lineSeparator());
        RecordList list = new RecordList();
        Record record = new Record(
                "NUS Hackers",
                "Core Member",
                "Python",
                YearMonth.parse("2025-01"),
                YearMonth.parse("2026-01")
        );

        AddCommand command = new AddCommand(record, new Ui());
        command.execute(list);

        assertEquals(1, list.getSize());
        assertEquals(1, record.getBullets().size());
        assertEquals("organized hacknroll", record.getBullets().get(0));
    }

    @Test
    public void execute_userAddsMultipleBullets_bulletsAddedInOrder() throws ResumakeException {
        provideInput("Y" + System.lineSeparator()
                + "organized hacknroll" + System.lineSeparator()
                + "press esc" + System.lineSeparator()
                + "esc" + System.lineSeparator());
        RecordList list = new RecordList();
        Record record = new Record(
                "NUS Hackers",
                "Core Member",
                "Python",
                YearMonth.parse("2025-01"),
                YearMonth.parse("2026-01")
        );

        AddCommand command = new AddCommand(record, new Ui());
        command.execute(list);

        assertEquals(1, list.getSize());
        assertEquals(2, record.getBullets().size());
        assertEquals("organized hacknroll", record.getBullets().get(0));
        assertEquals("press esc", record.getBullets().get(1));
    }

    @Test
    public void execute_invalidAddBulletsPromptAnswer_promptsAgainAndAddsBullet() throws ResumakeException {
        provideInput("maybe" + System.lineSeparator()
                + "y" + System.lineSeparator()
                + "organized hacknroll" + System.lineSeparator()
                + "esc" + System.lineSeparator());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            RecordList list = new RecordList();
            Record record = new Record(
                    "NUS Hackers",
                    "Core Member",
                    "Python",
                    YearMonth.parse("2025-01"),
                    YearMonth.parse("2026-01")
            );

            AddCommand command = new AddCommand(record, new Ui());
            command.execute(list);

            assertEquals(1, list.getSize());
            assertEquals(1, record.getBullets().size());
            assertEquals("organized hacknroll", record.getBullets().get(0));
            assertTrue(out.toString().contains("Please enter y or n."));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void execute_userImmediatelyEscapes_noBulletsAdded() throws ResumakeException {
        provideInput("y" + System.lineSeparator()
                + "esc" + System.lineSeparator());
        RecordList list = new RecordList();
        Record record = new Record(
                "NUS Hackers",
                "Core Member",
                "Python",
                YearMonth.parse("2025-01"),
                YearMonth.parse("2026-01")
        );

        AddCommand command = new AddCommand(record, new Ui());
        command.execute(list);

        assertEquals(1, list.getSize());
        assertEquals(0, record.getBullets().size());
    }

    @Test
    public void execute_blankBullet_skipsBlankAndContinues() throws ResumakeException {
        provideInput("y" + System.lineSeparator()
                + "   " + System.lineSeparator()
                + "organized hacknroll" + System.lineSeparator()
                + "esc" + System.lineSeparator());
        RecordList list = new RecordList();
        Record record = new Record(
                "NUS Hackers",
                "Core Member",
                "Python",
                YearMonth.parse("2025-01"),
                YearMonth.parse("2026-01")
        );

        AddCommand command = new AddCommand(record, new Ui());
        command.execute(list);

        assertEquals(1, list.getSize());
        assertEquals(1, record.getBullets().size());
        assertEquals("organized hacknroll", record.getBullets().get(0));
    }

    @Test
    public void execute_duplicateBullet_skipsDuplicateAndContinues() throws ResumakeException {
        provideInput("y" + System.lineSeparator()
                + "organized hacknroll" + System.lineSeparator()
                + "organized hacknroll" + System.lineSeparator()
                + "press esc" + System.lineSeparator()
                + "esc" + System.lineSeparator());
        RecordList list = new RecordList();
        Record record = new Record(
                "NUS Hackers",
                "Core Member",
                "Python",
                YearMonth.parse("2025-01"),
                YearMonth.parse("2026-01")
        );

        AddCommand command = new AddCommand(record, new Ui());
        command.execute(list);

        assertEquals(1, list.getSize());
        assertEquals(2, record.getBullets().size());
        assertEquals("organized hacknroll", record.getBullets().get(0));
        assertEquals("press esc", record.getBullets().get(1));
    }

    @Test
    public void execute_nullUi_usesDefaultUi() throws ResumakeException {
        provideInput("n");
        RecordList list = new RecordList();
        Record record = new Record(
                "Internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );

        AddCommand command = new AddCommand(record, null);
        command.execute(list);

        assertEquals(1, list.getSize());
        assertEquals(record, list.getRecord(0));
    }

    private static class EmptyTitleRecord extends Record {
        EmptyTitleRecord() {
            super("Internship", "Developer", "Java",
                    YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
            this.title = "   ";
        }
    }
}
