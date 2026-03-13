package seedu.duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommandTest {

    @Test
    public void execute_validIndex_recordDescriptionUpdated() {
        RecordList recordList = new RecordList();
        Record record = new Record("Old description");
        recordList.add(record);

        EditCommand editCommand = new EditCommand(0, "New description");
        editCommand.execute(recordList);

        assertEquals("New description", record.description);
    }

    @Test
    public void execute_validIndexOnlyTargetRecordUpdated() {
        RecordList recordList = new RecordList();
        Record firstRecord = new Record("First record");
        Record secondRecord = new Record("Second record");
        recordList.add(firstRecord);
        recordList.add(secondRecord);

        EditCommand editCommand = new EditCommand(1, "Updated second record");
        editCommand.execute(recordList);

        assertEquals("First record", firstRecord.description);
        assertEquals("Updated second record", secondRecord.description);
    }

    @Test
    public void execute_validIndex_sizeRemainsSame() {
        RecordList recordList = new RecordList();
        recordList.add(new Record("Record one"));

        EditCommand editCommand = new EditCommand(0, "Edited record one");
        editCommand.execute(recordList);

        assertEquals(1, recordList.getSize());
    }
}