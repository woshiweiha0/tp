package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.duke.commands.SortCommand;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

public class SortCommandTest {

    @Test
    public void execute_unsortedList_sortsByTitleAlphabetically() throws Exception {
        RecordList list = new RecordList();
        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));
        list.add(new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1)));
        list.add(new Cca("Middle Club", "VP", "C++",
                YearMonth.of(2022, 1), YearMonth.of(2023, 1)));

        SortCommand cmd = new SortCommand();
        cmd.execute(list);

        assertEquals("Alpha Club", list.getRecord(0).getTitle());
        assertEquals("Middle Club", list.getRecord(1).getTitle());
        assertEquals("Zebra Club", list.getRecord(2).getTitle());
    }

    @Test
    public void execute_emptyList_noError() throws Exception {
        RecordList list = new RecordList();
        SortCommand cmd = new SortCommand();
        cmd.execute(list);
        assertEquals(0, list.getSize());
    }

    @Test
    public void execute_singleRecord_remainsUnchanged() throws Exception {
        RecordList list = new RecordList();
        list.add(new Record("Only Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));

        SortCommand cmd = new SortCommand();
        cmd.execute(list);

        assertEquals("Only Club", list.getRecord(0).getTitle());
    }
}
