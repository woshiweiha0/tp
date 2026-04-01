package seedu.duke;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.duke.commands.EditBulletCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditBulletCommandTest {

    @Test
    public void execute_editBullet_success() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        record.addBullet("Wrote tests");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 2, "Improved JUnit coverage");
        command.execute(list);

        assertEquals(2, record.getBullets().size());
        assertEquals("Implemented parser", record.getBullets().get(0));
        assertEquals("Improved JUnit coverage", record.getBullets().get(1));
    }

    @Test
    public void execute_editBullet_invalidBulletIndexThrowsResumakeException() {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 2, "Improved JUnit coverage");

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }

    @Test
    public void execute_editBullet_blankBulletThrowsResumakeException() {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 1, "   ");

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }

    @Test
    public void execute_editFirstBullet_success() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("First");
        record.addBullet("Second");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 1, "Updated First");
        command.execute(list);

        assertEquals("Updated First", record.getBullets().get(0));
        assertEquals("Second", record.getBullets().get(1));
        assertEquals(2, record.getBullets().size());
    }

    @Test
    public void execute_invalidRecordIndexThrowsResumakeException() {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(2, 1, "Updated");

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }

    @Test
    public void execute_blankBullet_originalBulletUnchanged() {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 1, "   ");

        assertThrows(ResumakeException.class, () -> command.execute(list));
        assertEquals("Implemented parser", record.getBullets().get(0));
        assertEquals(1, record.getBullets().size());
    }

    @Test
    public void execute_editBullet_sizeRemainsSame() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("A");
        record.addBullet("B");
        record.addBullet("C");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 2, "Updated B");
        command.execute(list);

        assertEquals(3, record.getBullets().size());
    }
}
