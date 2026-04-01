package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.recordtype.Record;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecordTest {

    private Record createRecord() {
        return new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
    }

    @Test
    public void setTitle_validTitle_updatesTitle() {
        Record record = createRecord();
        record.setTitle("New Title");
        assertEquals("New Title", record.getTitle());
    }

    @Test
    public void setTitle_blankTitle_throwsIllegalArgumentException() {
        Record record = createRecord();
        assertThrows(IllegalArgumentException.class, () -> record.setTitle("   "));
    }

    @Test
    public void setRole_validRole_updatesRole() {
        Record record = createRecord();
        record.setRole("Team Lead");
        assertEquals("Team Lead", record.getRole());
    }

    @Test
    public void setRole_blankRole_throwsIllegalArgumentException() {
        Record record = createRecord();
        assertThrows(IllegalArgumentException.class, () -> record.setRole("   "));
    }

    @Test
    public void setTech_validTech_updatesTech() {
        Record record = createRecord();
        record.setTech("JavaFX");
        assertEquals("JavaFX", record.getTech());
    }

    @Test
    public void setTech_blankTech_throwsIllegalArgumentException() {
        Record record = createRecord();
        assertThrows(IllegalArgumentException.class, () -> record.setTech("   "));
    }

    @Test
    public void setFrom_validDate_updatesFrom() {
        Record record = createRecord();
        record.setFrom(YearMonth.parse("2025-12"));
        assertEquals(YearMonth.parse("2025-12"), record.getFrom());
    }

    @Test
    public void setFrom_afterTo_throwsIllegalArgumentException() {
        Record record = createRecord();
        assertThrows(IllegalArgumentException.class,
                () -> record.setFrom(YearMonth.parse("2026-04")));
    }

    @Test
    public void setTo_validDate_updatesTo() {
        Record record = createRecord();
        record.setTo(YearMonth.parse("2026-04"));
        assertEquals(YearMonth.parse("2026-04"), record.getTo());
    }

    @Test
    public void setTo_beforeFrom_throwsIllegalArgumentException() {
        Record record = createRecord();
        assertThrows(IllegalArgumentException.class,
                () -> record.setTo(YearMonth.parse("2025-12")));
    }

    @Test
    public void addBullet_validBullet_addsBullet() {
        Record record = createRecord();
        record.addBullet("Implemented parser");
        assertEquals(1, record.getBullets().size());
        assertEquals("Implemented parser", record.getBullets().get(0));
    }

    @Test
    public void editBullet_validBullet_updatesBullet() {
        Record record = createRecord();
        record.addBullet("Old bullet");
        record.editBullet(0, "New bullet");
        assertEquals("New bullet", record.getBullets().get(0));
    }

    @Test
    public void editBullet_blankBullet_throwsIllegalArgumentException() {
        Record record = createRecord();
        record.addBullet("Old bullet");
        assertThrows(IllegalArgumentException.class,
                () -> record.editBullet(0, "   "));
    }

    @Test
    public void deleteBullet_validIndex_removesBullet() {
        Record record = createRecord();
        record.addBullet("A");
        record.addBullet("B");
        String removed = record.deleteBullet(0);

        assertEquals("A", removed);
        assertEquals(1, record.getBullets().size());
        assertEquals("B", record.getBullets().get(0));
    }

    @Test
    public void moveBullet_validIndexes_movesBullet() {
        Record record = createRecord();
        record.addBullet("A");
        record.addBullet("B");
        record.addBullet("C");

        record.moveBullet(0, 2);

        assertEquals("B", record.getBullets().get(0));
        assertEquals("C", record.getBullets().get(1));
        assertEquals("A", record.getBullets().get(2));
    }
}
