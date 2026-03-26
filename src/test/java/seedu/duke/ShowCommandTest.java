package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import seedu.duke.commands.ShowCommand;
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

        ShowCommand.printRecord(records, 0);

        System.setOut(originalOut);

        assertEquals(records.getRecord(0) + System.lineSeparator(), output.toString());
    }
}
