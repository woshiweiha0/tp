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
}
