package seedu.duke;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.duke.commands.AddBulletCommand;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.FindCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.recordtype.Record;
import seedu.duke.commands.EditBulletCommand;

public class ParserTest {
    @Test
    public void parse_byeInput_returnsExitCommand() {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void parse_mixedCaseByeInput_returnsExitCommand() {
        Command command = Parser.parse("ByE");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void parse_findInput_returnsFindCommand() {
        Command command = Parser.parse("find java");
        assertInstanceOf(FindCommand.class, command);
        assertEquals("java", ((FindCommand) command).getKeyword());
    }

    @Test
    public void parse_findInputWithExtraSpaces_returnsFindCommand() {
        Command command = Parser.parse("find    java");
        assertInstanceOf(FindCommand.class, command);
        assertEquals("java", ((FindCommand) command).getKeyword());
    }

    @Test
    public void parse_findWithoutKeyword_returnsNull() {
        Command command = Parser.parse("find");
        assertNull(command);
    }

    @Test
    public void parse_unknownCommandInput_returnsNull() {
        Command command = Parser.parse("hello");
        assertNull(command);
    }

    @Test
    public void parse_validProjectInput() throws Exception {
        Command command = Parser.parse(
                "project Capo CLI /role Developer /tech Java /from 2026-01 /to 2026-03"
        );

        assertInstanceOf(AddCommand.class, command);

        RecordList list = new RecordList();
        command.execute(list);

        Record record = list.getRecord(0);
        assertEquals("Capo CLI", record.getTitle());
        assertEquals("Developer", record.getRole());
        assertEquals("Java", record.getTech());
        assertEquals("P", record.getRecordType());
    }

    @Test
    public void parse_validExperienceInput() throws Exception {
        Command command = Parser.parse(
                "experience Google /role SWE Intern /tech JavaScript /from 2025-12 /to 2026-02"
        );

        assertInstanceOf(AddCommand.class, command);

        RecordList list = new RecordList();
        command.execute(list);

        Record record = list.getRecord(0);
        assertEquals("Google", record.getTitle());
        assertEquals("SWE Intern", record.getRole());
        assertEquals("JavaScript", record.getTech());
        assertEquals("E", record.getRecordType());
    }

    @Test
    public void parse_validCcaInput() throws Exception {
        Command command = Parser.parse(
                "cca Tennis /role Captain /tech nil /from 2025-01 /to 2026-01"
        );

        assertInstanceOf(AddCommand.class, command);

        RecordList list = new RecordList();
        command.execute(list);

        Record record = list.getRecord(0);
        assertEquals("Tennis", record.getTitle());
        assertEquals("Captain", record.getRole());
        assertEquals("nil", record.getTech());
        assertEquals("C", record.getRecordType());
    }

    @Test
    public void parse_bulletCommand() {
        Command command = Parser.parse("addbullet 2 / did frontend UI");

        assertInstanceOf(AddBulletCommand.class, command);

        AddBulletCommand abc = (AddBulletCommand) command;

        assertEquals(1, abc.getIndex());
        assertEquals("did frontend UI", abc.getBullet());
    }

    @Test
    public void parse_editTitleOnly_returnsEditCommandAndEditsTitle() throws Exception {
        Command command = Parser.parse("edit 1 New Resume Title");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        command.execute(list);

        assertEquals("New Resume Title", record.getTitle());
        assertEquals("Developer", record.getRole());
        assertEquals("Java", record.getTech());
        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
    }

    @Test
    public void parse_editRoleOnly_returnsEditCommandAndEditsRole() throws Exception{
        Command command = Parser.parse("edit 1 /role Team Lead");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        command.execute(list);

        assertEquals("Old Title", record.getTitle());
        assertEquals("Team Lead", record.getRole());
        assertEquals("Java", record.getTech());
    }

    @Test
    public void parse_editTechOnly_returnsEditCommandAndEditsTech() throws Exception {
        Command command = Parser.parse("edit 1 /tech Spring Boot");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        command.execute(list);

        assertEquals("Spring Boot", record.getTech());
    }

    @Test
    public void parse_editDatesOnly_returnsEditCommandAndEditsDates() throws Exception{
        Command command = Parser.parse("edit 1 /from 2025-12 /to 2026-04");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        command.execute(list);

        assertEquals(YearMonth.parse("2025-12"), record.getFrom());
        assertEquals(YearMonth.parse("2026-04"), record.getTo());
    }

    @Test
    public void parse_editMultipleFields_returnsEditCommandAndEditsAllProvidedFields() throws Exception {
        Command command = Parser.parse(
                "edit 1 New Project Title /role Team Lead /tech JavaFX /from 2025-11 /to 2026-05"
        );
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(record);

        command.execute(list);

        assertEquals("New Project Title", record.getTitle());
        assertEquals("Team Lead", record.getRole());
        assertEquals("JavaFX", record.getTech());
        assertEquals(YearMonth.parse("2025-11"), record.getFrom());
        assertEquals(YearMonth.parse("2026-05"), record.getTo());
    }

    @Test
    public void parse_editWithoutFields_returnsNull() {
        Command command = Parser.parse("edit 1");
        assertNull(command);
    }

    @Test
    public void parse_editBulletCommand() {
        Command command = Parser.parse("editbullet 2 3 / updated frontend UI");

        assertInstanceOf(EditBulletCommand.class, command);
        EditBulletCommand editBulletCommand = (EditBulletCommand) command;

        assertEquals(2, editBulletCommand.getUserRecordIndex());
        assertEquals(3, editBulletCommand.getUserBulletIndex());
        assertEquals("updated frontend UI", editBulletCommand.getNewBullet());
    }
}
