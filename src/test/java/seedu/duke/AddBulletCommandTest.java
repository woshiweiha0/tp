package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;

import seedu.duke.commands.AddBulletCommand;
import seedu.duke.recordtype.Record;

public class AddBulletCommandTest {
    @Test
    public void addBulletToProjectTest() {
        RecordList list = new RecordList();
        seedu.duke.recordtype.Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        AddBulletCommand command = new AddBulletCommand(0, "Implemented parser logic");
        command.execute(list);

        assertEquals(1,record.getBullets().size());
        assertEquals("Implemented parser logic", record.getBullets().get(0));
    }
}
