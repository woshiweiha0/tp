package seedu.duke;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.duke.commands.MoveBulletCommand;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveBulletCommandTest {

    private Record createRecordWithThreeBullets() {
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("A");
        record.addBullet("B");
        record.addBullet("C");
        return record;
    }

    @Test
    public void execute_moveBulletForward_success() {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 0, 2);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("B", record.getBullets().get(0));
        assertEquals("C", record.getBullets().get(1));
        assertEquals("A", record.getBullets().get(2));
    }

    @Test
    public void execute_moveBulletBackward_success() {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 2, 0);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("C", record.getBullets().get(0));
        assertEquals("A", record.getBullets().get(1));
        assertEquals("B", record.getBullets().get(2));
    }

    @Test
    public void execute_moveBulletSameIndex_noChange() {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 1, 1);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }

    @Test
    public void execute_invalidRecordIndex_noMutation() {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(1, 0, 1);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }

    @Test
    public void execute_invalidFromBulletIndex_noMutation() {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, -1, 1);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }

    @Test
    public void execute_invalidToBulletIndex_noMutation() {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 0, 3);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }

    @Test
    public void execute_nullRecordList_noThrow() {
        MoveBulletCommand command = new MoveBulletCommand(0, 0, 1);

        assertDoesNotThrow(() -> command.execute(null));
    }
}
