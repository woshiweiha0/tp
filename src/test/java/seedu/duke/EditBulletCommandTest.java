package seedu.duke;

import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.EditBulletCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditBulletCommandTest {

    @BeforeEach
    public void setUp() {
        User.resetInstance();

        User.loadFrom("John", 91234567, "john@example.com");
    }

    @Test
    public void execute_editBullet_success() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        record.addBullet("Wrote tests");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 2, "Improved JUnit coverage", new Ui());
        command.execute(list);

        assertEquals(2, record.getBullets().size());
        assertEquals("Implemented parser", record.getBullets().get(0));
        assertEquals("Improved JUnit coverage", record.getBullets().get(1));
    }

    @Test
    public void execute_editBullet_invalidBulletIndexThrowsResumakeException() throws ResumakeException {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 2, "Improved JUnit coverage", new Ui());

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }

    @Test
    public void constructor_blankBullet_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EditBulletCommand(1, 1, "   ", new Ui()));
    }

    @Test
    public void execute_editFirstBullet_success() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("First");
        record.addBullet("Second");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 1, "Updated First", new Ui());
        command.execute(list);

        assertEquals("Updated First", record.getBullets().get(0));
        assertEquals("Second", record.getBullets().get(1));
        assertEquals(2, record.getBullets().size());
    }

    @Test
    public void execute_invalidRecordIndexThrowsResumakeException() throws ResumakeException {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Implemented parser");
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(2, 1, "Updated", new Ui());

        assertThrows(ResumakeException.class, () -> command.execute(list));
    }

    @Test
    public void constructor_zeroRecordIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EditBulletCommand(0, 1, "text", new Ui()));
    }

    @Test
    public void constructor_zeroBulletIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EditBulletCommand(1, 0, "text", new Ui()));
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

        EditBulletCommand command = new EditBulletCommand(1, 2, "Updated B", new Ui());
        command.execute(list);

        assertEquals(3, record.getBullets().size());
    }

    @Test
    public void execute_largeValidBulletIndex_succeeds() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));

        // Add many bullets
        for (int i = 0; i < 100; i++) {
            record.addBullet("Bullet " + i);
        }
        list.add(record);

        EditBulletCommand command = new EditBulletCommand(1, 100, "Updated last bullet", new Ui());
        command.execute(list);

        assertEquals("Updated last bullet", record.getBullets().get(99));
    }

    @Test
    public void execute_specialCharactorsInBullet_succeeds() throws Exception {
        RecordList list = new RecordList();
        Record record = new Project("Capo CLI", "Developer", "Java",
                YearMonth.parse("2026-01"), YearMonth.parse("2026-03"));
        record.addBullet("Original");
        list.add(record);

        String specialBullet = "Used <html> & \"quotes\" & symbols: @#$%^&*()";
        EditBulletCommand command = new EditBulletCommand(1, 1, specialBullet, new Ui());
        command.execute(list);

        assertEquals(specialBullet, record.getBullets().get(0));
    }

    @Test
    public void execute_negativeBulletIndex_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new EditBulletCommand(1, -1, "text", new Ui()));
    }

    @Test
    public void execute_negativeRecordIndex_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new EditBulletCommand(-1, 1, "text", new Ui()));
    }
}
