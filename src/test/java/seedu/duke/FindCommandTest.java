package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.duke.recordtype.Record;

public class FindCommandTest {
    private final PrintStream originalOut = System.out;

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

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "--------------------" + lineSeparator
                + "Matching records:" + lineSeparator
                + "1. Java project" + lineSeparator
                + "2. Full stack Java app" + lineSeparator
                + "--------------------" + lineSeparator;

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
                + "1. JAVA capstone" + lineSeparator
                + "--------------------" + lineSeparator;

        assertEquals(expectedOutput, outputStream.toString());
    }
}
