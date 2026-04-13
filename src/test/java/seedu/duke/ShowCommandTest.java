package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import seedu.duke.commands.ShowCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;

public class ShowCommandTest {

    @Test
    void printRecord_validIndex_printsCorrectRecord() {
        RecordList records = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        records.add(record);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            ShowCommand.printRecord(records, 0);
        } finally {
            System.setOut(originalOut);
        }

        String expected = records.getRecord(0) + System.lineSeparator()
                + "  (no bullets)" + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void printRecord_withBullets_printsBullets() throws ResumakeException {
        RecordList records = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.addBullet("Built parser");
        record.addBullet("Wrote tests");
        records.add(record);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            ShowCommand.printRecord(records, 0);
        } finally {
            System.setOut(originalOut);
        }

        String expected = records.getRecord(0) + System.lineSeparator()
                + "  Bullets:" + System.lineSeparator()
                + "  1. Built parser" + System.lineSeparator()
                + "  2. Wrote tests" + System.lineSeparator();

        assertEquals(expected, output.toString());
    }

    @Test
    void printRecord_invalidIndex_throwsIndexOutOfBoundsException() {
        RecordList records = new RecordList();
        assertThrows(IndexOutOfBoundsException.class, () -> ShowCommand.printRecord(records, 0));
    }

    @Test
    void execute_validIndex_printsShowingRecord() {
        RecordList records = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        records.add(record);

        ShowCommand command = new ShowCommand(1);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            command.execute(records);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("Showing record 1"));
        assertTrue(result.contains(record.toString()));
        assertTrue(result.contains("(no bullets)"));
    }

    @Test
    void execute_validIndexWithBullets_printsBullets() throws ResumakeException {
        RecordList records = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.addBullet("Built parser");
        records.add(record);

        ShowCommand command = new ShowCommand(1);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            command.execute(records);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("Showing record 1"));
        assertTrue(result.contains("Bullets:"));
        assertTrue(result.contains("1. Built parser"));
    }

    @Test
    void execute_invalidIndex_printsErrorMessage() {
        RecordList records = new RecordList();
        ShowCommand command = new ShowCommand(1);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            command.execute(records);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("Error: Invalid record index"));
    }

    @Test
    void execute_nullList_throwsAssertionError() {
        ShowCommand command = new ShowCommand(1);
        assertThrows(AssertionError.class, () -> command.execute(null));
    }
}
