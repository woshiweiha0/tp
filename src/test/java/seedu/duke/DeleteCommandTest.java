package seedu.duke;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.duke.commands.DeleteCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandTest {
    @Test
    public void execute_deleteRecord_success() throws Exception {
        RecordList list = new RecordList();
        list.add(new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03")));

        DeleteCommand command = new DeleteCommand(1);
        command.execute(list);

        assertEquals(0, list.getSize());
    }

    @Test
    public void execute_deleteRecord_invalidIndexThrowsResumakeException() {
        RecordList list = new RecordList();

        DeleteCommand command = new DeleteCommand(1);

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }

    @Test
    public void execute_deleteBullet_success() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        record.addBullet("Wrote tests");
        list.add(record);

        DeleteCommand command = new DeleteCommand(1, 1);
        command.execute(list);

        assertEquals(1, record.getBullets().size());
        assertEquals("Wrote tests", record.getBullets().get(0));
    }

    @Test
    public void execute_deleteBullet_invalidIndexThrowsResumakeException() {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        list.add(record);

        DeleteCommand command = new DeleteCommand(1, 1);

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }
}
