package seedu.duke;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.duke.Commands.AddCommand;
import seedu.duke.recordtype.Record;

public class AddCommandTest {
    @Test
    public void addedToList(){
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
}
