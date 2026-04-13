package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.duke.commands.FindCommand;
import seedu.duke.recordtype.Record;

public class FindCommandTest {
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        User.loadFrom("John", 91234567, "john@example.com");
    }

    private RecordList fillRecordList() {
        RecordList recordList = new RecordList();
        Record javaProject = new Record(
                "Java project",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        Record pythonIntern = new Record(
                "Python internship",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        Record fullStackApp = new Record(
                "Full stack Java app",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );

        recordList.add(javaProject);
        recordList.add(pythonIntern);
        recordList.add(fullStackApp);
        return recordList;
    }

    @AfterEach
    public void restoreSystemStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void execute_matchingRecords_printsMatches() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList recordList = fillRecordList();

        FindCommand findCommand = new FindCommand("java");
        findCommand.execute(recordList);

        String dashedLine = "--------------------";
        String lineSeparator = System.lineSeparator();

        String firstMatch = "1. [R] Java project | role: Developer | tech: Java | "
                + "from: 2026-01 | to: 2026-03";
        String secondMatch = "2. [R] Python internship | role: Developer | tech: Java | "
                + "from: 2026-01 | to: 2026-03";
        String thirdMatch = "3. [R] Full stack Java app | role: Developer | tech: Java | "
                + "from: 2026-01 | to: 2026-03";

        String expectedOutput = dashedLine + lineSeparator
                + "Matching records:" + lineSeparator
                + firstMatch + lineSeparator
                + secondMatch + lineSeparator
                + thirdMatch + lineSeparator
                + dashedLine + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_noMatchingRecords_printsNoMatchMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList recordList = fillRecordList();

        FindCommand findCommand = new FindCommand("golang");
        findCommand.execute(recordList);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "--------------------" + lineSeparator
                + "Matching records:" + lineSeparator
                + "No matching records found for keyword: golang" + lineSeparator
                + "--------------------" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_caseInsensitiveKeyword_printsMatches() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList recordList = new RecordList();
        Record javaCapstone = new Record(
                "JAVA capstone",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        recordList.add(javaCapstone);

        FindCommand findCommand = new FindCommand("java");
        findCommand.execute(recordList);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "--------------------" + lineSeparator
                + "Matching records:" + lineSeparator
                + "1. [R] JAVA capstone | role: Developer | tech: Java | from: 2026-01 | to: 2026-03" + lineSeparator
                + "--------------------" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void execute_sparseMatch_printsOriginalListIndex() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        RecordList recordList = fillRecordList();

        FindCommand findCommand = new FindCommand("internship");
        findCommand.execute(recordList);

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "--------------------" + lineSeparator
                + "Matching records:" + lineSeparator
                + "2. [R] Python internship | role: Developer | tech: Java | from: 2026-01 | to: 2026-03"
                + lineSeparator
                + "--------------------" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void constructor_nullKeyword_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FindCommand(null));
    }

    @Test
    public void constructor_blankKeyword_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FindCommand("   "));
    }

    @Test
    public void constructor_nullUi_usesDefaultUiAndStoresKeyword() {
        FindCommand findCommand = new FindCommand("java", null);
        assertEquals("java", findCommand.getKeyword());
    }

    @Test
    public void execute_nullRecordList_printsErrorMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        FindCommand findCommand = new FindCommand("java");
        findCommand.execute(null);

        assertTrue(outputStream.toString().contains("RecordList cannot be null"));
    }

    @Test
    public void execute_nullRecordInList_throwsAssertionError() {
        RecordList list = new RecordList();
        list.add(null);
        list.add(new Record(
                "Java capstone",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        ));

        FindCommand findCommand = new FindCommand("java");
        assertThrows(AssertionError.class, () -> findCommand.execute(list));
    }
}
