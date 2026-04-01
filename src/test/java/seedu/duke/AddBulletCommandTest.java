package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void addBullet_existingBullets_appendsToEnd() {
        RecordList list = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        record.addBullet("First bullet");
        list.add(record);

        AddBulletCommand command = new AddBulletCommand(0, "Second bullet");
        command.execute(list);

        assertEquals(2, record.getBullets().size());
        assertEquals("First bullet", record.getBullets().get(0));
        assertEquals("Second bullet", record.getBullets().get(1));
    }

    @Test
    public void addBullet_blankBullet_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new AddBulletCommand(0, "   "));
    }

    @Test
    public void addBullet_invalidRecordIndex_noMutation() {
        RecordList list = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        AddBulletCommand command = new AddBulletCommand(1, "Bullet");
        command.execute(list);

        assertEquals(0, record.getBullets().size());
    }
}
