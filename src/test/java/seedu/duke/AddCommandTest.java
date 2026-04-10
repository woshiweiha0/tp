package seedu.duke;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.duke.commands.AddCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

public class AddCommandTest {
    @BeforeEach
    public void setUp() {
        User.loadFrom("John", 91234567, "john@example.com");
    }

    @Test
    public void addedToList() throws ResumakeException {
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
        assertEquals("Record cannot be null", e.getMessage());
    }

    @Test
    public void execute_nullList_throwsException() throws ResumakeException {
        Record record = new Record(
                "Internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        AddCommand cmd = new AddCommand(record);

        ResumakeException e = assertThrows(ResumakeException.class, () -> cmd.execute(null));
        assertEquals("Record list cannot be null", e.getMessage());
    }

    @Test
    public void execute_validRecord_addsRecord() throws ResumakeException {
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
    }

    @Test
    public void execute_validRecord_printsSuccessMessage() throws Exception {
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
}
