package seedu.duke;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import seedu.duke.commands.EditCommand;
import seedu.duke.recordtype.Record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditCommandTest {

    @Test
    public void execute_editTitle_titleUpdated() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                "New title", null, null, null, null);
        editCommand.execute(recordList);

        assertEquals("New title", record.getTitle());
        assertEquals("Developer", record.getRole());
        assertEquals("Java", record.getTech());
        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
    }

    @Test
    public void execute_editRole_roleUpdated() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, "Team Lead", null, null, null);
        editCommand.execute(recordList);

        assertEquals("Resumake CLI", record.getTitle());
        assertEquals("Team Lead", record.getRole());
        assertEquals("Java", record.getTech());
    }

    @Test
    public void execute_editTech_techUpdated() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, null, "JavaFX", null, null);
        editCommand.execute(recordList);

        assertEquals("Resumake CLI", record.getTitle());
        assertEquals("Developer", record.getRole());
        assertEquals("JavaFX", record.getTech());
    }

    @Test
    public void execute_editFromAndTo_datesUpdated() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, null, null,
                YearMonth.parse("2025-12"),
                YearMonth.parse("2026-04"));
        editCommand.execute(recordList);

        assertEquals(YearMonth.parse("2025-12"), record.getFrom());
        assertEquals(YearMonth.parse("2026-04"), record.getTo());
    }

    @Test
    public void execute_editMultipleFields_onlyTargetRecordUpdated() {
        RecordList recordList = new RecordList();

        Record firstRecord = new Record(
                "First record",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        Record secondRecord = new Record(
                "Second record",
                "Intern",
                "Python",
                YearMonth.parse("2025-12"),
                YearMonth.parse("2026-02")
        );

        recordList.add(firstRecord);
        recordList.add(secondRecord);

        EditCommand editCommand = new EditCommand(1,
                "Updated second record",
                "Team Lead",
                "Spring Boot",
                YearMonth.parse("2025-11"),
                YearMonth.parse("2026-05"));
        editCommand.execute(recordList);

        assertEquals("First record", firstRecord.getTitle());
        assertEquals("Developer", firstRecord.getRole());
        assertEquals("Java", firstRecord.getTech());
        assertEquals(YearMonth.parse("2026-01"), firstRecord.getFrom());
        assertEquals(YearMonth.parse("2026-03"), firstRecord.getTo());

        assertEquals("Updated second record", secondRecord.getTitle());
        assertEquals("Team Lead", secondRecord.getRole());
        assertEquals("Spring Boot", secondRecord.getTech());
        assertEquals(YearMonth.parse("2025-11"), secondRecord.getFrom());
        assertEquals(YearMonth.parse("2026-05"), secondRecord.getTo());
    }

    @Test
    public void execute_validEdit_sizeRemainsSame() {
        RecordList recordList = new RecordList();
        recordList.add(new Record(
                "secondRecord",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        ));

        EditCommand editCommand = new EditCommand(0,
                "Edited record one", null, null, null, null);
        editCommand.execute(recordList);

        assertEquals(1, recordList.getSize());
    }

    @Test
    public void constructor_allFieldsNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new EditCommand(0, null, null, null, null, null));
    }

    @Test
    public void execute_invalidRecordIndex_noChangeToList() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Resumake CLI",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(1,
                "New Title", null, null, null, null);

        editCommand.execute(recordList);

        assertEquals("Resumake CLI", record.getTitle());
        assertEquals("Developer", record.getRole());
        assertEquals("Java", record.getTech());
        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
        assertEquals(1, recordList.getSize());
    }

    @Test
    public void execute_editBlankTitle_noChange() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                "   ", "Team Lead", null, null, null);

        editCommand.execute(recordList);

        assertEquals("Old Title", record.getTitle());
        assertEquals("Team Lead", record.getRole());
        assertEquals("Java", record.getTech());
    }

    @Test
    public void execute_editToBeforeExistingFrom_noChange() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, null, null, null, YearMonth.parse("2025-12"));

        editCommand.execute(recordList);

        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
    }

    @Test
    public void execute_editFromAfterExistingTo_noChange() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, null, null, YearMonth.parse("2026-04"), null);

        editCommand.execute(recordList);

        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
    }

    @Test
    public void executeEditOnlyFromUpdatesFrom() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, null, null, YearMonth.parse("2025-12"), null);

        editCommand.execute(recordList);

        assertEquals(YearMonth.parse("2025-12"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
    }

    @Test
    public void executeEditOnlyToUpdatesTo() {
        RecordList recordList = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0,
                null, null, null, null, YearMonth.parse("2026-04"));

        editCommand.execute(recordList);

        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-04"), record.getTo());
    }
}
