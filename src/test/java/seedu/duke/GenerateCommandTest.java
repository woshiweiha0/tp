package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.GenerateCommand;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;


import java.time.YearMonth;

public class GenerateCommandTest {

    @BeforeEach
    public void setUp() {
        User.loadFrom("John", 91234567, "john@example.com");
    }

    @Test
    public void execute_withRecords_noException() throws Exception {
        RecordList list = new RecordList();

        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));
        list.add(new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1)));
        list.add(new Cca("Middle Club", "VP", "C++",
                YearMonth.of(2022, 1), YearMonth.of(2023, 1)));

        GenerateCommand cmd = new GenerateCommand();
        cmd.execute(list);

        // Verifies execution completes without throwing
        assertEquals(3, list.getSize());
    }

    @Test
    public void execute_emptyList_noException() throws Exception {
        RecordList list = new RecordList();
        GenerateCommand cmd = new GenerateCommand();
        cmd.execute(list);
        assertEquals(0, list.getSize());
    }

    @Test
    public void execute_userDataAccessible() throws Exception {
        User user = User.getInstance();
        assertEquals("John", user.getName());
        assertEquals(91234567, user.getNumber());
        assertEquals("john@example.com", user.getEmail());
    }
}
