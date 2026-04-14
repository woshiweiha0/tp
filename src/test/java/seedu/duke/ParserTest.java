package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import seedu.duke.commands.AddBulletCommand;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.FindCommand;
import seedu.duke.commands.FindBulletCommand;
import seedu.duke.commands.HelpCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.commands.ShowCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;
import seedu.duke.commands.EditBulletCommand;
import seedu.duke.commands.MoveBulletCommand;

public class ParserTest {
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalIn);
    }

    @BeforeEach
    public void setUp() {
        User.resetInstance();

        User.loadFrom("John", 91234567, "john@example.com");
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void parse_byeInput_returnsExitCommand() throws ResumakeException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void parse_mixedCaseByeInput_returnsExitCommand() throws ResumakeException {
        Command command = Parser.parse("ByE");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void parse_findInput_returnsFindCommand() throws ResumakeException {
        Command command = Parser.parse("find java");
        assertInstanceOf(FindCommand.class, command);
        assertEquals("java", ((FindCommand) command).getKeyword());
    }

    @Test
    public void parse_findInputWithExtraSpaces_returnsFindCommand() throws ResumakeException {
        Command command = Parser.parse("find    java");
        assertInstanceOf(FindCommand.class, command);
        assertEquals("java", ((FindCommand) command).getKeyword());
    }

    @Test
    public void parse_findWithoutKeyword_throwExceptions() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("find"));
    }

    @Test
    public void parse_findBulletInput_returnsFindBulletCommand() throws ResumakeException {
        Command command = Parser.parse("findbullet persistent");
        assertInstanceOf(FindBulletCommand.class, command);
        assertEquals("persistent", ((FindBulletCommand) command).getKeyword());
    }

    @Test
    public void parse_findBulletWithoutKeyword_throwExceptions() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("findbullet"));
    }

    @Test
    public void parse_helpInput_returnsHelpCommand() throws ResumakeException {
        Command command = Parser.parse("help");
        assertInstanceOf(HelpCommand.class, command);
    }

    @Test
    public void parse_helpWithExtraArgument_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("help extra"));
    }

    @Test
    public void parse_showValidIndex_returnsShowCommand() throws ResumakeException {
        Command command = Parser.parse("show 1");
        assertInstanceOf(ShowCommand.class, command);
    }

    @Test
    public void parse_showZeroIndex_throwsException() {
        ResumakeException ex = assertThrows(ResumakeException.class, () -> Parser.parse("show 0"));
        assertEquals("Record index must be positive.", ex.getMessage());
    }

    @Test
    public void parse_showNegativeIndex_throwsException() {
        ResumakeException ex = assertThrows(ResumakeException.class, () -> Parser.parse("show -1"));
        assertEquals("Record index must be positive.", ex.getMessage());
    }

    @Test
    public void parse_unknownCommandInput_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("hello"));
    }

    @Test
    public void parse_validProjectInput() throws Exception {
        provideInput("n");
        Command command = Parser.parse(
                "project Capo CLI /role Developer /tech Java /from 2026-01 /to 2026-03");

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
        provideInput("n");
        Command command = Parser.parse(
                "experience Google /role SWE Intern /tech JavaScript /from 2025-12 /to 2026-02");

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
        provideInput("n");
        Command command = Parser.parse(
                "cca Tennis /role Captain /tech nil /from 2025-01 /to 2026-01");

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
    public void parse_bulletCommand() throws ResumakeException {
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
                YearMonth.parse("2026-03"));
        list.add(record);

        command.execute(list);

        assertEquals("New Resume Title", record.getTitle());
        assertEquals("Developer", record.getRole());
        assertEquals("Java", record.getTech());
        assertEquals(YearMonth.parse("2026-01"), record.getFrom());
        assertEquals(YearMonth.parse("2026-03"), record.getTo());
    }

    @Test
    public void parse_editRoleOnly_returnsEditCommandAndEditsRole() throws Exception {
        Command command = Parser.parse("edit 1 /role Team Lead");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03"));
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
                YearMonth.parse("2026-03"));
        list.add(record);

        command.execute(list);

        assertEquals("Spring Boot", record.getTech());
    }

    @Test
    public void parse_editDatesOnly_returnsEditCommandAndEditsDates() throws Exception {
        Command command = Parser.parse("edit 1 /from 2025-12 /to 2026-04");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03"));
        list.add(record);

        command.execute(list);

        assertEquals(YearMonth.parse("2025-12"), record.getFrom());
        assertEquals(YearMonth.parse("2026-04"), record.getTo());
    }

    @Test
    public void parse_editMultipleFields_returnsEditCommandAndEditsAllProvidedFields() throws Exception {
        Command command = Parser.parse(
                "edit 1 New Project Title /role Team Lead /tech JavaFX /from 2025-11 /to 2026-05");
        assertInstanceOf(EditCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03"));
        list.add(record);

        command.execute(list);

        assertEquals("New Project Title", record.getTitle());
        assertEquals("Team Lead", record.getRole());
        assertEquals("JavaFX", record.getTech());
        assertEquals(YearMonth.parse("2025-11"), record.getFrom());
        assertEquals(YearMonth.parse("2026-05"), record.getTo());
    }

    @Test
    public void parse_editWithoutFields_throwException() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1"));
    }

    @Test
    public void parse_editBulletCommand() throws ResumakeException {
        Command command = Parser.parse("editbullet 2 3 / updated frontend UI");

        assertInstanceOf(EditBulletCommand.class, command);
        EditBulletCommand editBulletCommand = (EditBulletCommand) command;

        assertEquals(2, editBulletCommand.getUserRecordIndex());
        assertEquals(3, editBulletCommand.getUserBulletIndex());
        assertEquals("updated frontend UI", editBulletCommand.getNewBullet());
    }

    @Test
    public void parseMovebulletCommandValidInput() throws ResumakeException {
        Command command = Parser.parse("movebullet 1 1 3");
        assertInstanceOf(MoveBulletCommand.class, command);
    }

    @Test
    public void parseMovebulletCommandExecutesWithExpectedIndexes() throws Exception {
        Command command = Parser.parse("movebullet 1 1 3");
        assertInstanceOf(MoveBulletCommand.class, command);

        RecordList list = new RecordList();
        Record record = new Record(
                "Old Title",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03"));
        record.addBullet("A");
        record.addBullet("B");
        record.addBullet("C");
        list.add(record);

        command.execute(list);

        assertEquals("B", record.getBullets().get(0));
        assertEquals("C", record.getBullets().get(1));
        assertEquals("A", record.getBullets().get(2));
    }

    @Test
    public void parseMovebulletCommandMissingArguments() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet"));
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet 1"));
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet 1 2"));
    }

    @Test
    public void parseMovebulletCommandNonnumericArguments() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet x 1 2"));
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet 1 y 2"));
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet 1 2 z"));
    }

    @Test
    public void parseMovebulletCommandExtraArguments() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("movebullet 1 2 3 4"));
    }

    @Test
    public void parse_editInvalidIndex_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit x New Title"));
    }

    @Test
    public void parse_editBlankRole_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1 /role   "));
    }

    @Test
    public void parse_editBlankTech_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1 /tech   "));
    }

    @Test
    public void parse_editInvalidFromDate_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1 /from 2026/01"));
    }

    @Test
    public void parse_editInvalidToDate_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1 /to march"));
    }

    @Test
    public void parse_editToBeforeFrom_returnsNull() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1 /from 2026-05 /to 2026-04"));
    }

    @Test
    public void parse_addbulletMissingSlash_throwsException() throws ResumakeException {
        assertThrows(ResumakeException.class, () -> Parser.parse("addbullet 1 missing slash"));
    }

    @Test
    public void parse_addbulletZeroIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("addbullet 0 / test bullet"));
    }

    @Test
    public void parse_edituserMissingField_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("edituser"));
    }

    @Test
    public void parse_projectBlankRole_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse(
                "project Demo /role /tech Java /from 2026-01 /to 2026-02"));
    }

    @Test
    public void parse_projectBlankTech_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse(
                "project Demo /role Developer /tech /from 2026-01 /to 2026-02"));
    }

    @Test
    public void parse_projectMissingFromValue_throwsFromDateFormatMessage() {
        ResumakeException exception = assertThrows(ResumakeException.class, () -> Parser.parse(
                "project Test /role Dev /tech Java /from /to 2025-12"));
        assertEquals("from date must be in yyyy-MM format.", exception.getMessage());
    }

    @Test
    public void parse_projectUnknownField_throwsInvalidFieldMessage() {
        ResumakeException exception = assertThrows(ResumakeException.class, () -> Parser.parse(
                "project MyApp /role Dev /tech Java /from 2026-01 /to 2026-02 /salary 5000"));
        assertEquals("\"/salary\" is not a valid field. Please use /role, /tech, /from, and /to only.",
                exception.getMessage());
    }

    @Test
    public void parse_projectTitleContainingSlashToken_parsesCorrectly() throws Exception {
        provideInput("n");
        Command command = Parser.parse(
                "project My/role App /role Developer /tech Java /from 2026-01 /to 2026-03");
        assertInstanceOf(AddCommand.class, command);

        RecordList list = new RecordList();
        command.execute(list);

        Record record = list.getRecord(0);
        assertEquals("My/role App", record.getTitle());
        assertEquals("Developer", record.getRole());
    }

    @Test
    public void parse_projectDuplicateField_throwsException() {
        ResumakeException exception = assertThrows(ResumakeException.class, () -> Parser.parse(
                "project Demo /role First /role Second /tech Java /from 2026-01 /to 2026-03"));
        assertEquals("Duplicate field \"/role\" is not allowed.", exception.getMessage());
    }

    @Test
    public void parse_editDuplicateField_throwsException() {
        ResumakeException exception = assertThrows(ResumakeException.class, () -> Parser.parse(
                "edit 1 New Title /role First /role Second"));
        assertEquals("Duplicate field \"/role\" is not allowed.", exception.getMessage());
    }

    @Test
    public void parse_editbulletBlankNewBullet_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("editbullet 1 1 /   "));
    }

    @Test
    public void parse_addbulletMissingBulletText_throwsException() throws ResumakeException {
        ResumakeException exception = assertThrows(ResumakeException.class, () -> Parser.parse("addbullet 1 /"));
        assertEquals("Bullet text cannot be blank.", exception.getMessage());
    }

    @Test
    public void parse_editZeroIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 0 new title"));
    }

    @Test
    public void parse_editNegativeIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit -1 new title"));
    }

    @Test
    public void parse_editOnlySpaces_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("edit 1      "));
    }

    @Test
    public void parse_addbulletNegativeIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("addbullet -1 / test bullet"));
    }

    @Test
    public void parse_editbulletZeroRecordIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("editbullet 0 1 / updated bullet"));
    }

    @Test
    public void parse_editbulletNegativeRecordIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("editbullet -1 1 / updated bullet"));
    }

    @Test
    public void parse_editbulletZeroBulletIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("editbullet 1 0 / updated bullet"));
    }

    @Test
    public void parse_editbulletNegativeBulletIndex_throwsException() {
        assertThrows(ResumakeException.class, () -> Parser.parse("editbullet 1 -1 / updated bullet"));
    }

    @Test
    public void parse_editWithLargeIndex_succeeds() throws ResumakeException {
        Command command = Parser.parse("edit 999999 new title");
        assertInstanceOf(EditCommand.class, command);
    }

    @Test
    public void parse_addbulletValidInput_succeeds() throws ResumakeException {
        Command command = Parser.parse("addbullet 1 / test bullet point");
        assertInstanceOf(AddBulletCommand.class, command);
    }
}
