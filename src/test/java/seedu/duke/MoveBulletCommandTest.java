package seedu.duke;

import java.time.YearMonth;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.MoveBulletCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveBulletCommandTest {
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        User.resetInstance();

        User.loadFrom("John", 91234567, "john@example.com");
    }

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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 1, 1);
        command.execute(list);

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
        assertEquals("--------------------" + System.lineSeparator()
                        + "No changes made: bullet is already at position 2 in record 1."
                        + System.lineSeparator()
                        + "--------------------" + System.lineSeparator(),
                outputStream.toString());
    }

    @Test
    public void execute_moveBulletSameOutOfRangeIndex_throwsInvalidBulletIndex() throws ResumakeException {
        RecordList list = new RecordList();
        Record record = createRecordWithThreeBullets();
        list.add(record);

        MoveBulletCommand command = new MoveBulletCommand(0, 98, 98);
        ResumakeException ex = assertThrows(ResumakeException.class, () -> command.execute(list));
        assertEquals("Invalid bullet index.", ex.getMessage());

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
        ResumakeException ex = assertThrows(ResumakeException.class, () -> command.execute(list));
        assertEquals("Invalid record index.", ex.getMessage());

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
        ResumakeException ex = assertThrows(ResumakeException.class, () -> command.execute(list));
        assertEquals("Invalid record index.", ex.getMessage());

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
        ResumakeException ex = assertThrows(ResumakeException.class, () -> command.execute(list));
        assertEquals("Invalid bullet index.", ex.getMessage());

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
        ResumakeException ex = assertThrows(ResumakeException.class, () -> command.execute(list));
        assertEquals("Invalid bullet index.", ex.getMessage());

        assertEquals(3, record.getBullets().size());
        assertEquals("A", record.getBullets().get(0));
        assertEquals("B", record.getBullets().get(1));
        assertEquals("C", record.getBullets().get(2));
    }

    @Test
    public void execute_nullRecordList_noThrow() {
        MoveBulletCommand command = new MoveBulletCommand(0, 0, 1);

        ResumakeException ex = assertThrows(ResumakeException.class, () -> command.execute(null));
        assertEquals("RecordList cannot be null.", ex.getMessage());
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
