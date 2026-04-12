package seedu.duke;

import java.time.YearMonth;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.MoveBulletCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveBulletCommandTest {
    private final PrintStream originalOut = System.out;

    @AfterEach
    public void restoreSystemStreams() {
        System.setOut(originalOut);
    }

    private Record createRecordWithThreeBullets() throws ResumakeException {
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("A");
        record.addBullet("B");
        record.addBullet("C");
        return record;
    }

    @Test
    public void execute_moveBulletForward_success() throws ResumakeException {
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
    public void execute_moveBulletBackward_success() throws ResumakeException {
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
    public void execute_moveBulletSameIndex_noChange() throws ResumakeException {
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
    public void execute_invalidRecordIndex_noMutation() throws ResumakeException {
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
    public void execute_negativeRecordIndex_noMutation() throws ResumakeException {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(-1, 0, 1);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }

    @Test
    public void execute_invalidFromBulletIndex_noMutation() throws ResumakeException {
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
    public void execute_invalidToBulletIndex_noMutation() throws ResumakeException {
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        MoveBulletCommand command = new MoveBulletCommand(0, 0, 1);

        assertDoesNotThrow(() -> command.execute(null));
        assertEquals("--------------------" + System.lineSeparator()
                        + "Error: RecordList cannot be null." + System.lineSeparator()
                        + "--------------------" + System.lineSeparator(),
                outputStream.toString());
    }

    @Test
    public void constructor_nullUi_executesNormally() throws ResumakeException {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 0, 1, null);
        command.execute(list);

        assertEquals("B", record.getBullets().get(0));
        assertEquals("A", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }
}
