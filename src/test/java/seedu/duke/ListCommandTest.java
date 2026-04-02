package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.duke.commands.ListCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;

public class ListCommandTest {

    @Test
    public void execute_emptyList_noException() throws Exception {
        RecordList list = new RecordList();
        ListCommand cmd = new ListCommand();
        cmd.execute(list);
        assertEquals(0, list.getSize());
    }

    @Test
    public void execute_listAll_noException() throws Exception {
        RecordList list = new RecordList();
        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));
        list.add(new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1)));

        ListCommand cmd = new ListCommand();
        cmd.execute(list);
        assertEquals(2, list.getSize());
    }

    @Test
    public void execute_filterByType_noException() throws Exception {
        RecordList list = new RecordList();

        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));
        list.add(new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1)));
        list.add(new Cca("Middle Club", "VP", "C++",
                YearMonth.of(2022, 1), YearMonth.of(2023, 1)));

        ListCommand cmd = new ListCommand("C");
        cmd.execute(list);

        // List still has all records, command just filters display
        assertEquals(3, list.getSize());
    }

    @Test
    public void execute_invalidType_throwsException() {
        RecordList list = new RecordList();
        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));

        ListCommand cmd = new ListCommand("Z");

        assertThrows(ResumakeException.class, () -> cmd.execute(list));
    }

    @Test
    public void execute_validTypesNoException() throws Exception {
        RecordList list = new RecordList();
        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));

        new ListCommand("E").execute(list);
        new ListCommand("C").execute(list);
        new ListCommand("P").execute(list);

        // All valid types execute without throwing
        assertEquals(1, list.getSize());
    }
}
