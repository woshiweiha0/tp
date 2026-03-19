package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;

import seedu.duke.recordtype.Record;

public class RecordListTest {
    @Test
    public void addRecord_recordAdded_sizeIncreases() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );

        recordList.add(record);

        assertEquals(1,recordList.getSize());
        assertEquals(record, recordList.getRecord(0));
    }
}
